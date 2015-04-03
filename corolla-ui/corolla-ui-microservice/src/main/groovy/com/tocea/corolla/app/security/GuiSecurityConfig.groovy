package com.tocea.corolla.app.security

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


/**
 * Check the ISSUE https://github.com/spring-projects/spring-boot/issues/1801 for explanation.
 * @author sleroy
 *
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@EnableWebSecurity
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class GuiSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger			LOGGER	= LoggerFactory.getLogger(GuiSecurityConfig.class)

	//	@Autowired
	//	private UserDetailsService			userDetailsService;

	//	@Autowired
	//	private PasswordEncoder passwordEncoder;



	@Override
	public void configure(final AuthenticationManagerBuilder auth)
	throws Exception {
		//		final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		//		authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
		//		authenticationProvider.setUserDetailsService(this.userDetailsService);
		//		final ReflectionSaltSource reflectionSaltSource = new ReflectionSaltSource();
		//		reflectionSaltSource.setUserPropertyToUse("salt");
		//		authenticationProvider.setSaltSource(reflectionSaltSource);
		//		auth.authenticationProvider(authenticationProvider);

		auth.inMemoryAuthentication().withUser("user").password("password1")
				.roles("USER")
	}

	@Bean
	public AuthenticationManager getAuthentication() throws Exception {
		return super.authenticationManager()
	}



	// @Override
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		LOGGER.info("Web-- Defining Web Security")
		http.authorizeRequests().antMatchers("/login").permitAll().antMatchers("/logout").permitAll().anyRequest().fullyAuthenticated()
		http.formLogin().loginPage("/login").defaultSuccessUrl("/home").failureUrl("/login?errorCode=badCredentials").usernameParameter("username").passwordParameter("password").and().logout().logoutUrl("/logout").deleteCookies("JSESSIONID").invalidateHttpSession(true)
		http.csrf().disable()


	}



}