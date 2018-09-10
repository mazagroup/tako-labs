package org.tako.data.commons.domain;

/**
 * {@link IPageable} implementation to represent the absence of pagination information.
 *
 */
enum IUnpaged implements IPageable {

	INSTANCE;

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.IPageable#isPaged()
	 */
	@Override
	public boolean isPaged() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.IPageable#previousOrFirst()
	 */
	@Override
	public IPageable previousOrFirst() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.IPageable#next()
	 */
	@Override
	public IPageable next() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.IPageable#hasPrevious()
	 */
	@Override
	public boolean hasPrevious() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.IPageable#getSort()
	 */
	@Override
	public Sort getSort() {
		return Sort.unsorted();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.IPageable#getPageSize()
	 */
	@Override
	public int getPageSize() {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.IPageable#getPageNumber()
	 */
	@Override
	public int getPageNumber() {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.IPageable#getOffset()
	 */
	@Override
	public long getOffset() {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.IPageable#first()
	 */
	@Override
	public IPageable first() {
		return this;
	}
}
