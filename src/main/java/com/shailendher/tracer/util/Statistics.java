package com.shailendher.tracer.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.base.Stopwatch;

import me.tongfei.progressbar.ProgressBar;

public class Statistics {

	private static Stopwatch stopwatch = Stopwatch.createUnstarted();

	//delegate to 3rd party impl instead of hardcoding the instance
	private static ProgressBar progressBar;

	private static long inputTotal = 0;

	private static final AtomicInteger JSON_ELEMENTS = new AtomicInteger(0);

	private static final AtomicInteger VALID = new AtomicInteger(0);

	private static final AtomicInteger INVALID = new AtomicInteger(0);

	private Statistics() {
	}

	public static void start() {
		stopwatch.start();
	}

	public static void stop() {
		try {
			progressBar.stop();
			stopwatch.stop();
		} catch (final Exception e) {
			// ignore
		}
	}

	public static void init(final long total) {
		inputTotal = total;
		progressBar = new ProgressBar("tracer", 0).start();
	}

	public static void valid(final int count) {
		try {
			progressBar.stepTo(VALID.addAndGet(count));
		} catch (final Exception e) {
			// ignore
		}
	}

	public static void valid() {
		VALID.incrementAndGet();
	}

	public static void invalid() {
		INVALID.incrementAndGet();
	}

	public static void invalid(final int count) {
		INVALID.addAndGet(count);
	}

	public static void json() {
		JSON_ELEMENTS.incrementAndGet();
	}

	public static void print() {
		System.out.println("Total number of entries in input file  : " + inputTotal);
		System.out.println("Total Number of entries in output file : " + VALID);
		System.out.println("Invalid/Malformed/Orphan entries       : " + INVALID);
		System.out.println("Total time taken to process (in ms)    : " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
	}

}
