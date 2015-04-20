package com.tocea.corolla.ui.views.application

import org.apache.wicket.markup.head.IHeaderResponse

import com.tocea.corolla.ui.views.LayoutPage
import com.tocea.corolla.ui.views.sidemenu.ApplicationSideMenu
import com.tocea.corolla.ui.widgets.sidemenu.SideMenuPanel

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
		new ApplicationSideMenu(restAPI)
	}
}