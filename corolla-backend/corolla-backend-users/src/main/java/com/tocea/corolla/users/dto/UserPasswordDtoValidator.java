/**
 *
 */
package com.tocea.corolla.users.dto;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.google.common.base.Objects;

/**
 * @author sleroy
 *
 */
public class UserPasswordDtoValidator implements Validator {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(final Class<?> _arg0) {

		return UserPasswordDto.class.equals(_arg0);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(final Object _object, final Errors _errors) {
		ValidationUtils.invokeValidator(new UserDtoValidator(), _object,
		                                _errors);
		ValidationUtils.rejectIfEmptyOrWhitespace(	_errors, "password",
				"password.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(	_errors, "passwordConfirm",
				"password.need	Confirm");
		final UserPasswordDto u = (UserPasswordDto) _object;
		if (u.getPassword().length() > 256) {
			_errors.reject("password", "password.tooLong");
		}
		if (Objects.equal(u.getPassword(), u.getPasswordConfirm())) {
			_errors.reject("password", "password.notEqualsConfirm");

		}
	}

}
