/**
 *
 */
package com.tocea.corolla.widget.links.factory;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.IModel;

import com.tocea.corolla.widget.links.factory.api.ILinkFactory;
import com.tocea.corolla.widget.links.factory.api.IUserAction;
import com.tocea.corolla.widget.links.factory.api.IUserActionFactory;

/**
 * This class is a factory to build basic ajaxlink on top of an
 * IUserActionFactory
 *
 * @author sleroy
 *
 */
public class AjaxLinkUserActionFactory<T extends Serializable> implements ILinkFactory<T> {

	private final IUserActionFactory<T>	userActionFactory;

	public AjaxLinkUserActionFactory(
			final IUserActionFactory<T> _userActionFactory) {
		this.userActionFactory = _userActionFactory;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.komea.product.wicket.widget.ajaxlink.api.IAjaxLinkFactory#newAjaxLink(java
	 * .lang.String, java.lang.Object)
	 */
	@Override
	public AbstractLink newLink(final String _widgetID, final IModel<T> _object) {

		return new AjaxLink<T>(_widgetID, _object) {

			private final IUserAction<T>	newUserAction	= AjaxLinkUserActionFactory.this.userActionFactory.newUserAction(_object);

			@Override
			public void onClick(final AjaxRequestTarget _target) {
				this.newUserAction.doAction(_target);

			}
		};
	}

}
