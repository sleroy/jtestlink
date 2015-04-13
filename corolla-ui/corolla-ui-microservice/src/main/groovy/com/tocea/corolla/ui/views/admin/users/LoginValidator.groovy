/**
 *
 */
package com.tocea.corolla.ui.views.admin.users

import groovy.transform.Canonical

import org.apache.wicket.validation.IValidatable
import org.apache.wicket.validation.IValidator
import org.apache.wicket.validation.ValidationError

import com.tocea.corolla.ui.rest.api.IRestAPI

/**
 * @author sleroy
 *
 */
@Canonical
class LoginValidator implements IValidator<String> {

	def IRestAPI restAPI

	@Override
	public void validate(final IValidatable<String> _validatable) {
		def loginValue = _validatable.getValue()
		if (restAPI.existLogin(loginValue)) {
			_validatable.error(new ValidationError("Login already exists"))

		}
	}
}
