/**
 *
 */
package com.tocea.corolla.cqrs.gate;

/**
 * This exception is a wrapper for exceptions produced inside the domain (pojo, dao, services)
 * @author sleroy
 *
 */
public class CqrsException extends RuntimeException {

	/**
	 *
	 */
	private static final String	DOMAIN_EXCEPTION	= "CQRS Exception : ";

	/**
	 * Domain exception.
	 */
	public CqrsException() {
		this("");
	}

	/**
	 * @param _message
	 */
	public CqrsException(final String _message) {
		this(_message, null);
	}

	/**
	 *
	 * @param _message
	 * @param _cause
	 */
	public CqrsException(final String _message, final Throwable _cause) {
		super(DOMAIN_EXCEPTION + _message, _cause);
	}

	public CqrsException(Throwable cause) {
		this(cause.getMessage(), cause);
	}


}
