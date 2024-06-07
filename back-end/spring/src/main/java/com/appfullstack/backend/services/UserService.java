package com.appfullstack.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appfullstack.backend.dto.UserDTO;
import com.appfullstack.backend.entities.Role;
import com.appfullstack.backend.entities.User;
import com.appfullstack.backend.projection.UserDetailsProjection;
import com.appfullstack.backend.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<UserDetailsProjection> list = repository.searchUserAndRolesByEmail(username);
		if (list.size() == 0) {
			throw new UsernameNotFoundException("User not found.");
		}
		
		User user = new User();
		user.setEmail(username);
		user.setPassword(list.get(0).getPassword());
		for (UserDetailsProjection projection : list) {
			user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
		}
		
		return user;
	}
	
	protected User authenticated() {
		try {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
		String username = jwtPrincipal.getClaim("username");
		return repository.findByEmail(username).get();
		}
		catch (Exception e) {
			throw new UsernameNotFoundException("Email not found.");
		}
	}
	
	@Transactional(readOnly = true)
	public UserDTO getMyself() {
		User user = authenticated();
		return new UserDTO(user);
	}
}
