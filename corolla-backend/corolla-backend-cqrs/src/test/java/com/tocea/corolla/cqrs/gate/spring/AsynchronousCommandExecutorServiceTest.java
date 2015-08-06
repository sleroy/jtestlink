package com.tocea.corolla.cqrs.gate.spring;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tocea.corolla.cqrs.gate.conf.CorollaCqrsConfiguration;

@RunWith(MockitoJUnitRunner.class)
public class AsynchronousCommandExecutorServiceTest {

	private static final int VALUE_3 = 3;

	private static final int VALUE_2 = 2;

	private static final int VALUE_1 = 1;

	private static final String COMMAND3 = "GNU";

	private static final String COMMAND2 = "GNA";

	private static final String COMMAND1 = "GNI";

	private static final CorollaCqrsConfiguration CONFIGURATION = new CorollaCqrsConfiguration();

	@Mock
	private SequentialCommandExecutorService sequentialCommandExecutorService;

	@InjectMocks
	private final AsynchronousCommandExecutorService asynchronousCommandExecutorService = new AsynchronousCommandExecutorService(
	        CONFIGURATION);

	@Test
	public void testInitAsyncEngine() throws Exception {
		asynchronousCommandExecutorService.initAsyncEngine();
		asynchronousCommandExecutorService.postDestroy();
	}

	@Test
	public void testSubmitCommand() throws Exception {
		asynchronousCommandExecutorService.initAsyncEngine();

		when(sequentialCommandExecutorService.run(COMMAND1)).thenReturn(Integer.valueOf(VALUE_1));
		when(sequentialCommandExecutorService.run(COMMAND2)).thenReturn(Integer.valueOf(VALUE_2));
		when(sequentialCommandExecutorService.run(COMMAND3)).thenReturn(Integer.valueOf(VALUE_3));

		final Future<Object> ob1 = asynchronousCommandExecutorService.submitCommand(COMMAND1);
		final Future<Object> ob2 = asynchronousCommandExecutorService.submitCommand(COMMAND2);
		final Future<Object> ob3 = asynchronousCommandExecutorService.submitCommand(COMMAND3);

		while (!ob1.isDone() && !ob2.isDone() && !ob3.isDone()) {
			Thread.sleep(10);
		}
		assertEquals(Integer.valueOf(VALUE_1), ob1.get());
		assertEquals(Integer.valueOf(VALUE_2), ob2.get());
		assertEquals(Integer.valueOf(VALUE_3), ob3.get());
		asynchronousCommandExecutorService.postDestroy();
	}

}
