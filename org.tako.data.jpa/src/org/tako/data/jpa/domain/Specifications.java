package org.tako.data.jpa.domain;

import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Helper class to easily combine {@link ISpecification} instances.
 */
@Deprecated
public class Specifications<T> implements ISpecification<T>, Serializable {

	private static final long serialVersionUID = 1L;

	private final ISpecification<T> spec;

	/**
	 * Creates a new {@link Specifications} wrapper for the given {@link ISpecification}.
	 *
	 * @param spec can be {@literal null}.
	 */
	Specifications(ISpecification<T> spec) {
		this.spec = spec;
	}

	/**
	 * Simple static factory method to add some syntactic sugar around a {@link ISpecification}.
	 *
	 * @deprecated since 2.0, use {@link ISpecification#where} instead
	 * @param <T>
	 * @param spec can be {@literal null}.
	 * @return
	 */
	@Deprecated
	public static <T> Specifications<T> where(ISpecification<T> spec) {
		return new Specifications<>(spec);
	}

	/**
	 * ANDs the given {@link ISpecification} to the current one.
	 *
	 * @deprecated since 2.0, use {@link ISpecification#and} instead
	 * @param <T>
	 * @param other can be {@literal null}.
	 * @return
	 */
	@Deprecated
	public Specifications<T> and(ISpecification<T> other) {
		return new Specifications<>(composed(spec, other, CompositionType.AND));
	}

	/**
	 * ORs the given ISpecification to the current one.
	 *
	 * @deprecated since 2.0, use {@link ISpecification#or} instead
	 * @param <T>
	 * @param other can be {@literal null}.
	 * @return
	 */
	@Deprecated
	public Specifications<T> or(ISpecification<T> other) {
		return new Specifications<>(composed(spec, other, CompositionType.OR));
	}

	/**
	 * Negates the given {@link ISpecification}.
	 *
	 * @deprecated since 2.0, use {@link ISpecification#not} instead
	 * @param <T>
	 * @param spec can be {@literal null}.
	 * @return
	 */
	@Deprecated
	public static <T> Specifications<T> not(ISpecification<T> spec) {
		return new Specifications<>(negated(spec));
	}

	
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		return spec == null ? null : spec.toPredicate(root, query, builder);
	}

	/**
	 * Enum for the composition types for {@link Predicate}s. Can not be turned into lambdas as we need to be
	 * serializable.
	 *
	 * @author Thomas Darimont
	 */
	enum CompositionType {

		AND {
			@Override
			public Predicate combine(CriteriaBuilder builder, Predicate lhs, Predicate rhs) {
				return builder.and(lhs, rhs);
			}
		},

		OR {
			@Override
			public Predicate combine(CriteriaBuilder builder, Predicate lhs, Predicate rhs) {
				return builder.or(lhs, rhs);
			}
		};

		abstract Predicate combine(CriteriaBuilder builder, Predicate lhs, Predicate rhs);
	}

	static <T> ISpecification<T> negated(ISpecification<T> spec) {
		return (root, query, builder) -> spec == null ? null : builder.not(spec.toPredicate(root, query, builder));
	}

	static <T> ISpecification<T> composed(ISpecification<T> lhs, ISpecification<T> rhs, CompositionType compositionType) {

		return (root, query, builder) -> {

			Predicate otherPredicate = rhs == null ? null : rhs.toPredicate(root, query, builder);
			Predicate thisPredicate = lhs == null ? null : lhs.toPredicate(root, query, builder);

			return thisPredicate == null ? otherPredicate
					: otherPredicate == null ? thisPredicate : compositionType.combine(builder, thisPredicate, otherPredicate);
		};
	}
}