/**
 *
 */
package com.tocea.corolla.widget.links.factory

import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.markup.html.link.AbstractLink
import org.apache.wicket.model.IModel

import com.tocea.corolla.widget.links.factory.api.ILinkFactory
import com.tocea.corolla.widget.links.factory.api.IUserAction
import com.tocea.corolla.widget.links.factory.api.IUserActionFactory

/**
 * This class is a factory to build basic ajaxlink on top of an
 * IUserActionFactory
 *
 * @author sleroy
 *
 */
class AjaxLinkUserActionFactory<T extends Serializable> implements ILinkFactory<T> {

	IUserActionFactory<T>	userActionFactory


	def AbstractLink newLink(final String _widgetID, final IModel<T> _object) {

		def link = new AjaxLink(_widgetID, _object) {

					IUserAction	newUserAction	= AjaxLinkUserActionFactory.this.userActionFactory.newUserAction(_object)

					def void onClick(final AjaxRequestTarget _target) {
						this.newUserAction.doAction(_target)

					}
				}
		return link
	}

}
