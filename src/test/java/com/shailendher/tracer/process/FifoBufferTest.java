package com.shailendher.tracer.process;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.shailendher.tracer.domain.LogEntry;
import com.shailendher.tracer.utils.TestDataUtils;

public class FifoBufferTest {

	@Test
	public void call_event_listener() {
		final int buffersize = 5, totalEntries = 10, popsize = 1;
		final AtomicInteger counter = new AtomicInteger(0);
		final EventListener<String, LogEntry> listener = i -> counter.incrementAndGet();
		final FifoBuffer buffer = new FifoBuffer(buffersize, popsize, listener);
		TestDataUtils.getDefaultLogEntries(totalEntries).stream().forEach(buffer::add);
		assertThat(counter.get()).isEqualTo(totalEntries - buffersize);
	}

	@Test
	public void flush_entries() {
		final int buffersize = 10, totalEntries = 10, popsize = 1;
		final AtomicInteger counter = new AtomicInteger(0);
		final EventListener<String, LogEntry> listener = i -> counter.addAndGet(i.size());
		final FifoBuffer buffer = new FifoBuffer(buffersize, popsize, listener);
		TestDataUtils.getDefaultLogEntries(totalEntries).stream().forEach(buffer::add);
		buffer.flush();
		assertThat(counter.get()).isEqualTo(totalEntries);
	}

}
