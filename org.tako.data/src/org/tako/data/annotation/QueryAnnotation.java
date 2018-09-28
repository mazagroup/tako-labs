package org.tako.data.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Meta-Annotation to mark a store specific annotation as a query annotation. This allows generic special handing of
 * finder methods on {@link Repository} interfaces.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface QueryAnnotation {
}
