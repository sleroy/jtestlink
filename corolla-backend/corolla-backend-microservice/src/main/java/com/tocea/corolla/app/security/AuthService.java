package com.tocea.corolla.app.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tocea.corolla.users.dao.IRoleDAO;
import com.tocea.corolla.users.dao.IUserDAO;
import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.domain.User;
import com.tocea.corolla.utils.serviceapi.IReadonlyService;

/**
 * Provides userDetails
 */
@Service("authService")
public class AuthService implements UserDetailsService, IReadonlyService {
	private static final Logger	LOGGER	= LoggerFactory.getLogger(AuthService.class);
	@Autowired
	private IUserDAO			userService;

	@Autowired
	private IRoleDAO			roleService;

	@Override
	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException {
		LOGGER.info("Requesting information about {}", username);
		final AuthUser authUser;

		final User user = this.userService.findUserByLogin(username);

		if (user == null || !user.isActive()) {
			LOGGER.info("Load user by username for " + username
			            + " but user unknown or inactive");

			throw new UsernameNotFoundException("User unknown");
		}
		Role role = this.roleService.findOne(user.getRoleId());
		if (role == null) {
			LOGGER.info("User {} does not have role!", username);
			role = Role.DEFAULT_ROLE;
		}
		authUser = new AuthUser(user, role);
		LOGGER.info("Informations returned {}", authUser);
		return authUser;
	}
}
