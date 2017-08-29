package com.shailendher.tracer.process;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.shailendher.tracer.Config;
import com.shailendher.tracer.domain.LogEntry;
import com.shailendher.tracer.input.TraceReader;
import com.shailendher.tracer.output.TraceWriter;
import com.shailendher.tracer.process.builder.JsonBuilder;
import com.shailendher.tracer.process.builder.LogEntryBuilder;
import com.shailendher.tracer.process.builder.TraceBuilder;

public class TraceProcessor implements EventListener<String, LogEntry> {

	private final TraceReader reader;

	private final TraceWriter writer;

	public TraceProcessor(final TraceReader reader, final TraceWriter writer) {
		this.reader = reader;
		this.writer = writer;
	}

	public void execute(final Config config) throws InterruptedException, ExecutionException {
		final FifoBuffer buffer = new FifoBuffer(config.BUFFER_MAX_SIZE, config.BUFFER_POP_SIZE, this);
		reader.stream().map(LogEntryBuilder::convert).filter(LogEntry::isValid).forEach(buffer::add);
		//Runtime.getRuntime().availableProcessors();
		//final ForkJoinPool limitedPool = new ForkJoinPool(8);
		//limitedPool.submit(() -> reader.stream().map(LogEntryBuilder::convert).filter(Objects::nonNull).forEach(buffer::add)).get();
		buffer.flush();
		writer.flush();
	}

	@Override
	public void process(final List<List<LogEntry>> values) {
		//executor.execute(() -> {});
		final List<String> lines = values.stream()
				.map(TraceBuilder::create)
				.filter(Objects::nonNull)
				.map(JsonBuilder::convert)
				.collect(Collectors.toList());
		writer.send(lines);
	}

}
