package com.tocea.corolla.views

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.view.RedirectView

@Controller
public class HomeController {


	@RequestMapping("/")
	public RedirectView index() {

		return new RedirectView("/ui/index.html")
	}

}
