/**
 *
 */
package com.tocea.corolla.ui.views.application

import groovy.util.logging.Slf4j

import javax.validation.Valid

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.RedirectView

import com.tocea.corolla.cqrs.gate.Gate
import com.tocea.corolla.products.commands.CreateNewApplicationCommand
import com.tocea.corolla.products.commands.EditApplicationCommand
import com.tocea.corolla.products.dao.IApplicationDAO
import com.tocea.corolla.products.domain.Application
import com.tocea.corolla.users.domain.Permission

/**
 * @author sleroy
 *
 */
@Secured([
	Permission.ADMIN,
	Permission.ADMIN_USERS
])
@RequestMapping("/ui/applications/")
@Controller
@Slf4j
public class ApplicationEditController {

	def static final String APPLICATION_EDIT = "application/application_edit"

	static final String APPLICATION = "application"

	@Autowired
	private IApplicationDAO applicationDAO

	@Autowired
	private Gate gate

	@Autowired
	private ApplicationPageController controller



	@RequestMapping("/add")
	public ModelAndView getAddPage(@ModelAttribute Application application) {
		def ModelAndView model = new ModelAndView(APPLICATION_EDIT)
		model.addObject APPLICATION, application
		return model
	}

	@RequestMapping(value="/add", method = RequestMethod.POST)
	public ModelAndView saveNewApplication(@Valid @ModelAttribute("application") Application application, BindingResult _result) {

		if (_result.hasErrors()) {
			def ModelAndView model2 = new ModelAndView(APPLICATION_EDIT)
			model2.addObject(APPLICATION, application)
			return model2
		}
		CreateNewApplicationCommand command = new CreateNewApplicationCommand(application)
		gate.dispatch(command)

		return controller.getHomePage()
	}


	@RequestMapping("/edit/{id}")
	public ModelAndView getAddPage(@PathVariable Integer id) {
		def ModelAndView model = new ModelAndView(APPLICATION_EDIT)
		def application = applicationDAO.findOne(id)
		if (application == null) {
			log.error("Application not found {}", id)
		}
		model.addObject APPLICATION, application == null ? new Application() : new Application(application)
		return model
	}

	@RequestMapping(value="/save", method = RequestMethod.GET)
	public RedirectView returnToApplications() {
		return "/ui/applications"
	}

	@RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
	public ModelAndView modifyUser(@Valid @ModelAttribute("application") Application _application, BindingResult _result, @PathVariable Integer id) {
		if (!id.equals(_application.id)) {
			_result.addError( new ObjectError("id", "ID is invalid"))
		}
		if (!_application.hasId() && applicationDAO.findOne(_application.getId()) != null) {
			_result.addError( new ObjectError("id", "ID already exists"))
		}

		if (_result.hasErrors()) {
			def ModelAndView model2 = new ModelAndView(APPLICATION_EDIT)
			model2.addObject(APPLICATION, _application)
			return model2
		}

		EditApplicationCommand command = new EditApplicationCommand(_application)
		gate.dispatch(command)

		return controller.getHomePage()
	}
}
