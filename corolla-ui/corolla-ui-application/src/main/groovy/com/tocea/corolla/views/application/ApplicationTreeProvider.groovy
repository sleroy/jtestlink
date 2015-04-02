/**
 *
 */
package com.tocea.corolla.views.application

import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model

import com.tocea.corolla.views.api.IViewAPI
import com.tocea.corolla.widget.treeItem.TreeItem

/**
 * @author sleroy
 *
 */
class ApplicationTreeProvider implements ITreeProvider<TreeItem> {

	IViewAPI viewAPI

	public ApplicationTreeProvider(IViewAPI _viewAPI) {
		super()
		viewAPI = _viewAPI
	}

	@Override
	public void detach() {
		treeItems = null
	}

	def List<TreeItem> treeItems


	@Override
	public Iterator<? extends TreeItem> getRoots() {
		if (treeItems == null) {
			treeItems =viewAPI.getProductRoots()
		}
		return treeItems.iterator()
	}

	@Override
	public boolean hasChildren(TreeItem _node) {
		return false
	}

	@Override
	public Iterator<? extends TreeItem> getChildren(TreeItem _node) {
		return null
	}

	@Override
	public IModel<TreeItem> model(TreeItem _object) {
		Model.of _object
	}
}
