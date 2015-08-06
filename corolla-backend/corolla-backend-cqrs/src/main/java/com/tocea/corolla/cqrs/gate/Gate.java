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
package com.tocea.corolla.cqrs.gate;

import java.util.concurrent.Future;

/**
 * Main access point to the Application.<br>
 * It handles:
 * <ul>
 * <li>filtering command duplicates
 * <li>command queues for asynchronous commands
 * </ul>
 *
 * @author Slawek
 * @author sleroy
 *
 */
public interface Gate {

	/**
	 * Dispatch a command and executes it sequentially.
	 *
	 * @param command
	 *            the command.
	 * @return the result of the command.
	 */
	public <R> R dispatch(Object command);
	
	/**
	 * Executes asynchronously .
	 *
	 * @param command
	 * @return
	 */
	public void dispatchAsync(Object command);
	
	/**
	 * Executes asynchronously and provides a way to obtain the result.
	 *
	 * @param command
	 * @return
	 */
	public <R> Future<R> executeAsync(Object command);
	
}