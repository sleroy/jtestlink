/**
 *
 */
package com.tocea.corolla.ui.widgets.links.factory

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.kendo.ui.markup.html.link.AjaxLink;
import com.tocea.corolla.ui.widgets.links.factory.api.IUserActionFactory;

/**
 * @author sleroy
 *
 */
public class AjaxUserLink<T extends Serializable> extends AjaxLink<T>{

	def IUserActionFactory<T>	userActionFactory

	AjaxUserLink(String _id, IModel<T> _model, IUserActionFactory<T> _userActionFactory) {
		super(_id, _model)
		userActionFactory = _userActionFactory
	}


	/* (non-Javadoc)
	 * @see org.apache.wicket.ajax.markup.html.AjaxLink#onClick(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	public void onClick(final AjaxRequestTarget _target) {
		def	newUserAction	= userActionFactory.newUserAction getDefaultModel()
		newUserAction.doAction _target
	}
}
