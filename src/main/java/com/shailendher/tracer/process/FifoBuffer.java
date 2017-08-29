package com.shailendher.tracer.process;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.shailendher.tracer.domain.LogEntry;

public class FifoBuffer {

	private final int MAX_SIZE;

	private final int POP_SIZE;

	private final ListMultimap<String, LogEntry> source;

	private final List<EventListener<String, LogEntry>> listeners = new ArrayList<>();

	@SafeVarargs
	public FifoBuffer(final int batchSize, final int popSize, final EventListener<String, LogEntry>... listeners) {
		MAX_SIZE = batchSize;
		POP_SIZE = popSize;
		source = Multimaps.synchronizedListMultimap(LinkedListMultimap.create(MAX_SIZE));
		if (listeners != null) {
			Stream.of(listeners).forEach(this.listeners::add);
		}
	}

	public void add(final LogEntry input) {
		source.put(input.getId(), input);
		//listener.onPut(source);
		if (isOverflow()) {
			pop(POP_SIZE);
		}
	}

	//removing entries one by one is extremely slow, use pop instead
	@Deprecated
	public void remove(final String key) {
		source.removeAll(key);
		//listeners.forEach(listener -> listener.onRemove(source, values));
	}

	private void pop(final int popSize) {
		final List<List<LogEntry>> values;
		synchronized (source) {
			final List<String> keys = source.keySet().stream().limit(popSize).collect(Collectors.toList());
			values = keys.stream().map(source::removeAll).collect(Collectors.toList());
		}
		listeners.forEach(listener -> listener.process(values));
	}

	public void flush() {
		pop(source.size());
	}

	private boolean isOverflow() {
		return source.size() > MAX_SIZE;
	}

}
