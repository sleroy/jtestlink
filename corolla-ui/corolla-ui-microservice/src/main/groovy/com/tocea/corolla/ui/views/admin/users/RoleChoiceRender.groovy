/**
 *
 */
package com.tocea.corolla.ui.views.admin.users

import org.apache.wicket.markup.html.form.ChoiceRenderer

import com.tocea.corolla.ui.rest.api.IRestAPI
import com.tocea.corolla.users.domain.Role

/**
 * @author sleroy
 *
 */
class RoleChoiceRender extends ChoiceRenderer<Integer> {
	def IRestAPI restAPI

	public RoleChoiceRender(IRestAPI _restAPI) {
		super()
		restAPI = _restAPI
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.html.form.ChoiceRenderer#getDisplayValue(java.lang.Object)
	 */
	@Override
	public Object getDisplayValue(Integer _object) {
		if (_object == null) {
			return super.getDisplayValue(_object)
		} else {
			return restAPI.getRole(_object).name
		}
	}


	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.html.form.ChoiceRenderer#getIdValue(java.lang.Object, int)
	 */
	@Override
	public String getIdValue(Integer _object, int _index) {
		if (_object == null) {
			return super.getIdValue(_object, _index)
		} else {
			return _object.toString()
		}
	}
}
