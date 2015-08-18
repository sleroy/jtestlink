package com.tocea.corolla.users.validation;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.github.fakemongo.impl.aggregation.Project;

public interface IUserAuthorization {

	/**
	 * VRAI // Quand Accès Lecture
	 * 
	 * @return
	 */
	boolean hasPortfolioReadAccess();

	boolean hasPorfolioWriteAccess();

	boolean canCreateOrDeleteProjects();

	boolean canEditProject(String _projectID);

	boolean canReadProject(String _projectID);

	boolean hasRequirementReadAccess(String _projectID);

	boolean hasRequirementWriteAccess(String _projectID);

	boolean hasRequirementReportAccess(String _projectID);

	boolean hasAdminAccess(String _projectID);

	boolean hasSystemConfigurationAccess(String _projectID);
	
	boolean hasCustomPermission(String _permission);
	
	Iterator<String> filterAllowedProjects(Iterator<String> _projects, List<String> permissions);

}

interface IuserLowAuthorization {

	// @Autowired
	// Authentication

	boolean hasPortofolioPermission(PortfolioPermission _permission);

	boolean hasProjectPermission(Project _project, ProjectPermission _permission);
}

interface UserPermissions {
	// UserPermissions ()
	// UserPermissions (Authentication _auth)
	// UserPermissions (Authentication _auth, Project _project)

	Set<String> getPermissions();

	boolean hasPermission(String _permission);
}

enum PortfolioPermission {
	READ, WRITE
}

enum ProjectPermission {
	CREATE, DELETE, EDIT, READ
}

enum RequirementPermission {
	READ, CHANGELOG, REPORT
}