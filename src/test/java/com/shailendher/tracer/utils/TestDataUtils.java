package com.shailendher.tracer.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.shailendher.tracer.Settings;
import com.shailendher.tracer.domain.LogEntry;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestDataUtils {

	public static List<String> getIntput() {
		try {
			return Files.lines(getInputPath()).collect(Collectors.toList());
		} catch (final IOException e) {
			log.error("IOException : {}", e);
			return null;
		}
	}

	public static Settings getDefaultSettings() {
		final Settings settings = new Settings();
		settings.setInput(TestDataUtils.getInputPath());
		settings.setOutput(getOutputPath());
		return settings;
	}

	public static Path getInputPath() {
		try {
			return Paths.get(TestDataUtils.class.getClassLoader().getResource("one-entry.txt").toURI());
		} catch (final URISyntaxException e) {
			log.error("URISyntaxException : {}", e);
			return null;
		}
	}

	public static Path getOutputPath() {
		try {
			return Paths.get(TestDataUtils.class.getClassLoader().getResource("one-entry-output.txt").toURI());
		} catch (final URISyntaxException e) {
			log.error("URISyntaxException : {}", e);
			return null;
		}
	}

	public static List<LogEntry> getDefaultLogEntries(final int size) {
		final List<LogEntry> entries = getLogEntries(size);
		entries.iterator().next().setSource("null");
		return entries;
	}

	public static List<LogEntry> getLogEntries(final int size) {
		final List<LogEntry> entries = new ArrayList<>();
		IntStream.range(0, size).forEach(i -> {
			final String s = String.valueOf(i);
			entries.add(new LogEntry(s, s, s, s, "source" + s, "target" + s));
		});
		return entries;
	}

}
