package com.tocea.corolla.wicketapp

import org.apache.wicket.Page
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication
import org.apache.wicket.devutils.stateless.StatelessChecker
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.spring.injection.annot.SpringComponentInjector
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

import com.tocea.corolla.views.HomePage
import com.tocea.corolla.views.LoginPage
import com.tocea.corolla.views.admin.central.AdminCentralPage
import com.tocea.corolla.views.admin.roles.RoleAdminPage
import com.tocea.corolla.views.admin.roles.RoleEditPage
import com.tocea.corolla.views.admin.users.UserAdminPage
import com.tocea.corolla.views.admin.users.UserEditPage

/**
 * The web application class also serves as spring boot starting point by using
 * spring boot's EnableAutoConfiguration annotation and providing the main
 * method.
 *
 * @author Stefan Kloe
 *
 */
@Component
class WicketWebApplication extends AuthenticatedWebApplication {

	private final static Logger logger = LoggerFactory
	.getLogger(WicketWebApplication.class)

	def boolean isInitialized = false

	@Autowired
	def ApplicationContext applicationContext

	/**
	 * provides page for default request
	 */
	public Class<? extends Page> getHomePage() {
		return HomePage.class
	}

	protected void init() {
		if (!isInitialized) {
			super.init()
			//https://github.com/sebfz1/wicket-jquery-ui
			//http://central.maven.org/maven2/com/googlecode/wicket-jquery-ui/
			this.getComponentInstantiationListeners().add(
					new SpringComponentInjector(this, this.applicationContext))

			this.getComponentPreOnBeforeRenderListeners().add(new StatelessChecker())
			this.mountPage "/login", LoginPage.class
			this.mountPage "/index.html", HomePage.class
			this.mountPage "/admin", AdminCentralPage.class
			this.mountPage "/admin/users", UserAdminPage.class
			this.mountPage "/admin/users/edit", UserEditPage.class
			this.mountPage "/admin/roles", RoleAdminPage.class
			this.mountPage "/admin/roles/edit", RoleEditPage.class
			// best place to do this is in Application#init()
		}
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return SecureWicketAuthenticatedWebSession.class
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return LoginPage.class
	}
}
