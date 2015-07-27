package com.tocea.corolla.products.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.commands.EditProjectBranchCommand;
import com.tocea.corolla.products.dao.IProjectBranchDAO;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.InvalidProjectBranchInformationException;
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;

@CommandHandler
@Transactional
public class EditProjectBranchCommandHandler implements ICommandHandler<EditProjectBranchCommand, ProjectBranch> {

	@Autowired
	private IProjectBranchDAO branchDAO;
	
	@Override
	public ProjectBranch handle(@Valid EditProjectBranchCommand command) {
		
		ProjectBranch branch = command.getBranch();
		
		if (branch == null) {
			throw new MissingProjectBranchInformationException("No data provided to edit a project branch");
		}
		
		if (StringUtils.isEmpty(branch.getId())) {
			throw new InvalidProjectBranchInformationException("No ID found");
		}
		
		branchDAO.save(branch);
		
		return branch;
	}
	
}
