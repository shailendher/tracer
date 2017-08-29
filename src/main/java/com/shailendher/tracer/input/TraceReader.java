package com.shailendher.tracer.input;

import java.util.Optional;
import java.util.stream.Stream;

import com.shailendher.tracer.Config;
import com.shailendher.tracer.Settings;

public interface TraceReader {

	Stream<String> stream();

	static TraceReader create(final Settings settings) {
		final Config config = Optional.ofNullable(settings).map(Settings::getMode).orElseThrow(() -> new IllegalArgumentException("Invalid settings: " + settings));
		switch (config) {
			case CONSERVATIVE:
				return new SimpleTraceReader(settings);
			case AGGRESSIVE:
				return new BatchTraceReader(settings);
			default:
				throw new IllegalArgumentException("Invalid mode: " + settings);
		}
	}

}
