package com.shailendher.tracer;

import java.util.EnumSet;

public enum Config {

	CONSERVATIVE(4, 4, 50_000, 1),
	AGGRESSIVE(10, 16, 100_000, 100);

	public final int READER_BATCH_SIZE;

	//not implemented
	public final int READER_MAX_THREAD_COUNT;

	public final int BUFFER_MAX_SIZE;

	public final int BUFFER_POP_SIZE;

	Config(final int batchsize, final int threadcount, final int buffersize, final int popsize) {
		READER_BATCH_SIZE = batchsize;
		READER_MAX_THREAD_COUNT = threadcount;
		BUFFER_MAX_SIZE = buffersize;
		BUFFER_POP_SIZE = popsize;
	}

	public static Config find(final String val) {
		return EnumSet.allOf(Config.class).stream().filter(e -> e.name().equalsIgnoreCase(val)).findFirst()
				.orElse(Config.CONSERVATIVE);
	}

}
