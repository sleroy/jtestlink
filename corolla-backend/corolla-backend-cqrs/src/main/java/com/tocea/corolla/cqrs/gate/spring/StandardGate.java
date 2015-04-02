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

@Component
public class StandardGate implements Gate {

	private static final Logger LOGGER = LoggerFactory.getLogger(StandardGate.class);

	@Autowired
	private RunEnvironment	runEnvironment;

	private final GateHistory		gateHistory	= new GateHistory();

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tocea.corolla.cqrs.gate.spring.Gate#dispatch(java.lang.Object)
	 */
	@Override
	public Object dispatch(final Object command) {
		if (!this.gateHistory.register(command)) {
			LOGGER.info("Duplicate command {}", command);
			return null;// skip duplicate
		}

		if (this.isAsynchronous(command)) {
			// TODO add to the queue. Queue should send this command to the
			// RunEnvironment
			return null;
		}
		LOGGER.info("Treating command {}", command);
		return this.runEnvironment.run(command);
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
				.getAnnotation(Command.class);
		return commandAnnotation.asynchronous();
	}

}
