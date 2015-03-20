/**
 *
 */
package com.tocea.corolla.widget.links.factory.api;

import java.io.Serializable;

import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.IModel;

/**
 * This interface defines a factory to build AjaxLinks.
 *
 * @author sleroy
 * @param <T>
 *            the the type of object manipulated by the ajax link.
 *
 */
public interface ILinkFactory<T extends Serializable> extends Serializable{
	/**
	 * Builds a new User action.
	 *
	 * @return a new user action>
	 */
	AbstractLink newLink(String _widgetID, IModel<T> _object);
}
