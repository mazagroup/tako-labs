package org.tako.data.jpa.datastore;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tako.data.jpa.datastore.query.JpaQueryMethod;


/**
 * Annotation to configure the JPA 2.1 {@link javax.persistence.EntityGraph}s that should be used on repository methods.
 * Since 1.9 we support the definition of dynamic {@link EntityGraph}s by allowing to customize the fetch-graph via via
 * {@link #attributePaths()} ad-hoc fetch-graph configuration.
 *
 *         {@link #value()} and treat this {@link EntityGraph} as dynamic.
 * @since 1.6
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Documented
public @interface EntityGraph {

	/**
	 * The name of the EntityGraph to use. If empty we fall-back to {@link JpaQueryMethod#getNamedQueryName()} as the
	 * value.
	 *
	 * @return
	 */
	String value() default "";

	/**
	 * The {@link EntityGraphType} of the EntityGraph to use, defaults to {@link EntityGraphType#FETCH}.
	 *
	 * @return
	 */
	EntityGraphType type() default EntityGraphType.FETCH;

	/**
	 * The paths of attributes of this {@link EntityGraph} to use, empty by default. You can refer to direct properties of
	 * the entity or nested properties via a {@code property.nestedProperty}.
	 *
	 * @return
	 * @since 1.9
	 */
	String[] attributePaths() default {};

	/**
	 * Enum for JPA 2.1 {@link javax.persistence.EntityGraph} types.
	 *
	 */
	public enum EntityGraphType {

		/**
		 * When the javax.persistence.loadgraph property is used to specify an entity graph, attributes that are specified
		 * by attribute nodes of the entity graph are treated as FetchType.EAGER and attributes that are not specified are
		 * treated according to their specified or default FetchType.
		 *
		 * @see <a href="http://download.oracle.com/otn-pub/jcp/persistence-2_1-fr-eval-spec/JavaPersistence.pdf">JPA 2.1
		 *      Specification: 3.7.4.2 Load Graph Semantics</a>
		 */
		LOAD("javax.persistence.loadgraph"),

		/**
		 * When the javax.persistence.fetchgraph property is used to specify an entity graph, attributes that are specified
		 * by attribute nodes of the entity graph are treated as FetchType.EAGER and attributes that are not specified are
		 * treated as FetchType.LAZY
		 *
		 * @see <a href="http://download.oracle.com/otn-pub/jcp/persistence-2_1-fr-eval-spec/JavaPersistence.pdf">JPA 2.1
		 *      Specification: 3.7.4.1 Fetch Graph Semantics</a>
		 */
		FETCH("javax.persistence.fetchgraph");

		private final String key;

		private EntityGraphType(String value) {
			this.key = value;
		}

		public String getKey() {
			return key;
		}
	}
}
