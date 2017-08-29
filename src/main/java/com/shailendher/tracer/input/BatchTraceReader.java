package com.shailendher.tracer.input;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import com.google.common.io.Closeables;
import com.shailendher.tracer.Settings;
import com.shailendher.tracer.input.iterator.FixedBatchSpliterator;

public class BatchTraceReader implements TraceReader, AutoCloseable {

	private final BufferedReader reader;

	private final int BATCH_SIZE;

	public BatchTraceReader(final Settings settings) {
		try {
			BATCH_SIZE = settings.getMode().READER_BATCH_SIZE;
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(settings.getInput().toFile())));
		} catch (final FileNotFoundException e) {
			//should not occur. Parameter and file is validated at start of application
			throw new RuntimeException("Unable to read file with configuration: " + settings);
		}
	}

	@Override
	public Stream<String> stream() {
		return FixedBatchSpliterator.withBatchSize(reader.lines(), BATCH_SIZE);
	}

	@Override
	public void close() {
		Closeables.closeQuietly(reader);
	}

	@Override
	protected void finalize() throws Throwable {
		close();
	}

}
