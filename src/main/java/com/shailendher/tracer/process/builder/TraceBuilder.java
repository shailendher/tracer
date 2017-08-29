package com.shailendher.tracer.process.builder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.shailendher.tracer.domain.LogEntry;
import com.shailendher.tracer.domain.Node;
import com.shailendher.tracer.domain.NodeTree;
import com.shailendher.tracer.domain.OrphanEntryException;
import com.shailendher.tracer.util.Statistics;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TraceBuilder {

	private final static String ORPHAN = "null";

	public static NodeTree create(final List<LogEntry> entries) {
		NodeTree tree = new NodeTree();
		try {
			final LogEntry rootEntry = findRoot(entries);
			final Node rootNode = new Node(rootEntry);
			tree = new NodeTree(rootEntry.getId(), rootNode);
			final Map<String, List<Node>> callerMap = entries.stream().map(Node::new)
					.collect(Collectors.groupingBy(Node::getCallerId));
			assign(callerMap, rootNode);
		} catch (final OrphanEntryException e) {
			//Statistics.orphans(entries.size());
			Statistics.invalid(entries.size());
		} catch (final RuntimeException e) {
			log.error("Error : {} for input: {}", e, entries);
			Statistics.invalid();
		}
		return tree;
	}

	private static LogEntry findRoot(final List<LogEntry> entries) {
		return entries.stream().filter(entry -> ORPHAN.equalsIgnoreCase(entry.getSource())).findFirst()
				.orElseThrow(OrphanEntryException::new);
	}

	private static void assign(final Map<String, List<Node>> callerMap, final Node node) {
		final String id = node.getSpan();
		final List<Node> calls = callerMap.get(id);
		if (calls != null) {
			node.setCalls(calls);
			for (final Node localNode : calls) {
				assign(callerMap, localNode);
			}
		}
	}

}
