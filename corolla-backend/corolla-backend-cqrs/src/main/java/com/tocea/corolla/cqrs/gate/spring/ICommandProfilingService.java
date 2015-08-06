package com.tocea.corolla.cqrs.gate.spring;

public interface ICommandProfilingService {
	
	<R> ICommandCallback<R> decorate(Object _command, ICommandCallback<R> _callback);
	
}
