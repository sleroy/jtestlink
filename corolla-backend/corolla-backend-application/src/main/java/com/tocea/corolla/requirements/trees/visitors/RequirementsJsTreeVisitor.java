package com.tocea.corolla.requirements.trees.visitors;

import java.util.Collection;

import com.tocea.corolla.requirements.domain.Requirement;
import com.tocea.corolla.requirements.trees.domain.RequirementNode;
import com.tocea.corolla.requirements.trees.utils.RequirementsTreeUtils;
import com.tocea.corolla.requirements.utils.RequirementUtils;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.visitors.JsTreeNodeVisitor;

public class RequirementsJsTreeVisitor extends JsTreeNodeVisitor {

	private Collection<Requirement> requirements;
	
	public RequirementsJsTreeVisitor(Collection<Requirement> requirements) {
		super();
		this.requirements = requirements;
	}
	
	@Override
	public void visit(TreeNode node) {
		
		super.visit(node);
		
		if (RequirementsTreeUtils.isRequirementNode(node)) {
			
			String ID = ((RequirementNode)node).getRequirementId();
			result.getA_attr().put("data-requirementID", ID);

			if (this.requirements != null) {
				
				result.setText(requirements.size()+"");
				Requirement requirement = RequirementUtils.findByID(requirements, ID);

				if (requirement != null) {
					result.setText(requirement.getName());
					result.getA_attr().put("data-key", requirement.getKey());
				}
				
			}
			
		}
		
	}
	
	@Override
	protected JsTreeNodeVisitor getChildVisitor() {		
		return new RequirementsJsTreeVisitor(requirements);		
	}
	
}
