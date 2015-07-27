package com.tocea.corolla.trees.visitors;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;
import com.tocea.corolla.trees.domain.TextNode;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.dto.JsTreeNodeDTO;

public class JsTreeNodeVisitor implements ITreeNodeVisitor {

	protected JsTreeNodeDTO result;
	
	public JsTreeNodeVisitor() {
		
	}
	
	@Override
	public void visit(TreeNode node) {
		
		result = new JsTreeNodeDTO();
		
		if (node.getClass().equals(TextNode.class)) {
			result.setText(((TextNode)node).getText());
		}	
		
		result.setA_attr(new HashMap<String, String>());
		result.getA_attr().put("data-nodeID", node.getId().toString());

		List<JsTreeNodeDTO> children = Lists.newArrayList();
		
		for(TreeNode child : node.getNodes()) {
			
			JsTreeNodeVisitor visitor = getChildVisitor();
			child.accept(visitor);
			
			children.add(visitor.getResult());		
			
		}
		
		result.setChildren(children);
		
	}

	public JsTreeNodeDTO getResult() {
		return result;
	}
	
	protected JsTreeNodeVisitor getChildVisitor() {		
		return new JsTreeNodeVisitor();		
	}

}