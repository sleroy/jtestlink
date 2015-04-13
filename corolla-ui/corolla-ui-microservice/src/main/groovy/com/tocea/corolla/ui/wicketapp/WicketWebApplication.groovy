package com.tocea.corolla.ui.wicketapp

import groovy.util.logging.Slf4j

import org.apache.wicket.Page
import org.apache.wicket.RuntimeConfigurationType
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AnnotationsRoleAuthorizationStrategy
import org.apache.wicket.devutils.stateless.StatelessChecker
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.request.IRequestHandler
import org.apache.wicket.request.cycle.RequestCycle
import org.apache.wicket.spring.injection.annot.SpringComponentInjector
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

import com.tocea.corolla.ui.views.HomePage
import com.tocea.corolla.ui.views.LoginPage
import com.tocea.corolla.ui.views.admin.central.AdminCentralPage
import com.tocea.corolla.ui.views.admin.roles.RoleAdminPage
import com.tocea.corolla.ui.views.admin.roles.RoleEditPage
import com.tocea.corolla.ui.views.admin.users.UserAdminPage
import com.tocea.corolla.ui.views.admin.users.UserEditPage

/**
 * The web application class also serves as spring boot starting point by using
 * spring boot's EnableAutoConfiguration annotation and providing the main
 * method.
 *
 *
 */
@Component
@Slf4j
class WicketWebApplication extends AuthenticatedWebApplication {

	@Autowired
	def ApplicationContext applicationContext



	/**
	 * provides page for default request
	 */
	public Class<? extends Page> getHomePage() {
		return HomePage.class
	}

	protected void init() {
		super.init()
		//https://github.com/sebfz1/wicket-jquery-ui
		//http://central.maven.org/maven2/com/googlecode/wicket-jquery-ui/
		this.getComponentInstantiationListeners().add(
				new SpringComponentInjector(this, this.applicationContext))
		getSecuritySettings().setAuthorizationStrategy(new AnnotationsRoleAuthorizationStrategy(this))
		this.getComponentPreOnBeforeRenderListeners().add(new StatelessChecker())

		// initialize debug settings
		log.info "---------------------------------------------"
		log.info "Initialisation of Komea Admin Webgui"

		def debugMode  = this.getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT

		this.getDebugSettings().setAjaxDebugModeEnabled(debugMode)
		this.getDebugSettings().setOutputComponentPath(debugMode)
		this.getDebugSettings().setOutputMarkupContainerClassName(debugMode)
		this.getDebugSettings().setLinePreciseReportingOnNewComponentEnabled(debugMode)
		this.getDebugSettings().setLinePreciseReportingOnAddComponentEnabled(debugMode)
		this.getDebugSettings().setDevelopmentUtilitiesEnabled(debugMode)
		this.getDebugSettings().setComponentUseCheck(debugMode)

		this.getComponentInstantiationListeners().add(new SpringComponentInjector(this))

		// Optimisation
		this.getResourceSettings().setThrowExceptionOnMissingResource(false)
		this.getMarkupSettings().setStripWicketTags(true)


		mountPages()
		// best place to do this is in Application#init()
		//
		//		/* In case of unhandled exception redirect it to a custom page */
		//		getRequestCycleListeners().add(new AbstractRequestCycleListener() {
		//					@Override
		//					public IRequestHandler onException(RequestCycle cycle,
		//							Exception e) {
		//						return new RenderPageRequestHandler(new
		//								PageProvider(new ExceptionErrorPage(e)))
		//					}
		//				})

	}

	def mountPages() {
		this.mountPage "/login", LoginPage.class
		this.mountPage "/home", HomePage.class
		this.mountPage "/admin", AdminCentralPage.class
		this.mountPage "/admin/users", UserAdminPage.class
		this.mountPage "/admin/users/edit", UserEditPage.class
		this.mountPage "/admin/users/add", UserEditPage.class
		this.mountPage "/admin/roles", RoleAdminPage.class
		this.mountPage "/admin/roles/edit", RoleEditPage.class
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
