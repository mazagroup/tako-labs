package org.tako.data.jpa.datastore;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.persistence.QueryHint;

/**
 * Wrapper annotation to allow {@link QueryHint} annotations to be bound to methods. It will be evaluated when using
 * {@link Query} on a query method or if you derive the query from the method name. If you rely on named queries either
 * use the XML or annotation based way to declare {@link QueryHint}s in combination with the actual named query
 * declaration.
 */
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryHints {

	/**
	 * The {@link QueryHint}s to apply when the query will be executed.
	 *
	 * @return
	 */
	QueryHint[] value() default {};

	/**
	 * Defines whether the configured {@link QueryHint}s shall be applied for count queries during pagination as well.
	 * Defaults to {@literal true}.
	 *
	 * @return
	 */
	boolean forCounting() default true;
}