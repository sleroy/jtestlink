/**
 *
 */
package com.tocea.corolla.widget.links.factory.api;

import java.io.Serializable;

import org.apache.wicket.model.IModel;

/**
 * This interface defines the user action factory. This factory builds a new
 * action for each model object provided.
 *
 * @author sleroy
 * @param <T>
 *            the type of object the user action is depending.
 *
 */
interface IUserActionFactory<T extends Serializable> extends Serializable {
	/**
	 * Builds a new User action.
	 *
	 * @return a new user action>
	 */
	IUserAction<T> newUserAction(IModel<T> _object);
}
