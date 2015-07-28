package com.tocea.corolla.trees.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tocea.corolla.trees.dao.IFolderNodeTypeDAO;
import com.tocea.corolla.trees.domain.FolderNodeType;

@RestController
@RequestMapping("/rest/trees/folders/types")
public class FolderNodeTypeRestController {

	@Autowired
	private IFolderNodeTypeDAO folderNodeTypeDAO;
	
	@RequestMapping(value = "/all")
	public Collection<FolderNodeType> findAll() {
		
		return folderNodeTypeDAO.findAll();
	}
	
}
