package com.shailendher.tracer.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	//for performance reasons, we don't do the date conversion (not recommended).
	//private LocalDateTime start;
	//private LocalDateTime end;

	private String start;

	private String end;

	private String id;

	private String service;

	private String source;

	private String target;

	public boolean isValid() {
		return id != null && id.trim().length() != 0;
	}

}
