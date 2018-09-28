package org.tako.data.domain;

/**
 * {@link IPageable} implementation to represent the absence of pagination information.
 *
 */
enum IUnpaged implements IPageable {

	INSTANCE;

	
	@Override
	public boolean isPaged() {
		return false;
	}

	
	@Override
	public IPageable previousOrFirst() {
		return this;
	}

	
	@Override
	public IPageable next() {
		return this;
	}

	
	@Override
	public boolean hasPrevious() {
		return false;
	}

	
	@Override
	public Sort getSort() {
		return Sort.unsorted();
	}

	
	@Override
	public int getPageSize() {
		throw new UnsupportedOperationException();
	}

	
	@Override
	public int getPageNumber() {
		throw new UnsupportedOperationException();
	}

	
	@Override
	public long getOffset() {
		throw new UnsupportedOperationException();
	}

	
	@Override
	public IPageable first() {
		return this;
	}
}
