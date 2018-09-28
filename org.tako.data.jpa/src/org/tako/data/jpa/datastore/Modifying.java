package org.tako.data.jpa.datastore;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates a method should be regarded as modifying query.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Documented
public @interface Modifying {

	/**
	 * Defines whether we should flush the underlying persistence context before executing the modifying query.
	 * 
	 * @return
	 */
	boolean flushAutomatically() default false;

	/**
	 * Defines whether we should clear the underlying persistence context after executing the modifying query.
	 *
	 * @return
	 */
	boolean clearAutomatically() default false;
}
