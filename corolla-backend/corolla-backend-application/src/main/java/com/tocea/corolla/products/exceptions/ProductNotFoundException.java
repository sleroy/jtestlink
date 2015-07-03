/**
 *
 */
package com.tocea.corolla.products.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

/**
 * @author sleroy
 *
 */
public class ProductNotFoundException extends CorollaDomainException {
	public ProductNotFoundException() {
		super("An invalid product definition has been provided.");
	}
}
