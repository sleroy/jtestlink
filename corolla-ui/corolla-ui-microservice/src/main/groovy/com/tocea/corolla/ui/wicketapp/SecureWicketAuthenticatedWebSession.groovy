package com.tocea.corolla.ui.wicketapp

import groovy.util.logging.Slf4j

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession
import org.apache.wicket.authroles.authorization.strategies.role.Roles
import org.apache.wicket.injection.Injector
import org.apache.wicket.request.Request
import org.apache.wicket.spring.injection.annot.SpringBean
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

@Slf4j
class SecureWicketAuthenticatedWebSession extends
AuthenticatedWebSession {

	def static final  Logger LOGGER= LoggerFactory.getLogger(SecureWicketAuthenticatedWebSession.class)

	@SpringBean()
	private AuthenticationManager	authenticationManager

	public SecureWicketAuthenticatedWebSession(final Request request) {
		super(request)
		this.injectDependencies()
		this.ensureDependenciesNotNull()
	}

	@Override
	public boolean authenticate(final String username, final String password) {
		boolean authenticated = false
		try {
			final Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
					password))
			SecurityContextHolder.getContext()
					.setAuthentication(authentication)
			authenticated = authentication.isAuthenticated()
		} catch (final AuthenticationException e) {
			LOGGER.warn(String.format(	"User '%s' failed to login. Reason: %s",
					username, e.getMessage()))
			authenticated = false
		}
		return authenticated
	}

	@Override
	public Roles getRoles() {
		final Roles roles = new Roles()
		this.getRolesIfSignedIn(roles)
		return roles
	}


	private void addRolesFromAuthentication(final Roles roles,
			final Authentication authentication) {
		for (final GrantedAuthority authority : authentication.getAuthorities()) {
			roles.add(authority.getAuthority())
		}
	}

	private void ensureDependenciesNotNull() {
		if (this.authenticationManager == null) {
			throw new IllegalStateException("AdminSession requires an authenticationManager.")
		}
	}

	private void getRolesIfSignedIn(final Roles roles) {
		if (this.isSignedIn()) {
			final Authentication authentication = SecurityContextHolder.getContext().getAuthentication()
			this.addRolesFromAuthentication(roles, authentication)
		}
	}

	private void injectDependencies() {
		Injector.get().inject(this)
	}
}