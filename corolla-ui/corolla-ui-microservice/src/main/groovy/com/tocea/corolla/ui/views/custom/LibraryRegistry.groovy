package com.tocea.corolla.ui.views.custom

import groovy.util.logging.Slf4j

/**
 * This class defines a component that store all the additional css libraries.
 * @author sleroy
 *
 */
@Slf4j
class LibraryRegistry {
	
	def List<String> pathLibrary = new ArrayList<String>()
	
	/**
	 * Adds a new path to the library.
	 * @param _library the library
	 */
	public void addPath(String _libraryPath) {
		log.debug("Adding a Library path {}", _libraryPath)
		pathLibrary.add _libraryPath
	}
	
	/**
	 * Adds a list of path to the library.
	 * @param _libraryPaths
	 */
	public void addPaths(List<String> _libraryPaths) {
		pathLibrary.addAll _libraryPaths
	}
}
