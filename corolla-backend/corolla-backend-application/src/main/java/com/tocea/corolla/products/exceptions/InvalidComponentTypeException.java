/**
 *
 */
package com.tocea.corolla.products.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

/**
 * @author sleroy
 *
 */
public class InvalidComponentTypeException extends CorollaDomainException {
	public InvalidComponentTypeException() {
		super("An invalid product definition has been provided.");
	}
}
