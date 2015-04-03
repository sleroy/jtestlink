

package com.tocea.corolla.views.admin.roles

import groovy.transform.InheritConstructors

import com.tocea.corolla.views.admin.central.AbstractAdminPage
import com.tocea.corolla.widgets.datatable.DataTableBuilder

/**
 * Role admin page
 *
 * @author Sylvain Leroy
 *
 */
@InheritConstructors
class RoleAdminPage  extends AbstractAdminPage  {


	protected void onInitialize() {
		super.onInitialize()
		def dataTableBuilder = DataTableBuilder.newTable("roleDataTable") //$NON-NLS-1$
		dataTableBuilder.with {
			addColumn "Role", "name"
			addColumn "Description", "note"
			displayRows 30
			withListData this.viewAPI.getRoles()
		}
		this.add(dataTableBuilder.build())

	}


}