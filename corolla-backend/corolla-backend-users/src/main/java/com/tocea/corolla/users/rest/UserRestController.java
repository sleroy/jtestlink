/**
 *
 */
package com.tocea.corolla.users.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.users.commands.DeleteUserCommand;
import com.tocea.corolla.users.commands.DisableUserCommand;
import com.tocea.corolla.users.commands.EnableUserCommand;
import com.tocea.corolla.users.dao.IRoleDAO;
import com.tocea.corolla.users.dao.IUserDAO;
import com.tocea.corolla.users.dao.UserDtoService;
import com.tocea.corolla.users.domain.Permission;
import com.tocea.corolla.users.dto.UserDto;
import com.tocea.corolla.users.dto.UserWithRoleDto;
import com.tocea.corolla.users.exceptions.InvalidLoginException;
import com.tocea.corolla.users.exceptions.OperationForbidenWithThisLoginException;
import com.tocea.corolla.users.validation.UserValidation;
import com.tocea.corolla.utils.datatable.DataTableList;

/**
 * @author sleroy
 *
 */
@RestController()
@RequestMapping("/rest/users")
@Secured(Permission.REST)
public class UserRestController {

	@Autowired
	private IUserDAO		userDao;
	@Autowired
	private IRoleDAO		roleDao;
	@Autowired
	private Gate			gate;

	@Autowired
	private UserDtoService	dtoService;

	@Autowired
	private UserValidation	userValidation;

	@Secured({ Permission.ADMIN, Permission.ADMIN_USERS })
	@RequestMapping(value = "/delete/{login}", method = RequestMethod.GET)
	public void deleteUser(@PathVariable final String login,
			final Principal _principal) {
		if (!this.userValidation.isValidLogin(login)) {
			throw new InvalidLoginException();
		}
		if (login.equals(_principal.getName())) {
			throw new OperationForbidenWithThisLoginException();
		}

		this.gate.dispatch(new DeleteUserCommand(login));

	}

	@Secured({ Permission.ADMIN, Permission.ADMIN_USERS })
	@RequestMapping(value = "/disable/{login}", method = RequestMethod.GET)
	public void disableUser(@PathVariable final String login,
			final Principal _principal) {
		if (!this.userValidation.isValidLogin(login)) {
			throw new InvalidLoginException();
		}
		if (login.equals(_principal.getName())) {
			throw new OperationForbidenWithThisLoginException();
		}

		this.gate.dispatch(new DisableUserCommand(login));
	}

	@Secured({ Permission.ADMIN, Permission.ADMIN_USERS })
	@RequestMapping(value = "/enable/{login}", method = RequestMethod.GET)
	public void enableUser(@PathVariable final String login,
			final Principal _principal) {
		if (!this.userValidation.isValidLogin(login)) {
			throw new InvalidLoginException();
		}
		if (login.equals(_principal.getName())) {
			throw new OperationForbidenWithThisLoginException();
		}

		this.gate.dispatch(new EnableUserCommand(login));
	}

	@RequestMapping("/all")
	public DataTableList<UserDto> getAllUsers() {
		final DataTableList<UserDto> userTable = new DataTableList();
		userTable.addAll(this.dtoService.getUsersDtoList());
		return userTable;
	}

	@RequestMapping("/allwithrole")
	public DataTableList<UserWithRoleDto> getAllUsersWithRole() {
		final DataTableList<UserWithRoleDto> userTable = new DataTableList();
		userTable.addAll(this.dtoService.getUsersWithRoleList());
		return userTable;
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public JsonError handleException(final Exception e) {
		return new JsonError(e.getMessage());
	}

}
