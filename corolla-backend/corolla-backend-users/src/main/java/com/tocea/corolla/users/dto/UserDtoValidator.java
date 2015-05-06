/**
 *
 */
package com.tocea.corolla.users.dto;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.tocea.corolla.users.dao.IRoleDAO;
import com.tocea.corolla.users.dao.IUserDAO;

/**
 * @author sleroy
 *
 */
@Component
public class UserDtoValidator implements Validator {

	@Autowired
	private IRoleDAO	roleDAO;

	@Autowired
	private IUserDAO	userDAO;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(final Class<?> _arg0) {

		return UserDto.class.equals(_arg0);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(final Object _arg0, final Errors _errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(	_errors, "firstName",
				"firstName.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(	_errors, "lastName",
				"firstName.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(	_errors, "email",
				"firstName.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(	_errors, "login",
				"firstName.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(	_errors, "locale",
				"firstName.empty");

		final UserDto dto = (UserDto) _arg0;
		if (dto.getRoleId() == null) {
			_errors.reject("roleId", "role.missing");
		}

		if (this.roleDAO.findOne(dto.getRoleId()) == null) {
			_errors.reject("roleId", "role.invalid");
		}

		if (Locale.forLanguageTag(dto.getLocale()) == null) {
			_errors.reject("locale", "locale.invalid");
		}

		final Set<ConstraintViolation<UserDto>> failures = Validation.buildDefaultValidatorFactory()
				.getValidator()
				.validate(	dto);

		for (final ConstraintViolation<UserDto> failure : failures) {
			System.out.println(failure);
			_errors.reject(	failure.getPropertyPath().toString(),
			               	failure.getPropertyPath().toString() + "-> "
			               			+ failure.getMessage());

		}

	}
}
