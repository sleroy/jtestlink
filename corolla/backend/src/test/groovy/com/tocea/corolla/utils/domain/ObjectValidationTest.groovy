package com.tocea.corolla.utils.domain

import static org.junit.Assert.*

import javax.validation.ConstraintViolationException

import spock.lang.Specification

import com.tocea.corolla.users.domain.User


class ObjectValidationTest extends Specification {
	def "object is not valid"()  {
		expect:
		new ObjectValidation().isValid(new User()) == false
	}
	def "object is valid"()  {
		expect:
		new ObjectValidation().isValid("bla") == true
	}

	def "object  :validation failed "()  {
		when:
		new ObjectValidation().validate(new User())
		then:
		thrown(ConstraintViolationException)
	}
	def "object : validation success"()  {
		when:
		new ObjectValidation().validate("bla")
		then:
		notThrown(ConstraintViolationException)
	}
}
