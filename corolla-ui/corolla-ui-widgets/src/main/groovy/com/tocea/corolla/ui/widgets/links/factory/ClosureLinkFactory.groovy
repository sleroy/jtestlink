/**
 *
 */
package com.tocea.corolla.ui.widgets.links.factory

import groovy.transform.Canonical

import org.apache.wicket.markup.html.link.AbstractLink
import org.apache.wicket.model.IModel

import com.tocea.corolla.ui.widgets.links.factory.api.ILinkFactory

/**
 * @author sleroy
 *
 */
@Canonical
public class ClosureLinkFactory<T extends Serializable> implements
ILinkFactory<T> {

	def Closure<AbstractLink> closure


	/* (non-Javadoc)
	 * @see com.tocea.corolla.ui.widgets.links.factory.api.ILinkFactory#newLink(java.lang.String, org.apache.wicket.model.IModel)
	 */
	@Override
	public AbstractLink newLink(final String _widgetID, final IModel<T> _object) {

		closure.doCall _widgetID, _object
	}
}
