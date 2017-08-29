package com.shailendher.tracer;

import static org.fest.assertions.api.Assertions.assertThat;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

import com.beust.jcommander.ParameterException;
import com.shailendher.tracer.utils.TestDataUtils;

public class SettingsTest {

	Settings.InputPath validator = null;

	Settings.PathConverter converter = null;

	@Before
	public void setup() {
		validator = new Settings.InputPath();
		converter = new Settings.PathConverter("input");
	}

	@Test(expected = ParameterException.class)
	public void validate_invalid_path() {
		validator.validate("input", "i-dont-exist.txt");
	}

	@Test(expected = ParameterException.class)
	public void validate_empty_or_null_path() {
		validator.validate("input", null);
	}

	@Test
	public void validate_valid_input_path() {
		final String path = TestDataUtils.getInputPath().toString();
		validator.validate("input", path);
	}

	@Test
	public void convert_valid_input_path() {
		final Path expected = TestDataUtils.getInputPath();
		final Path actual = converter.convert(expected.toString());
		assertThat(actual).isNotNull().isEqualTo(expected);
	}

	@Test
	public void validate_valid_output_path() {
		final String path = TestDataUtils.getInputPath().toString();
		validator.validate("output", path);
	}

	@Test(expected = ParameterException.class)
	public void validate_invalid_output_path() {
		validator.validate("output", "/tmp/xyasfjalfsj");
	}

}
