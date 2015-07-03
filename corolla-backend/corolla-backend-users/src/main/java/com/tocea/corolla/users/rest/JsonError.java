/**
 *
 */
package com.tocea.corolla.users.rest;

/**
 * @author sleroy
 *
 */
public class JsonError {
	private String	errorMessage;

	public JsonError() {
		super();
	}

	/**
	 * @param _string
	 */
	public JsonError(final String _string) {
		this.errorMessage = _string;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}

	/**
	 * @param _errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(final String _errorMessage) {
		this.errorMessage = _errorMessage;
	}
}
