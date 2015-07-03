package com.tocea.corolla.cqrs.gate;

public interface ICommandCallback<R> {
	/** Invoked when command handling execution resulted in an error. */
	void onFailure(Throwable cause);

	/** Invoked when command handling execution was successful. */
	void onSuccess(R result);

}
