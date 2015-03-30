package com.tocea.corolla.views

import groovy.transform.InheritConstructors

import org.apache.wicket.bootstrap.Bootstrap
import org.apache.wicket.markup.head.CssHeaderItem
import org.apache.wicket.markup.head.IHeaderResponse
import org.apache.wicket.markup.head.JavaScriptHeaderItem
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.request.resource.CssResourceReference
import org.apache.wicket.request.resource.JavaScriptResourceReference

import com.tocea.corolla.wicketapp.WicketWebApplication

/**
 * Layout page
 *
 * @author Sylvain Leroy
 *
 */
@InheritConstructors
public abstract class BootstrapPage extends WebPage {


	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response)

		// this.addCss(response, "static/css/bootstrap.css");
		// this.addCss(response, "static/css/styles.css");
		// this.addJs(response, "js/bootstrap.js");

		Bootstrap.renderHeadPlain(response)
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


	}

}