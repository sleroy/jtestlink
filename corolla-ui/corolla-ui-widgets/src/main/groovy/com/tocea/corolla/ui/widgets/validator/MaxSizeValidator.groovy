/**
 *
 */
package com.tocea.corolla.ui.widgets.validator

import org.apache.wicket.validation.IValidatable
import org.apache.wicket.validation.IValidator
import org.apache.wicket.validation.ValidationError

/**
 * This class is a validator for textfields  that checks the upper bound.
 * @author sleroy
 *
 */
class MaxSizeValidator implements IValidator {


	def int max

	public MaxSizeValidator(int _max) {
		max = _max
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.validation.IValidator#validate(org.apache.wicket.validation.IValidatable)
	 */
	@Override
	public void validate(IValidatable _validatable) {
		def Object value = _validatable.value
		if (value instanceof String && value.length() > max) {
			def error = new ValidationError()
			error.setVariable "length", value.length
			error.setVariable "max", max
			error.setMessage "Field length is too long ($max characters max)"
			_validatable.error error
		}
	}
}
