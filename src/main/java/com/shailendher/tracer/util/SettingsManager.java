package com.shailendher.tracer.util;

import com.shailendher.tracer.Settings;

//unused
public class SettingsManager {

	private static final InheritableThreadLocal<Settings> context = new InheritableThreadLocal<>();

	public static void add(final Settings settings) {
		context.set(settings);
	}

	public static Settings get() {
		return context.get();
	}

	public static void clean() {
		context.remove();
	}
}
