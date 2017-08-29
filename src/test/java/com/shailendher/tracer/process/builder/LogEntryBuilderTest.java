package com.shailendher.tracer.process.builder;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.shailendher.tracer.domain.LogEntry;

public class LogEntryBuilderTest {

	private static final String SAMPLE_LOG_LINE = "2013-10-23T10:12:35.298Z 2013-10-23T10:12:35.300Z eckakaau service3 d6m3shqy->62d45qeh";

	private static final String MALFORMED_LOG_LINE = "Hello world!";

	private static final LogEntry EXPECTED_LOG_ENTRY = LogEntry.builder().start("2013-10-23T10:12:35.298Z").end("2013-10-23T10:12:35.300Z").id("eckakaau").service("service3")
			.source("d6m3shqy").target("62d45qeh").build();

	@Test
	public void standard_input() {
		//when
		final LogEntry output = LogEntryBuilder.convert(SAMPLE_LOG_LINE);
		//then
		assertThat(output).isNotNull().isEqualTo(EXPECTED_LOG_ENTRY);
	}

	@Test
	public void empty_or_null_input() {
		//when
		final LogEntry output = LogEntryBuilder.convert(null);
		//then
		assertThat(output).isNotNull();
		assertThat(output.isValid()).isFalse();
	}

	@Test
	public void malformed_input() {
		//when
		final LogEntry output = LogEntryBuilder.convert(MALFORMED_LOG_LINE);
		//then
		assertThat(output).isNotNull();
		assertThat(output.isValid()).isFalse();
	}

	@Test
	public void ignore_additional_input() {
		//given
		final String inputWithExtraEntries = SAMPLE_LOG_LINE + " extra1`extra2";
		//when
		final LogEntry output = LogEntryBuilder.convert(inputWithExtraEntries);
		//then
		assertThat(output).isNotNull().isEqualTo(EXPECTED_LOG_ENTRY);
	}

}
