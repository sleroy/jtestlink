/**
 *
 */
package com.tocea.corolla.ui.views.application

import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider
import org.apache.wicket.model.IModel

import com.tocea.corolla.ui.rest.api.IRestAPI
import com.tocea.corolla.ui.widgets.treeItem.TreeItem

/**
 * @author sleroy
 *
 */
class ApplicationTreeProvider implements ITreeProvider<TreeItem> {

	IRestAPI restAPI

	public ApplicationTreeProvider(IRestAPI _restAPI) {
		super()
		restAPI = _restAPI
	}

	@Override
	public void detach() {
		treeItems = null
	}

	def List<TreeItem> treeItems


	@Override
	public Iterator<? extends TreeItem> getRoots() {
		if (treeItems == null) {
			treeItems =restAPI.getProductRoots()
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
