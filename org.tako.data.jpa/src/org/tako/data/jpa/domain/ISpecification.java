package org.tako.data.jpa.domain;

import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.tako.data.jpa.domain.Specifications.CompositionType;

public interface ISpecification<T> extends Serializable {

	long serialVersionUID = 1L;

	/**
	 * Negates the given {@link ISpecification}.
	 *
	 * @param <T>
	 * @param spec can be {@literal null}.
	 * @return
	 * @since 2.0
	 */
	static <T> ISpecification<T> not(ISpecification<T> spec) {
		return Specifications.negated(spec);
	}

	/**
	 * Simple static factory method to add some syntactic sugar around a {@link ISpecification}.
	 *
	 * @param <T>
	 * @param spec can be {@literal null}.
	 * @return
	 * @since 2.0
	 */
	static <T> ISpecification<T> where(ISpecification<T> spec) {
		return Specifications.where(spec);
	}

	/**
	 * ANDs the given {@link ISpecification} to the current one.
	 *
	 * @param other can be {@literal null}.
	 * @return The conjunction of the Specifications
	 * @since 2.0
	 */
	default ISpecification<T> and(ISpecification<T> other) {
		return Specifications.composed(this, other, CompositionType.AND);
	}

	/**
	 * ORs the given ISpecification to the current one.
	 *
	 * @param other can be {@literal null}.
	 * @return The disjunction of the Specifications
	 * @since 2.0
	 */
	default ISpecification<T> or(ISpecification<T> other) {
		return Specifications.composed(this, other, CompositionType.OR);
	}

	/**
	 * Creates a WHERE clause for a query of the referenced entity in form of a {@link Predicate} for the given
	 * {@link Root} and {@link CriteriaQuery}.
	 *
	 * @param root must not be {@literal null}.
	 * @param query must not be {@literal null}.
	 * @param criteriaBuilder must not be {@literal null}.
	 * @return a {@link Predicate}, may be {@literal null}.
	 */
	Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder);
}

