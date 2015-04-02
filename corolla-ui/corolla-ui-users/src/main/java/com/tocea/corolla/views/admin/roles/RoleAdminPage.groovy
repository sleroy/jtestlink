

package com.tocea.corolla.views.admin.roles

import groovy.transform.InheritConstructors

import com.tocea.corolla.users.domain.Role
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
		final DataTableBuilder<Role,String> dataTableBuilder = DataTableBuilder.newTable("roleDataTable") //$NON-NLS-1$
		dataTableBuilder.addColumn("Role", "name")
		dataTableBuilder.addColumn("Description", "note")
		dataTableBuilder.displayRows(30)
		dataTableBuilder.withListData(this.viewAPI.getRoles())
		this.add(dataTableBuilder.build())

	}


}