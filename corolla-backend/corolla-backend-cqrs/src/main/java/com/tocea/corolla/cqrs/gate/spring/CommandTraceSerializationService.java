package com.tocea.corolla.cqrs.gate.spring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tocea.corolla.cqrs.gate.conf.CorollaCqrsConfiguration;

/**
 * This command listener provides a facility to log and serialize every actions
 * executed by Corolla in order to replay them in tests.
 *
 * @author sleroy
 *         
 */
@Service
public class CommandTraceSerializationService implements ICommandExecutionListener {
	public static class CommandTrace {
		private List<Object> commands = new ArrayList<>();
		
		public void addCommand(final Object _command) {
			commands.add(_command);
		}
		
		public List<Object> getCommands() {
			return commands;
		}
		
		public void setCommands(final List<Object> _commands) {
			commands = _commands;
		}
		
		@Override
		public String toString() {
			return "CommandTrace [commands=" + commands + "]";
		}
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommandTraceSerializationService.class);
	
	@Autowired
	private CorollaCqrsConfiguration configuration;
	
	private ObjectMapper objectMapper;
	
	public CommandTraceSerializationService() {
		super();
	}
	
	public CommandTraceSerializationService(final CorollaCqrsConfiguration _corollaCqrsConfiguration) {
		configuration = _corollaCqrsConfiguration;
		
	}
	
	@PostConstruct
	public void init() {
		objectMapper = new ObjectMapper();
		try {
			if (configuration.getTraceFile().createNewFile()) {
				final CommandTrace trace = new CommandTrace();
				objectMapper.writeValue(configuration.getTraceFile(), trace);
			}
		} catch (final IOException e) {
			LOGGER.error("Could not create the trace, already existing");
		}
	}
	
	@Override
	public void onFailure(final Object _command, final Throwable _cause) {
		if (configuration.isTracingEnabled()) {
			serializeTrace(_command);
		}
		
	}
	
	@Override
	public void onSuccess(final Object _command, final Object _result) {
		if (configuration.isTracingEnabled()) {
			serializeTrace(_command);
		}
		
	}
	
	private void serializeTrace(final Object _command) {
		try {
			final CommandTrace trace = objectMapper.readValue(configuration.getTraceFile(), CommandTrace.class);
			trace.addCommand(_command);
			objectMapper.writeValue(configuration.getTraceFile(), trace);
		} catch (final Throwable e) {
			LOGGER.error("Error during the serialization of the command {} -> {}", configuration.getTraceFile(),
			        _command, e);
		}
	}
	
}
