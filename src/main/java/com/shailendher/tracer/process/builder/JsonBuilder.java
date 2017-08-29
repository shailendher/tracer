package com.shailendher.tracer.process.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shailendher.tracer.domain.NodeTree;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonBuilder {

	private final static ObjectMapper objectMapper = getObjectMapper();

	private JsonBuilder() {

	}

	public static String convert(final NodeTree tree) {
		try {
			final String json = objectMapper.writeValueAsString(tree);
			return json;
		} catch (final JsonProcessingException e) {
			log.error("Error : {} for input: {}", e, tree);
			//exception handling approach should either be -> report to error file or throw exception
			return null;
		}
	}

	private static ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}

}
