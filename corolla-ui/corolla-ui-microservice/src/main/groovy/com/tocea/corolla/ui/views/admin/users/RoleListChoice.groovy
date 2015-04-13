/**
 *
 */
package com.tocea.corolla.ui.views.admin.users

import org.apache.wicket.markup.html.form.DropDownChoice
import org.apache.wicket.markup.html.form.IChoiceRenderer
import org.apache.wicket.model.IModel
import org.apache.wicket.model.util.ListModel

import com.tocea.corolla.ui.rest.api.IRestAPI
import com.tocea.corolla.users.domain.Role

/**
 * This list choice defines the role list.
 *
 * @author sleroy
 *
 */
class RoleListChoice extends DropDownChoice<Role> {

	public RoleListChoice(final String _id, final IModel<Role> _model,
	final IRestAPI _restAPI) {
		this(_id, _model, _restAPI.getRoles(), new RoleChoiceRender())
	}

	public RoleListChoice(final String _id, final IModel<Role> _model,
	final List<Role> _choices,
	final IChoiceRenderer<Role> _renderer) {
		super(_id, _model, new ListModel<Role>(_choices), _renderer)
	}
}
