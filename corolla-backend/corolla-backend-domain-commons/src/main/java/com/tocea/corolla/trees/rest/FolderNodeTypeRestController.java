package com.tocea.corolla.trees.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.trees.commands.DeleteFolderNodeTypeCommand;
import com.tocea.corolla.trees.dao.IFolderNodeTypeDAO;
import com.tocea.corolla.trees.domain.FolderNodeType;
import com.tocea.corolla.users.domain.Permission;

@RestController
@RequestMapping("/rest/trees/folders/types")
public class FolderNodeTypeRestController {

	@Autowired
	private IFolderNodeTypeDAO folderNodeTypeDAO;
	
	@Autowired
	private Gate gate;
	
	@RequestMapping(value = "/all")
	public Collection<FolderNodeType> findAll() {
		
		return folderNodeTypeDAO.findAll();
	}
	
	@Secured(Permission.ADMIN)
	@RequestMapping(value = "/delete/{typeID}")
	public FolderNodeType delete(@PathVariable String typeID) {
		
		return gate.dispatch(new DeleteFolderNodeTypeCommand(typeID));
	}
	
}
