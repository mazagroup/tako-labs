package org.tako.data.commons.util;

import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

import lombok.Value;

@Value(staticConstructor = "of")
class LazyStreamable<T> implements IStreamable<T> {

	private Supplier<? extends Stream<T>> stream;

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return stream().iterator();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.util.Streamable#stream()
	 */
	@Override
	public Stream<T> stream() {
		return stream.get();
	}
}