package com.tocea.corolla.cqrs.gate.spring;

import java.util.concurrent.Callable;

public interface ICommandCallback<R> extends Callable<R> {

}
