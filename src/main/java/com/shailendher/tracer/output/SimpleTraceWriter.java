package com.shailendher.tracer.output;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

import com.google.common.io.Closeables;
import com.shailendher.tracer.Settings;
import com.shailendher.tracer.util.Statistics;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleTraceWriter implements TraceWriter, AutoCloseable {

	private final BufferedWriter writer;

	public SimpleTraceWriter(final Settings settings) {
		try {
			writer = Files.newBufferedWriter(settings.getOutput(), StandardOpenOption.CREATE);
		} catch (final IOException e) {
			throw new RuntimeException("Unable to create output file for path: " + settings.getOutput());
		}
	}

	@Override
	public void send(final List<String> lines) {
		lines.forEach(str -> {
			try {
				//write method is thread-safe
				writer.write(str + System.lineSeparator());
				//with multiple write methods, ordering is not guaranteed
				//writer.newLine();
			} catch (final IOException e) {
				log.error("Error : {} for entries: {}", e, lines);
			}
		});
		Statistics.valid(lines.size());
	}

	@Override
	public void flush() {
		try {
			writer.flush();
		} catch (final IOException e) {
			log.error("Error while flushing: {}", e);
		}
	}

	@Override
	public void close() {
		try {
			Closeables.close(writer, true);
		} catch (final IOException e) {
			// do nothing
		}
	}

	@Override
	protected void finalize() throws Throwable {
		close();
	}

}
