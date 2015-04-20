package com.tocea.corolla.ui.views.admin.central

import groovy.transform.InheritConstructors

import com.tocea.corolla.ui.views.CorollaPage
import com.tocea.corolla.ui.views.sidemenu.AdminSideMenu
import com.tocea.corolla.ui.widgets.sidemenu.SideMenuPanel

/**
 * @author sleroy
 *
 */
@InheritConstructors
//@AuthorizeInstantiation("ADMIN")
class AdminPage extends CorollaPage {

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tocea.corolla.ui.views.LayoutPage#provideMenu()
	 */
	@Override
	protected final SideMenuPanel useSideMenu() {

		return new AdminSideMenu()
	}
}
