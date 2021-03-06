/**
 *
 */
package com.tocea.corolla.users.handlers

import static org.mockito.Mockito.*

import org.junit.Rule

import spock.lang.Specification

import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.users.commands.DisableUserCommand
import com.tocea.corolla.users.dao.IUserDAO
import com.tocea.corolla.users.domain.User
import com.tocea.corolla.utils.functests.FunctionalTestDoc

/**
 * @author sleroy
 *
 */
@FunctionalTestDoc(requirementName = "ENABLE_USER")
class DisableUserCommandHandlerTest extends Specification{

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IUserDAO userDao = Mock(IUserDAO)

	def DisableUserCommandHandler	handler

	def User validUser


	def setup() {
		handler = new DisableUserCommandHandler(
				userDAO : userDao
				)
		validUser = 		new User(
				activationToken:"",
				active:true,
				createdTime:null,
				email:"user@dummy.org",
				firstName:"John",
				lastName:"Doe",
				locale:"fr",
				login:"login",
				password:"superPassword",
				roleId:1)
	}


	/**
	 * Test method for
	 * {@link com.tocea.corolla.users.handlers.CreateUserCommandHandler#handle(com.tocea.corolla.users.commands.CreateUserCommand)}
	 * .
	 */
	def "test enable user with existing user"() {
		given:
		userDao.findUserByLogin('sleroy') >> validUser
		when:
		final DisableUserCommand command = new DisableUserCommand(userLogin : 'sleroy')

		this.handler.handle command

		then:
		1 * userDao.save({ User u ->
			u.active == false
		})
	}

	/**
	 * Test method for
	 * {@link com.tocea.corolla.users.handlers.AddNewUserCommandHandler#handle(com.tocea.corolla.users.commands.AddNewUserCommand)}
	 * .
	 */
	def "test enable user with unknown user"() {
		given:
		userDao.findUserByLogin('sleroy') >> null
		when:
		final DisableUserCommand command = new DisableUserCommand(userLogin : 'sleroy')

		this.handler.handle command

		then:
		0 * userDao.save(_)
	}
}
