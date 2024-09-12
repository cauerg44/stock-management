package com.appfullstack.backend.services;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appfullstack.backend.dto.EmailDTO;
import com.appfullstack.backend.dto.NewPasswordDTO;
import com.appfullstack.backend.entities.PasswordRecover;
import com.appfullstack.backend.entities.User;
import com.appfullstack.backend.repositories.PasswordRecoverRepository;
import com.appfullstack.backend.repositories.UserRepository;
import com.appfullstack.backend.services.exceptions.ForbiddenException;
import com.appfullstack.backend.services.exceptions.ResourceNotFoundException;

@Service
public class AuthService {

	@Value("${email.password-recover.token.minutes}")
	private Long tokenMinutes;
	
	@Value("${email.password-recover.uri}")
	private String recoverUri;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private PasswordRecoverRepository passwordRecoverRepository;
	
	public void validateClientOrStockManager(Long userId) {
		User me = userService.authenticated();
		if (me.hasRole("ROLE_STOCK_MANAGER")) {
			return;
		}
		if (!me.getId().equals(userId)) {
			throw new ForbiddenException("Acess denied. Should be self or stock manager");
		}
	}

	@Transactional
	public void createRecoverToken(EmailDTO body) {
		User user = userRepository.findByEmail(body.getEmail());
		if (user == null) {
			throw new ResourceNotFoundException("Email not found");
		}

		String token = UUID.randomUUID().toString();

		PasswordRecover entity = new PasswordRecover();
		entity.setToken(token);
		entity.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));
		entity.setEmail(body.getEmail());
		passwordRecoverRepository.save(entity);

		String text = "Acesse o link para definir uma nova senha (válido por " + tokenMinutes + " minutos):\n\n"
				+ recoverUri + token;

		emailService.sendEmail(body.getEmail(), "Recuperação de senha", text);
	}

	@Transactional
	public void saveNewPassword(NewPasswordDTO body) {
		
		List<PasswordRecover> result = passwordRecoverRepository.searchValidTokens(body.getToken(), Instant.now());
		if (result.size() == 0) {
			throw new ResourceNotFoundException("Invalid token");
		}
		
        User user = userRepository.findByEmail(result.get(0).getEmail());
		user.setPassword(passwordEncoder.encode(body.getPassword()));
		user = userRepository.save(user);
	}
}







