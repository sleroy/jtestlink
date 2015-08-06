package com.tocea.corolla.cqrs.gate.spring;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SpringGateTest {
	private static final String COMMAND = "GNI";
	
	@Mock
	private AsynchronousCommandExecutorService asynchronousCommandExecutorService;

	@Mock
	private SequentialCommandExecutorService sequentialCommandExecutorService;
	
	@InjectMocks
	private SpringGate springGate;
	
	@Test
	public void testDispatch() throws Exception {
		springGate.dispatch(COMMAND);
		verify(sequentialCommandExecutorService, Mockito.times(1)).run(COMMAND);
	}

	@Test
	public void testDispatchAsync() throws Exception {
		springGate.dispatchAsync(COMMAND);
		verify(asynchronousCommandExecutorService, Mockito.times(1)).submitCommand(COMMAND);
	}

	@Test
	public void testExecuteAsync() throws Exception {
		springGate.executeAsync(COMMAND);
		verify(asynchronousCommandExecutorService, Mockito.times(1)).submitCommand(COMMAND);
	}
	
}
