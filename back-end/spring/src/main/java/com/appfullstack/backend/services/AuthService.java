package com.appfullstack.backend.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appfullstack.backend.dto.EmailDTO;
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
		Optional<User> user = userRepository.findByEmail(body.getEmail());
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
}
