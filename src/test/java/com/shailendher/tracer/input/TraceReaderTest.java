package com.shailendher.tracer.input;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.shailendher.tracer.Config;
import com.shailendher.tracer.Settings;
import com.shailendher.tracer.utils.TestDataUtils;

public class TraceReaderTest {

	@Test(expected = IllegalArgumentException.class)
	public void null_input() throws Exception {
		final TraceReader reader = TraceReader.create(null);
		assertThat(reader).isNull();
	}

	@Test
	public void default_settings_conservative() throws Exception {
		final Settings defaultSettings = TestDataUtils.getDefaultSettings();
		final TraceReader reader = TraceReader.create(defaultSettings);
		assertThat(reader).isNotNull().isInstanceOf(SimpleTraceReader.class);
	}

	@Test
	public void return_aggressive() throws Exception {
		final Settings defaultSettings = TestDataUtils.getDefaultSettings();
		defaultSettings.setMode(Config.AGGRESSIVE);
		final TraceReader reader = TraceReader.create(defaultSettings);
		assertThat(reader).isNotNull().isInstanceOf(BatchTraceReader.class);
	}
}
