package com.shailendher.tracer.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({ "start", "end", "service", "span" })
public class Node implements Serializable {

	private static final long serialVersionUID = 1L;

	//private LocalDateTime start;
	//private LocalDateTime end;

	private String start;
	private String end;

	private String service;

	private String span;

	@JsonIgnore
	private String callerId;

	private List<Node> calls = Collections.emptyList();

	public Node(final LogEntry entry) {
		start = entry.getStart();
		end = entry.getEnd();
		service = entry.getService();
		span = entry.getTarget();
		callerId = entry.getSource();
	}

}
