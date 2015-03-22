/**
 *
 */
package com.tocea.corolla.widgets.panel.table.editdelete

import org.apache.wicket.markup.html.link.AbstractLink
import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.model.IModel

import com.google.common.base.Predicate
import com.google.common.base.Predicates

/**
 * @author sleroy
 *
 */
class EditDeleteColumnPanel<T extends Serializable> extends Panel {

	private static final String	DELETE_BUTTON	= "delete"
	private static final String	EDIT_BUTTON		= "edit"
	Predicate<T>	deleteEnabled
	Predicate<T>	editEnabled

	/**
	 * Builds a new panel to be inserted into the table.
	 *
	 * @param id
	 *            component id
	 * @param model
	 *            model containing the data of the row
	 * @param _deleteLinkFactory
	 *            user action to perform on delete click
	 * @param _editLinkFactory
	 *            user action to perform on edit click
	 *
	 */
	public EditDeleteColumnPanel(id, model,	_deleteLinkFactory,	_editLinkFactory) {

		this(id, model,_deleteLinkFactory, _editLinkFactory, (Predicate<T>) Predicates.alwaysTrue(), (Predicate<T>) Predicates.alwaysTrue())
	}


	/**
	 * Builds a new panel to be inserted into the table.
	 *
	 * @param id
	 *            component id
	 * @param model
	 *            model containing the data of the row
	 * @param _deleteLinkFactory
	 *            user action to perform on delete click
	 * @param _editLinkFactory
	 *            user action to perform on edit click
	 *
	 */
	public EditDeleteColumnPanel(id, model,	_deleteLinkFactory,	_editLinkFactory, _deletePredicateEnabled, _editPredicateEnabled) {

		super(id, model)
		this.deleteEnabled = _deletePredicateEnabled
		this.editEnabled = _editPredicateEnabled

		final IModel<T> modelObject =   (IModel<T>) this.getDefaultModel()

		final AbstractLink editLink = _editLinkFactory.newLink(EDIT_BUTTON, modelObject)
		editLink.setVisibilityAllowed(this.editEnabled.apply(modelObject.getObject()))
		editLink.setVisible(this.editEnabled.apply(modelObject.getObject()))
		this.add(editLink)
		final AbstractLink deleteLink = _deleteLinkFactory.newLink(DELETE_BUTTON, modelObject)
		deleteLink.setVisibilityAllowed(this.editEnabled.apply(modelObject.getObject()))
		deleteLink.setVisible(this.editEnabled.apply(modelObject.getObject()))
		this.add(deleteLink)
	}
}