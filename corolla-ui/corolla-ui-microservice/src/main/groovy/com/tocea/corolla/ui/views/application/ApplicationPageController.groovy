/**
 *
 */
package com.tocea.corolla.ui.views.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

import com.tocea.corolla.products.dao.IApplicationDAO

/**
 * @author sleroy
 *
 */


@Controller
public class ApplicationPageController {

	@Autowired
	private IApplicationDAO applicationDAO

	@ModelAttribute("applications")
	def addProducts() {
		return applicationDAO.findAll()
	}

	@ModelAttribute("sideMenu")
	public String addApplicationMenu() {
		return "applicationMenu"
	}

	@RequestMapping(value=["/ui/applications"])
	public ModelAndView getHomePage() {
		return new ModelAndView("application/control")
	}
}
