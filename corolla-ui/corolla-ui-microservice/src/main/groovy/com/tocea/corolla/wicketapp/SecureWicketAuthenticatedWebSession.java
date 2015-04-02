package com.tocea.corolla.wicketapp;
import org.apache.log4j.Logger;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecureWicketAuthenticatedWebSession extends AuthenticatedWebSession {

	private static final long serialVersionUID = 3355101222374558750L;

	private static final Logger logger = Logger.getLogger(SecureWicketAuthenticatedWebSession.class);

	@SpringBean()
	private AuthenticationManager authenticationManager;

	public SecureWicketAuthenticatedWebSession(final Request request) {
		super(request);
		this.injectDependencies();
		this.ensureDependenciesNotNull();
	}

	@Override
	public boolean authenticate(final String username, final String password) {
		boolean authenticated = false;
		try {
			final Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			authenticated = authentication.isAuthenticated();
		} catch (final AuthenticationException e) {
			logger.warn(String.format("User &#039;%s&#039; failed to login. Reason: %s", username, e.getMessage()));
			authenticated = false;
		}
		return authenticated;
	}

	@Override
	public Roles getRoles() {
		final Roles roles = new Roles();
		this.getRolesIfSignedIn(roles);
		return roles;
	}

	private void addRolesFromAuthentication(final Roles roles, final Authentication authentication) {
		for (final GrantedAuthority authority : authentication.getAuthorities()) {
			roles.add(authority.getAuthority());
		}
	}

	private void ensureDependenciesNotNull() {
		if (this.authenticationManager == null) {
			throw new IllegalStateException("An authenticationManager is required.");
		}
	}

	private void getRolesIfSignedIn(final Roles roles) {
		if (this.isSignedIn()) {
			final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			this.addRolesFromAuthentication(roles, authentication);
		}
	}

	private void injectDependencies() {
		Injector.get().inject(this);
	}
}