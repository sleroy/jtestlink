/**
 *
 */
package com.tocea.corolla.users.rest;

import javax.transaction.Transactional;

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

import com.tocea.corolla.cqrs.gate.EmptyCommandCallback;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.users.commands.DeleteRoleCommand;
import com.tocea.corolla.users.dao.IRoleDAO;
import com.tocea.corolla.users.domain.Permission;
import com.tocea.corolla.users.exceptions.InvalidRoleException;

/**
 * @author sleroy
 *
 */
@RestController()
@RequestMapping("/rest/roles")
@Secured(Permission.REST)
@Transactional
public class RoleRestController {

	@Autowired
	private IRoleDAO	roleDao;
	@Autowired
	private Gate		gate;

	@Secured({ Permission.ADMIN, Permission.ADMIN_ROLES })
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public void deleteUser(@PathVariable final Integer id) {
		if (this.roleDao.findOne(id) == null) {
			throw new InvalidRoleException();
		}

		this.gate.dispatch(new DeleteRoleCommand(id));

	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public JsonError handleException(final Exception e) {
		return new JsonError(e.getMessage());
	}

}
