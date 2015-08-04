/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 */
package com.tocea.corolla.cqrs.gate.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.cqrs.gate.ICommandCallback;

@Component
public class StandardGate implements Gate {

	private static final Logger	LOGGER		= LoggerFactory.getLogger(StandardGate.class);

	@Autowired
	private RunEnvironment		runEnvironment;

	// @Autowired
	// private RunEnvironment runEnvironment;

	private final GateHistory	gateHistory	= new GateHistory();

	@Override
	public <R> R dispatch(final Object _command) {
		if (this.isAsynchronous(_command)) {
			throw new InvalidCommandException(_command);
		}
		final ThreadLocal<R> threadLocal = new ThreadLocal<R>();

		this.dispatch(_command, new ICommandCallback<R>() {

			@Override
			public void onFailure(final Throwable _cause) {
				throw new CommandExecutionException(_cause);

			}

			@Override
			public void onSuccess(final R _result) {
				threadLocal.set(_result);

			}
		});
		return threadLocal.get();
	}

	@Override
	public <R> void dispatch(final Object command,
			final ICommandCallback<R> _callBack) {
		if (!this.gateHistory.register(command)) {
			LOGGER.warn("Duplicate command {}", command);
			return;
		}

		if (this.isAsynchronous(command)) {
			// TODO add to the queue. Queue should send this command to the
			// RunEnvironment
			// asyncQueue.push(command, _callBack);
		} else {
			LOGGER.info("Treating command {}", command);
			this.runEnvironment.run(command, _callBack);
		}

	}

	/**
	 * @param command
	 * @return
	 */
	private boolean isAsynchronous(final Object command) {
		if (!command.getClass().isAnnotationPresent(Command.class)) {
			return false;
		}

		final Command commandAnnotation = command.getClass()
				.getAnnotation(	Command.class);
		return commandAnnotation.asynchronous();
	}

}
