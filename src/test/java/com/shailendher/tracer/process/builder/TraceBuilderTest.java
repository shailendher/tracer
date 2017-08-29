package com.shailendher.tracer.process.builder;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.shailendher.tracer.domain.LogEntry;
import com.shailendher.tracer.domain.NodeTree;
import com.shailendher.tracer.utils.TestDataUtils;

public class TraceBuilderTest {

	@Test
	public void empty_list() {
		final NodeTree tree = TraceBuilder.create(Collections.emptyList());
		assertThat(tree).isNotNull();
		assertThat(tree.isValid()).isFalse();
	}

	@Test
	public void valid_list() {
		//given
		final List<LogEntry> entries = TestDataUtils.getDefaultLogEntries(2);
		//when
		final NodeTree tree = TraceBuilder.create(entries);
		//then
		assertThat(tree).isNotNull();
		assertThat(tree.getId()).isEqualTo("0");
	}

	@Test
	public void missing_root_node() {
		//given
		final List<LogEntry> entries = TestDataUtils.getLogEntries(2);
		//when
		final NodeTree tree = TraceBuilder.create(entries);
		//then
		assertThat(tree).isNotNull();
		assertThat(tree.isValid()).isFalse();
	}

}
