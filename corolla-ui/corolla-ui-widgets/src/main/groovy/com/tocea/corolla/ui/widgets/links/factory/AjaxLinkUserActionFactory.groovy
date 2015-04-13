/**
 *
 */
package com.tocea.corolla.ui.widgets.links.factory

import java.io.Serializable;

import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.IModel;

import com.tocea.corolla.ui.widgets.links.factory.api.ILinkFactory;
import com.tocea.corolla.ui.widgets.links.factory.api.IUserActionFactory;

/**
 * This class is a factory to build basic ajaxlink on top of an
 * IUserActionFactory
 *
 * @author sleroy
 *
 */
class AjaxLinkUserActionFactory<T extends Serializable> implements ILinkFactory<T> {


	def IUserActionFactory<T>	userActionFactory

	def AbstractLink newLink(final String _widgetID, final IModel<T> _object) {

		return new AjaxUserLink<Serializable>(_widgetID, _object, userActionFactory)
	}
}
