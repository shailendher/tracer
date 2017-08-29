package com.shailendher.tracer;

import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ExecutionException;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.shailendher.tracer.input.TraceReader;
import com.shailendher.tracer.output.TraceWriter;
import com.shailendher.tracer.process.TraceProcessor;
import com.shailendher.tracer.util.Statistics;

public class DefaultApplication {

	public static void main(final String[] args) throws Exception {
		try {
			final Settings settings = new Settings();
			JCommander.newBuilder().addObject(settings).build().parse(args);
			Statistics.start();
			execute(settings);
			Statistics.stop();
			Statistics.print();
		} catch (final ParameterException e) {
			System.out.println("******************************************");
			System.out.println("Error parsing input: " + e.getMessage());
			e.usage();
			System.out.println("******************************************");
		}

	}

	private static void execute(final Settings settings) throws IOException, InterruptedException, ExecutionException {
		final TraceReader reader = TraceReader.create(settings);
		final TraceWriter writer = TraceWriter.create(settings);
		Statistics.init(Files.lines(settings.getInput()).count());
		final TraceProcessor processor = new TraceProcessor(reader, writer);
		processor.execute(settings.getMode());
	}

}
