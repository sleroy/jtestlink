package com.tocea.corolla.cqrs.gate.spring;

import com.tocea.corolla.cqrs.gate.CqrsException;

public class CommandExecutionException extends CqrsException {

	public CommandExecutionException(Throwable cause) {
		super(cause);
	}
	
}
