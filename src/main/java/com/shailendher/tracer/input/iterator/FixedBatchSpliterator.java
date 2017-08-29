package com.shailendher.tracer.input.iterator;

import static java.util.stream.StreamSupport.stream;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class FixedBatchSpliterator<T> extends FixedBatchSpliteratorBase<T> {
	private final Spliterator<T> spliterator;

	public FixedBatchSpliterator(final Spliterator<T> toWrap, final int batchSize) {
		super(toWrap.characteristics(), batchSize, toWrap.estimateSize());
		this.spliterator = toWrap;
	}

	public static <T> FixedBatchSpliterator<T> batchedSpliterator(final Spliterator<T> toWrap, final int batchSize) {
		return new FixedBatchSpliterator<>(toWrap, batchSize);
	}

	public static <T> Stream<T> withBatchSize(final Stream<T> in, final int batchSize) {
		return stream(batchedSpliterator(in.spliterator(), batchSize), true);
	}

	@Override
	public boolean tryAdvance(final Consumer<? super T> action) {
		return spliterator.tryAdvance(action);
	}

	@Override
	public void forEachRemaining(final Consumer<? super T> action) {
		spliterator.forEachRemaining(action);
	}
}
