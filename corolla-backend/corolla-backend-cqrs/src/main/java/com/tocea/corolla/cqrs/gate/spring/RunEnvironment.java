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

import com.tocea.corolla.cqrs.gate.ICommandCallback;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.utils.domain.ObjectValidation;

/**
 * This class prepares the command to be executed. It can override the default
 * command handler with a wrapper with enhanced functionalities.
 *
 * @author Slawek
 */
@Component
public class RunEnvironment {

	public interface HandlersProvider {
		ICommandHandler<Object, Object> getHandler(Object command);
	}

	private static final Logger	LOGGER	= LoggerFactory.getLogger(RunEnvironment.class);

	@Autowired
	private HandlersProvider	handlersProvider;

	/**
	 * Executes a command in synchronous way.
	 *
	 * @param command
	 *            the command
	 * @param _callback
	 *            the callback
	 */
	public <R> void run(final Object command,
			final ICommandCallback<R> _callback) {
		final ICommandHandler<Object, Object> handler = this.handlersProvider.getHandler(command);

		// You can add Your own capabilities here: dependency injection,
		// security, transaction management, logging, profiling, spying, storing
		// commands, etc
		try {
			final ObjectValidation objectValidation = new ObjectValidation();
			if (!objectValidation.isValid(command)) {
				throw new InvalidCommandException(command);
			}
			final Object result = handler.handle(command);
			_callback.onSuccess((R) result);

		} catch (final Throwable e) {
			LOGGER.error("The command has failed {}", e);
			_callback.onFailure(e);
		}
	}
}
