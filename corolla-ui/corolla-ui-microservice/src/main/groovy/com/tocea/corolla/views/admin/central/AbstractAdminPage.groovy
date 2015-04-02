package com.tocea.corolla.views.admin.central

import groovy.transform.InheritConstructors

import com.tocea.corolla.views.LayoutPage
import com.tocea.corolla.views.sidemenu.AdminSideMenu
import com.tocea.corolla.widgets.sidemenu.SideMenuPanel

/**
 * @author sleroy
 *
 */
@InheritConstructors
abstract class AbstractAdminPage extends LayoutPage {

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tocea.corolla.views.LayoutPage#provideMenu()
	 */
	@Override
	protected final SideMenuPanel useSideMenu() {

		return new AdminSideMenu()
	}
}
