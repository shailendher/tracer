package com.shailendher.tracer.process;

import java.util.List;

@FunctionalInterface
public interface EventListener<K, V> {

	//	default void onPut(final ListMultimap<K, V> map) {
	//		//do nothing
	//	}
	//
	//	default void onRemove(final ListMultimap<K, V> map, final List<V> values) {
	//	}

	void process(List<List<V>> values);
}
