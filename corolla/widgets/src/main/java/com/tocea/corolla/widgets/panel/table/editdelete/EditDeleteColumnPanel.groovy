/**
 *
 */
package com.tocea.corolla.widgets.panel.table.editdelete;

import java.io.Serializable;

import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.tocea.corolla.widget.links.factory.api.ILinkFactory;

/**
 * @author sleroy
 *
 */
public class EditDeleteColumnPanel<T extends Serializable> extends Panel {

	private static final String	DELETE_BUTTON	= "delete";
	private static final String	EDIT_BUTTON		= "edit";
	private final Predicate<T>	deleteEnabled;
	private final Predicate<T>	editEnabled;

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
	public EditDeleteColumnPanel(final String id, final IModel<T> model,
			final ILinkFactory<T> _deleteLinkFactory,
			final ILinkFactory<T> _editLinkFactory) {

		this(id, model,_deleteLinkFactory, _editLinkFactory, (Predicate<T>) Predicates.alwaysTrue(), (Predicate<T>) Predicates.alwaysTrue());

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
	public EditDeleteColumnPanel(final String id, final IModel<T> model,
			final ILinkFactory<T> _deleteLinkFactory,
			final ILinkFactory<T> _editLinkFactory, final Predicate<T> _deleteEnabled, final Predicate<T> _editEnabled) {

		super(id, model);
		this.deleteEnabled = _deleteEnabled;
		this.editEnabled = _editEnabled;

		final IModel<T> modelObject =   (IModel<T>) this.getDefaultModel();

		final AbstractLink editLink = _editLinkFactory.newLink(EDIT_BUTTON, modelObject);
		editLink.setVisibilityAllowed(this.editEnabled.apply(modelObject.getObject()));
		editLink.setVisible(this.editEnabled.apply(modelObject.getObject()));
		this.add(editLink);
		final AbstractLink deleteLink = _deleteLinkFactory.newLink(DELETE_BUTTON, modelObject);
		deleteLink.setVisibilityAllowed(this.editEnabled.apply(modelObject.getObject()));
		deleteLink.setVisible(this.editEnabled.apply(modelObject.getObject()));
		this.add(deleteLink);

	}
}