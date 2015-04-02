package com.tocea.corolla.views

import groovy.transform.InheritConstructors

import org.apache.wicket.markup.head.IHeaderResponse
import org.apache.wicket.spring.injection.annot.SpringBean

import com.tocea.corolla.views.api.IViewAPI
import com.tocea.corolla.views.links.HomePageLink
import com.tocea.corolla.widgets.sidemenu.SideMenuPanel

/**
 * Layout page
 *
 * @author Sylvain Leroy
 *
 */
@InheritConstructors
public abstract class LayoutPage extends BootstrapPage {

	@SpringBean
	protected IViewAPI				viewAPI




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
	protected abstract SideMenuPanel useSideMenu()
}