/**
 *
 */
package com.tocea.corolla.products.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

/**
 * @author sleroy
 *
 */
public class InvalidProductException extends CorollaDomainException {
	public InvalidProductException() {
		super("An invalid product definition has been provided.");
	}
}
