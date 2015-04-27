/**
 *
 */
package com.tocea.corolla.ui.views.admin

import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

import com.tocea.corolla.users.domain.Permission

/**
 * @author sleroy
 *
 */
@Controller
public class UserAdminController {

	@ModelAttribute("sideMenu")
	public String addAccount() {
		return "adminMenu"
	}

	@Secured([
		Permission.ADMIN,
		Permission.ADMIN_USERS
	])
	@RequestMapping("/ui/admin/users")
	public ModelAndView getHomePage() {
		return new ModelAndView("admin/users")
	}
}
