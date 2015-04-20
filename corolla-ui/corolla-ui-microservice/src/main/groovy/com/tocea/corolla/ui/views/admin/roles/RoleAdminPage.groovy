

package com.tocea.corolla.ui.views.admin.roles

import groovy.transform.InheritConstructors

import com.tocea.corolla.ui.views.admin.central.AdminPage
import com.tocea.corolla.ui.widgets.datatable.DataTableBuilder

/**
 * Role admin page
 *
 * @author Sylvain Leroy
 *
 */
@InheritConstructors
class RoleAdminPage  extends AdminPage  {


	@Override
	protected void onInitialize() {
		super.onInitialize()
		def dataTableBuilder = DataTableBuilder.newTable("roleDataTable") //$NON-NLS-1$
		dataTableBuilder.with {
			addColumn "Role", "name"
			addColumn "Description", "note"
			displayRows 30
			withListData this.restAPI.getRoles()
		}
		this.add(dataTableBuilder.build())

	}


}