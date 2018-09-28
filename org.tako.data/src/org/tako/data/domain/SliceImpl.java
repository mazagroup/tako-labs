package org.tako.data.domain;

import java.util.List;
import java.util.function.Function;

/**
 * Default implementation of {@link ISlice}.
 *
 */
public class SliceImpl<T> extends Chunk<T> {


	private final boolean hasNext;
	private final IPageable pageable;

	/**
	 * Creates a new {@link ISlice} with the given content and {@link IPageable}.
	 *
	 * @param content the content of this {@link ISlice}, must not be {@literal null}.
	 * @param IPageable the paging information, can be {@literal null}.
	 * @param hasNext whether there's another ISlice following the current one.
	 */
	public SliceImpl(List<T> content, IPageable pageable, boolean hasNext) {

		super(content, pageable);

		this.hasNext = hasNext;
		this.pageable = pageable;
	}

	/**
	 * Creates a new {@link SliceImpl} with the given content. This will result in the created {@link ISlice} being
	 * identical to the entire {@link List}.
	 *
	 * @param content must not be {@literal null}.
	 */
	public SliceImpl(List<T> content) {
		this(content, IPageable.unpaged(), false);
	}

	
	public boolean hasNext() {
		return hasNext;
	}

	
	@Override
	public <U> ISlice<U> map(Function<? super T, ? extends U> converter) {
		return new SliceImpl<>(getConvertedContent(converter), pageable, hasNext);
	}

	
	@Override
	public String toString() {

		String contentType = "UNKNOWN";
		List<T> content = getContent();

		if (content.size() > 0) {
			contentType = content.get(0).getClass().getName();
		}

		return String.format("ISlice %d containing %s instances", getNumber(), contentType);
	}

	
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SliceImpl<?>)) {
			return false;
		}

		SliceImpl<?> that = (SliceImpl<?>) obj;

		return this.hasNext == that.hasNext && super.equals(obj);
	}

	
	@Override
	public int hashCode() {

		int result = 17;

		result += 31 * (hasNext ? 1 : 0);
		result += 31 * super.hashCode();

		return result;
	}
}
