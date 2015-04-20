/**
 *
 */
package com.tocea.corolla.ui.views.admin.users

import org.apache.wicket.model.IModel
import org.apache.wicket.validation.IValidatable
import org.apache.wicket.validation.IValidator
import org.apache.wicket.validation.ValidationError

import com.tocea.corolla.users.domain.User

/**
 * Validator to confirm password entry.
 * @author sleroy
 *
 */
public class PasswordConfirmValidator implements IValidator<String> {

	def IModel<User> userModel

	public PasswordConfirmValidator(final IModel<User> _user) {
		userModel = _user
	}

	@Override
	public void validate(IValidatable<String> _validatable) {
		def userModelGetObject = userModel.getObject()
		def _validatableGetValue = _validatable.getValue()
		if (!Objects.equals(userModelGetObject, _validatableGetValue)) {
			_validatable.error new ValidationError("Password and confirmation does not match")
		}
	}
}
