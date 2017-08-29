package com.shailendher.tracer.output;

import java.util.List;

import com.shailendher.tracer.Settings;

public interface TraceWriter {

	void send(final List<String> lines);

	void flush();

	static TraceWriter create(Settings settings) {
		switch (settings.getMode()) {
		case CONSERVATIVE:
		case AGGRESSIVE:
			return new SimpleTraceWriter(settings);
		default:
			throw new IllegalArgumentException("Invalid settings: " + settings);
		}
	}

}
