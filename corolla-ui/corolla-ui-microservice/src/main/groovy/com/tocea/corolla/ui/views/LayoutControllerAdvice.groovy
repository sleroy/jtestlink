package com.tocea.corolla.ui.views;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@PropertySource("artifact.properties")
public class LayoutControllerAdvice {

	@Value('${artifactBuildNumber}')
	private String artifactBuildNumber;
	
	@ModelAttribute('artifactBuildNumber')
	public String version() {
		return artifactBuildNumber
	}
	
}
