package com.tocea.corolla.views.application

import org.apache.wicket.markup.head.IHeaderResponse

import com.tocea.corolla.views.LayoutPage
import com.tocea.corolla.views.sidemenu.ApplicationSideMenu
import com.tocea.corolla.widgets.sidemenu.SideMenuPanel

/**
 * Application page
 *
 * @author Sylvain Leroy
 *
 */
class ApplicationPage extends LayoutPage {
	public ApplicationPage() {
		super()

	}

	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response)
	}

	protected void onInitialize() {
		super.onInitialize()


	}

	protected SideMenuPanel useSideMenu() {
		new ApplicationSideMenu(viewAPI)
	}
}