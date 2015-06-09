package com.tocea.corolla.cqrs.gate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmptyCommandCallback<R> implements ICommandCallback<R> {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(EmptyCommandCallback.class);

	public EmptyCommandCallback() {
	}

	@Override
	public void onFailure(final Throwable _cause) {
		LOGGER.error("Command has failed {}", _cause.getMessage(), _cause);

	}

	@Override
	public void onSuccess(final R _result) {
		//
	}

}
