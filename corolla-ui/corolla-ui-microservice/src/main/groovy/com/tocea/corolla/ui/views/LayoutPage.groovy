package com.tocea.corolla.ui.views

import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors

import javax.servlet.ServletRequest

import org.apache.wicket.markup.head.CssHeaderItem
import org.apache.wicket.markup.head.IHeaderResponse
import org.apache.wicket.markup.head.JavaScriptHeaderItem
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.request.resource.CssResourceReference
import org.apache.wicket.request.resource.JavaScriptResourceReference
import org.apache.wicket.spring.injection.annot.SpringBean
import org.springframework.security.core.userdetails.UserDetails

import com.tocea.corolla.ui.rest.api.IRestAPI
import com.tocea.corolla.ui.views.links.HomePageLink
import com.tocea.corolla.ui.wicketapp.WicketWebApplication
import com.tocea.corolla.ui.widgets.sidemenu.SideMenuPanel

/**
 * Layout page
 *
 * @author Sylvain Leroy
 *
 */
@InheritConstructors
@CompileStatic
public abstract class LayoutPage extends WebPage {

	@SpringBean
	def IRestAPI				restAPI


	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response)

		// this.addCss(response, "static/css/bootstrap.css");
		// this.addCss(response, "static/css/styles.css");
		// this.addJs(response, "js/bootstrap.js");

		//Bootstrap.renderHeadPlain(response)
	}

	/**
	 * @param response
	 * @param css1
	 */
	protected void addCss(final IHeaderResponse response, final String css1) {
		final CssResourceReference cssResourceReference = new CssResourceReference(WicketWebApplication.class,
				css1)
		response.render(CssHeaderItem.forReference(cssResourceReference))
	}

	/**
	 * @param response
	 * @param css1
	 */
	protected void addJs(final IHeaderResponse response, final String css1) {
		final JavaScriptResourceReference jsResourceReference = new JavaScriptResourceReference(WicketWebApplication.class,
				css1)
		response.render(JavaScriptHeaderItem.forReference(jsResourceReference))
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.wicket.Page#onInitialize()
	 */
	@Override
	protected void onInitialize() {

		super.onInitialize()

		this.setStatelessHint(true)

		this.add(new HomePageLink("homeLink"))

		this.add(this.useSideMenu())
	}


	/**
	 * Returns the side menu for this page.
	 *
	 * @return the side menu.
	 */
	protected SideMenuPanel useSideMenu() {
		return new SideMenuPanel()
	}

	protected SecurityDetails getSecurityController() {

		final SecurityDetails securityController = new SecurityDetails((ServletRequest) this.getRequest()
				.getContainerRequest(), "")
		return securityController
	}

	/**
	 * Returns the user details
	 *
	 * @return
	 */
	protected UserDetails obtainSecurityDetails() {

		final SecurityDetails securityController = this.getSecurityController()
		final UserDetails userDetails = securityController.getUserDetails()
		return userDetails
	}
}
