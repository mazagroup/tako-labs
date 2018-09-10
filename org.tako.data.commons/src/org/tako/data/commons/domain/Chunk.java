package org.tako.data.commons.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Getter;

/**
 * A chunk of data restricted by the configured {@link IPageable}.
 */
abstract class Chunk<T> implements ISlice<T>, Serializable {


	private final List<T> content = new ArrayList<>();
	private final @Getter IPageable pageable;

	/**
	 * Creates a new {@link Chunk} with the given content and the given governing {@link IPageable}.
	 *
	 * @param content must not be {@literal null}.
	 * @param IPageable must not be {@literal null}.
	 */
	public Chunk(List<T> content, IPageable pageable) {

		assert content != null:  "Content must not be null!";
		assert pageable != null: "IPageable must not be null!";

		this.content.addAll(content);
		this.pageable = pageable;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.ISlice#getNumber()
	 */
	public int getNumber() {
		return pageable.isPaged() ? pageable.getPageNumber() : 0;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.ISlice#getSize()
	 */
	public int getSize() {
		return pageable.isPaged() ? pageable.getPageSize() : 0;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.ISlice#getNumberOfElements()
	 */
	public int getNumberOfElements() {
		return content.size();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.ISlice#hasPrevious()
	 */
	public boolean hasPrevious() {
		return getNumber() > 0;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.ISlice#isFirst()
	 */
	public boolean isFirst() {
		return !hasPrevious();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.ISlice#isLast()
	 */
	public boolean isLast() {
		return !hasNext();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.ISlice#nextIPageable()
	 */
	public IPageable nextIPageable() {
		return hasNext() ? pageable.next() : org.tako.data.commons.domain.IPageable.unpaged();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.ISlice#previousIPageable()
	 */
	public IPageable previousIPageable() {
		return hasPrevious() ? pageable.previousOrFirst() : org.tako.data.commons.domain.IPageable.unpaged();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.ISlice#hasContent()
	 */
	public boolean hasContent() {
		return !content.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.ISlice#getContent()
	 */
	public List<T> getContent() {
		return Collections.unmodifiableList(content);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.ISlice#getSort()
	 */
	@Override
	public Sort getSort() {
		return pageable.getSort();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<T> iterator() {
		return content.iterator();
	}

	/**
	 * Applies the given {@link Function} to the content of the {@link Chunk}.
	 *
	 * @param converter must not be {@literal null}.
	 * @return
	 */
	protected <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {

		assert converter != null:  "Function must not be null!";

		return this.stream().map(converter::apply).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Chunk<?>)) {
			return false;
		}

		Chunk<?> that = (Chunk<?>) obj;

		boolean contentEqual = this.content.equals(that.content);
		boolean IPageableEqual = this.pageable.equals(that.pageable);

		return contentEqual && IPageableEqual;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {

		int result = 17;

		result += 31 * pageable.hashCode();
		result += 31 * content.hashCode();

		return result;
	}
}
