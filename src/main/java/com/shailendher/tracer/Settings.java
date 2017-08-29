package com.shailendher.tracer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.converters.BaseConverter;

import lombok.Data;

@Data
public class Settings {

	@Parameter(names = { "-i", "--input" }, description = "Path to the log file.", required = true, validateWith = InputPath.class, converter = PathConverter.class)
	private Path input;

	@Parameter(names = { "-o", "--output" }, description = "Path to the output file.", required = true, validateWith = OutputPath.class, converter = PathConverter.class)
	private Path output;

	@Parameter(names = { "-m", "--mode" }, description = "conservate: single threaded, aggressive: multi threaded and faster by multiple of 2 or more")
	private Config mode = Config.AGGRESSIVE;

	public static class PathConverter extends BaseConverter<Path> {
		public PathConverter(final String optionName) {
			super(optionName);
		}

		@Override
		public Path convert(final String value) {
			if (value.equalsIgnoreCase("system.in")) {
				throw new ParameterException("System.in/out is not implemented.");
			} else {
				return Paths.get(value);
			}
		}
	}

	public static class InputPath implements IParameterValidator {
		@Override
		public void validate(final String name, final String value) throws ParameterException {
			if (value == null || value.trim().length() == 0) {
				throw new ParameterException("Empty or null input path");
			}
			final boolean isValidPath = Files.isReadable(Paths.get(value));
			if (!isValidPath) {
				throw new ParameterException("The path entered is probably invalid? " + value);
			}
		}
	}

	public static class OutputPath implements IParameterValidator {
		@Override
		public void validate(final String name, final String value) throws ParameterException {
			if (value == null || value.trim().length() == 0) {
				throw new ParameterException("Empty or null input path");
			}
			try {
				Files.createFile(Paths.get(value)).toFile().delete();
			} catch (IOException e) {
				throw new ParameterException("The path entered is probably invalid? " + value);
			}

		}
	}

	//	public static class InputStreamConverter extends BaseConverter<InputStream> {
	//		public InputStreamConverter(final String optionName) {
	//			super(optionName);
	//		}
	//
	//		@Override
	//		public InputStream convert(final String value) {
	//			try {
	//				if (value.equalsIgnoreCase("system.in")) {
	//					return System.in;
	//				} else {
	//					return new FileInputStream(value);
	//				}
	//			} catch (final FileNotFoundException e) {
	//				e.printStackTrace();
	//				throw new ParameterException(getErrorString(value, " an inputStream"));
	//			}
	//		}
	//	}
	//
	//	public static class OutputStreamConverter extends BaseConverter<OutputStream> {
	//		public OutputStreamConverter(final String optionName) {
	//			super(optionName);
	//		}
	//
	//		@Override
	//		public OutputStream convert(final String value) {
	//			try {
	//				if (value.equalsIgnoreCase("system.out")) {
	//					return System.out;
	//				} else {
	//					return new FileOutputStream(value);
	//				}
	//			} catch (final FileNotFoundException e) {
	//				e.printStackTrace();
	//				throw new ParameterException(getErrorString(value, " an outputStream"));
	//			}
	//		}
	//	}

}
