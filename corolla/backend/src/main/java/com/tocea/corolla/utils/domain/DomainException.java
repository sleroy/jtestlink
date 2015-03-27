/**
 *
 */
package com.tocea.corolla.utils.domain;

/**
 * This exception is a wrapper for exceptions produced inside the domain (pojo, dao, services)
 * @author sleroy
 *
 */
public class DomainException extends RuntimeException {

	/**
	 *
	 */
	private static final String	DOMAIN_EXCEPTION	= "Domain Exception : ";

	/**
	 * Domain exception.
	 */
	public DomainException() {
		this("");
	}

	/**
	 * @param _message
	 */
	public DomainException(final String _message) {
		this(_message, null);
	}

	/**
	 *
	 * @param _message
	 * @param _cause
	 */
	public DomainException(final String _message, final Throwable _cause) {
		super(DOMAIN_EXCEPTION + _message, _cause);
	}




}
