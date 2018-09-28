package org.tako.data.util;

import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

import lombok.Value;

@Value(staticConstructor = "of")
class LazyStreamable<T> implements Streamable<T> {

	private Supplier<? extends Stream<T>> stream;

	
	@Override
	public Iterator<T> iterator() {
		return stream().iterator();
	}

	
	@Override
	public Stream<T> stream() {
		return stream.get();
	}
}