package com.shailendher.tracer.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeTree implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private Node root;

	public boolean isValid() {
		return id != null && id.trim().length() != 0;
	}

}
