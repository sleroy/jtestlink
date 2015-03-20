/**
 *
 */
package com.tocea.corolla.widget.links.factory.api;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * This interface defines a user action expected to be stateless. It cannot keep
 * a state since this instante is shared between many component.
 *
 *
 * @author sleroy
 *
 */
public interface IStatelessUserAction<T> extends Serializable {

	public void doAction(AjaxRequestTarget _target, T _value);
}
