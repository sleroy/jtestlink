/**
 *
 */
package com.tocea.corolla.users.handlers

import static org.mockito.Mockito.*

import org.junit.Rule
import org.mockito.Mockito

import spock.lang.Specification

import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.users.commands.AddNewUserCommand
import com.tocea.corolla.users.dao.IRoleDAO
import com.tocea.corolla.users.dao.IUserDAO
import com.tocea.corolla.users.domain.Role
import com.tocea.corolla.users.domain.User
import com.tocea.corolla.users.exceptions.InvalidEmailAddressException
import com.tocea.corolla.users.exceptions.MissingUserInformationException
import com.tocea.corolla.users.exceptions.RoleManagementBrokenException
import com.tocea.corolla.users.service.EmailValidationService
import com.tocea.corolla.utils.functests.FunctionalTestDoc

/**
 * @author sleroy
 *
 */
@FunctionalTestDoc(requirementName = "CREATE_USER")
class AddNewUserCommandHandlerTest extends Specification{

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IUserDAO userDao = Mockito.mock(IUserDAO)
	def IRoleDAO roleDao = Mockito.mock(IRoleDAO)
	def emailValid = new EmailValidationService()
	def defaultRole = new Role(id:1, name:'Guest', note:'Guest role', permissions:"ALL")

	def AddNewUserCommandHandler	handler

	def User validUser
	def User validUserWithoutRole
	def User userWithInvalidEmail


	def setup() {
		handler = new AddNewUserCommandHandler(
				userDAO : userDao,
				roleDAO : roleDao,
				emailValidationService : emailValid
				)
		validUser = 		new User(
				activationToken:"",
				active:true,
				createdTime:null,
				defaultTestProject_id:0,
				email:"user@dummy.org",
				firstName:"John",
				lastName:"Doe",
				locale:"fr",
				login:"login",
				password:"superPassword",
				roleId:1)

		validUserWithoutRole = 		new User(
				activationToken:"",
				active:true,
				createdTime:null,
				defaultTestProject_id:0,
				email:"user@dummy.org",
				firstName:"John",
				lastName:"Doe",
				locale:"fr",
				login:"login",
				password:"superPassword",
				roleId:null)

		userWithInvalidEmail = 		new User(
				activationToken:"",
				active:true,
				createdTime:null,
				defaultTestProject_id:0,
				email:"user",
				firstName:"John",
				lastName:"Doe",
				locale:"fr",
				login:"login",
				password:"superPassword",
				roleId:null)
	}


	/**
	 * Test method for
	 * {@link com.tocea.corolla.users.handlers.AddNewUserCommandHandler#handle(com.tocea.corolla.users.commands.AddNewUserCommand)}
	 * .
	 */
	def "testvalid user creation"() {

		when:
		final AddNewUserCommand command = new AddNewUserCommand()


		command.setUser validUser

		this.handler.handle(command)
		Mockito.verify(roleDao, times(0)).getDefaultRole()
		Mockito.verify(userDao, times(1)).save validUser
		then:
		notThrown(Exception.class)
	}

	/**
	 * Test method for
	 * {@link com.tocea.corolla.users.handlers.AddNewUserCommandHandler#handle(com.tocea.corolla.users.commands.AddNewUserCommand)}
	 * .
	 */
	def "test valid user without role creation"() {
		given:
		when(roleDao.getDefaultRole()).thenReturn defaultRole
		when:
		final AddNewUserCommand command = new AddNewUserCommand()

		command.setUser validUserWithoutRole
		this.handler.handle(command)

		Mockito.verify(roleDao, times(1)).getDefaultRole()
		Mockito.verify(userDao, times(1)).save validUserWithoutRole
		then:
		notThrown(Exception.class)
	}


	/**
	 * Test method for
	 * {@link com.tocea.corolla.users.handlers.AddNewUserCommandHandler#handle(com.tocea.corolla.users.commands.AddNewUserCommand)}
	 * .
	 */
	def "test user with invalid email"() {
		given:
		when(roleDao.getDefaultRole()).thenReturn defaultRole
		when:
		final AddNewUserCommand command = new AddNewUserCommand()

		command.setUser userWithInvalidEmail
		this.handler.handle(command)

		Mockito.verify(roleDao, times(1)).getDefaultRole()
		Mockito.verify(userDao, times(1)).save userWithInvalidEmail
		then:
		thrown(InvalidEmailAddressException.class)
	}


	/**
	 * Test method for
	 * {@link com.tocea.corolla.users.handlers.AddNewUserCommandHandler#handle(com.tocea.corolla.users.commands.AddNewUserCommand)}
	 * .
	 */
	def "test creation with missing user"() {
		when:
		final AddNewUserCommand command = new AddNewUserCommand()

		this.handler.handle(command)

		then:
		thrown(MissingUserInformationException.class)
	}


	/**
	 * Test method for
	 * {@link com.tocea.corolla.users.handlers.AddNewUserCommandHandler#handle(com.tocea.corolla.users.commands.AddNewUserCommand)}
	 * .
	 */
	def "test valid user with missing role and no role available"() {
		given:
		when(roleDao.getDefaultRole()).thenReturn null
		when:
		final AddNewUserCommand command = new AddNewUserCommand()

		command.setUser validUserWithoutRole
		this.handler.handle(command)

		then:
		thrown(RoleManagementBrokenException)
	}
}
