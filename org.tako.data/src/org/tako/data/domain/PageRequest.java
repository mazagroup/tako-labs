package org.tako.data.domain;

import org.tako.data.domain.Sort.Direction;

/**
 * Basic Java Bean implementation of {@code Pageable}.
 */
public class PageRequest extends AbstractPageRequest {


	private final Sort sort;

	/**
	 * Creates a new {@link PageRequest}. Pages are zero indexed, thus providing 0 for {@code page} will return the first
	 * page.
	 *
	 * @param page zero-based page index.
	 * @param size the size of the page to be returned.
	 * @deprecated use {@link #of(int, int)} instead.
	 */
	@Deprecated
	public PageRequest(int page, int size) {
		this(page, size, Sort.unsorted());
	}

	/**
	 * Creates a new {@link PageRequest} with sort parameters applied.
	 *
	 * @param page zero-based page index.
	 * @param size the size of the page to be returned.
	 * @param direction the direction of the {@link Sort} to be specified, can be {@literal null}.
	 * @param properties the properties to sort by, must not be {@literal null} or empty.
	 * @deprecated use {@link #of(int, int, Direction, String...)} instead.
	 */
	@Deprecated
	public PageRequest(int page, int size, Direction direction, String... properties) {
		this(page, size, Sort.by(direction, properties));
	}

	/**
	 * Creates a new {@link PageRequest} with sort parameters applied.
	 *
	 * @param page zero-based page index.
	 * @param size the size of the page to be returned.
	 * @param sort can be {@literal null}.
	 * @deprecated since 2.0, use {@link #of(int, int, Sort)} instead.
	 */
	@Deprecated
	public PageRequest(int page, int size, Sort sort) {

		super(page, size);

		this.sort = sort;
	}

	/**
	 * Creates a new unsorted {@link PageRequest}.
	 *
	 * @param page zero-based page index.
	 * @param size the size of the page to be returned.
	 * @since 2.0
	 */
	public static PageRequest of(int page, int size) {
		return of(page, size, Sort.unsorted());
	}

	/**
	 * Creates a new {@link PageRequest} with sort parameters applied.
	 *
	 * @param page zero-based page index.
	 * @param size the size of the page to be returned.
	 * @param sort must not be {@literal null}.
	 * @since 2.0
	 */
	public static PageRequest of(int page, int size, Sort sort) {
		return new PageRequest(page, size, sort);
	}

	/**
	 * Creates a new {@link PageRequest} with sort direction and properties applied.
	 *
	 * @param page zero-based page index.
	 * @param size the size of the page to be returned.
	 * @param direction must not be {@literal null}.
	 * @param properties must not be {@literal null}.
	 * @since 2.0
	 */
	public static PageRequest of(int page, int size, Direction direction, String... properties) {
		return of(page, size, Sort.by(direction, properties));
	}

	
	public Sort getSort() {
		return sort;
	}

	
	public IPageable next() {
		return new PageRequest(getPageNumber() + 1, getPageSize(), getSort());
	}

	
	public PageRequest previous() {
		return getPageNumber() == 0 ? this : new PageRequest(getPageNumber() - 1, getPageSize(), getSort());
	}

	
	public IPageable first() {
		return new PageRequest(0, getPageSize(), getSort());
	}

	
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PageRequest)) {
			return false;
		}

		PageRequest that = (PageRequest) obj;

		return super.equals(that) && this.sort.equals(that.sort);
	}

	
	@Override
	public int hashCode() {
		return 31 * super.hashCode() + sort.hashCode();
	}

	
	@Override
	public String toString() {
		return String.format("Page request [number: %d, size %d, sort: %s]", getPageNumber(), getPageSize(), sort);
	}
}
