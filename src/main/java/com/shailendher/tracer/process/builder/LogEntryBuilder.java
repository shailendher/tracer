package com.shailendher.tracer.process.builder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import com.shailendher.tracer.domain.InvalidInputException;
import com.shailendher.tracer.domain.LogEntry;
import com.shailendher.tracer.util.Statistics;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogEntryBuilder {

	private final static String DELIMITER = " ";
	private final static String CALLER_DELIMITER = "->";

	public static LogEntry convert(final String line) {
		LogEntry entry = new LogEntry();
		try {
			final String[] fields = Optional.ofNullable(line).orElse("").split(DELIMITER);
			if (fields.length < 5) {
				throw new InvalidInputException("Line does not have expected number of fields");
			}
			final String[] route = fields[4].split(CALLER_DELIMITER);
			entry = LogEntry.builder().start(fields[0]).end(fields[1]).id(fields[2]).service(fields[3])
					.source(route[0]).target(route[1]).build();
			//TODO: validate output with bean validation before returning it
		} catch (RuntimeException e) {
			//TODO: set error details in logentry for use in error reporting
			log.error("Error : {} for input: {}", e, line);
			Statistics.invalid();
		}
		return entry;
	}

	@SuppressWarnings("unused")
	private static LocalDateTime parseLocalDate(final String input) {
		return LocalDateTime.ofInstant(Instant.parse(input), ZoneId.systemDefault());
	}

}
