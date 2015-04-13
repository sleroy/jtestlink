/**
 *
 */
package com.tocea.corolla.ui.views.admin.users

import org.apache.wicket.markup.html.form.ChoiceRenderer

import com.tocea.corolla.users.domain.Role

/**
 * @author sleroy
 *
 */
class RoleChoiceRender extends ChoiceRenderer<Role> {


	public RoleChoiceRender() {
		super("name", "id")
	}


}
