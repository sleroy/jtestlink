package com.tocea.corolla.app.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tocea.corolla.app.configuration.LdapSecurityConfigurationBean;
import com.tocea.corolla.app.configuration.SecurityConfigurationBean;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled=true)
@EnableWebMvcSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Configuration
	@Order(1)
	@EnableWebSecurity
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(final HttpSecurity http) throws Exception {
			LOGGER.info("Web-- Defining REST Security");
			http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.antMatcher("/api/**")
			.authorizeRequests()
			.anyRequest().hasRole("REST")
			.and()
			.httpBasic().and().
			anonymous().disable();

		}
	}
	@Configuration
	@EnableWebSecurity
	@Order(2)
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(final HttpSecurity http) throws Exception {
			LOGGER.info("Web-- Defining Web Security");
			http.csrf().disable().exceptionHandling().and().headers()
			.contentTypeOptions().xssProtection().cacheControl()
			.httpStrictTransportSecurity().frameOptions().and()
			.sessionManagement().and().httpBasic().and().

			authorizeRequests()
			.antMatchers("/static/**").permitAll()
			.antMatchers("/font/**").permitAll()
			.antMatchers("/css/**").permitAll()
			.antMatchers("/images/**").permitAll()
			.antMatchers("/js/**").permitAll()
			.antMatchers("/resources/**").permitAll()
			.antMatchers("/ui/login").permitAll()
			.anyRequest().authenticated().and().httpBasic().and().

			formLogin().loginPage("/ui/login.html").usernameParameter("username")
			.passwordParameter("password").defaultSuccessUrl("/ui/index.html").failureUrl("/ui/login.html&error=true").and()
			.logout().logoutUrl("/ui/logout.html").permitAll().deleteCookies("JSESSIONID");
		}
	}
	private static final Logger				LOGGER	= LoggerFactory.getLogger(WebSecurityConfig.class);

	@Autowired
	private UserDetailsService				userDetailsService;

	@Autowired
	private SecurityConfigurationBean		security;

	@Autowired
	private LdapSecurityConfigurationBean	ldapSecurity;


	@Override
	public void configure(final AuthenticationManagerBuilder auth)
			throws Exception {
		LOGGER.info("SECURITY : Configuration of Authentication");
		/** LDAP Security */
		if (this.ldapSecurity.hasUserDN()) {
			auth.ldapAuthentication()
			.userDnPatterns(this.ldapSecurity.getUserDnPatterns())
			.groupSearchBase(this.ldapSecurity.getGroupSearchBase())
			.groupRoleAttribute(this.ldapSecurity.getGroupRoleAttribute())
			.groupSearchFilter(this.ldapSecurity.getGroupSearchFilter());
		} else if (this.ldapSecurity.hasUserSearch()) {
			auth.ldapAuthentication()
			.userSearchBase(this.ldapSecurity.getUserSearchBase())
			.userSearchFilter(this.ldapSecurity.getUserSearchFilter())
			.groupSearchBase(this.ldapSecurity.getGroupSearchBase())
			.groupRoleAttribute(this.ldapSecurity.getGroupRoleAttribute())
			.groupSearchFilter(this.ldapSecurity.getGroupSearchFilter());
		}
		auth.userDetailsService(this.userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());

		//		auth
		//		.inMemoryAuthentication()
		//		.withUser("user").password("password").roles("USER");
	}

}