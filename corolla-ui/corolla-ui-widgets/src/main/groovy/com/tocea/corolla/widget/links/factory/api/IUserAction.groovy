/**
 *
 */
package com.tocea.corolla.widget.links.factory.api;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * This interface defines a user action expected to be performed when a button
 * is clicked. The user action must provides two informations.
 * <ul>
 * <li>What is the expected behaviour when the button is clicked ?
 * <li>
 * <li>is there any graphical alterations depending of the context ?</li>
 * </ul>
 *
 * @author sleroy
 *
 */
interface IUserAction<T extends Serializable> extends Serializable {

	void doAction(AjaxRequestTarget _target);
}
