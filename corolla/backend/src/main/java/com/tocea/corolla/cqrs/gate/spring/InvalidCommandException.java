/**
 *
 */
package com.tocea.corolla.cqrs.gate.spring;

import com.tocea.corolla.cqrs.gate.CqrsException;

/**
 * @author sleroy
 *
 */
public class InvalidCommandException extends CqrsException {
	/**
	 *
	 */
	public InvalidCommandException(final Object _command) {
		super("Command invalid : " + _command);
	}
}
