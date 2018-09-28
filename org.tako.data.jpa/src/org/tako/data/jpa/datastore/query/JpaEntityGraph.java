package org.tako.data.jpa.datastore.query;

import java.util.Arrays;
import java.util.List;

import org.tako.data.commons.lang.Nullable;
import org.tako.data.commons.util.Assert;
import org.tako.data.commons.util.StringUtils;
import org.tako.data.jpa.datastore.EntityGraph;
import org.tako.data.jpa.datastore.EntityGraph.EntityGraphType;


/**
 * EntityGraph configuration for JPA 2.1 {@link EntityGraph}s.
 */
public class JpaEntityGraph {

	private static String[] EMPTY_ATTRIBUTE_PATHS = {};

	private final String name;
	private final EntityGraphType type;
	private final List<String> attributePaths;

	/**
	 * Creates an {@link JpaEntityGraph}.
	 *
	 * @param entityGraph must not be {@literal null}.
	 * @param nameFallback must not be {@literal null} or empty.
	 */
	public JpaEntityGraph(EntityGraph entityGraph, String nameFallback) {
		this(StringUtils.hasText(entityGraph.value()) ? entityGraph.value() : nameFallback, entityGraph.type(), entityGraph
				.attributePaths());
	}

	/**
	 * Creates an {@link JpaEntityGraph} with the given name, {@link EntityGraphType} and attribute paths.
	 *
	 * @param name must not be {@literal null} or empty.
	 * @param type must not be {@literal null}.
	 * @param attributePaths may be {@literal null}.
	 * @since 1.9
	 */
	public JpaEntityGraph(String name, EntityGraphType type, @Nullable String[] attributePaths) {

		Assert.hasText(name, "The name of an EntityGraph must not be null or empty!");
		Assert.notNull(type, "FetchGraphType must not be null!");

		this.name = name;
		this.type = type;
		this.attributePaths = Arrays.asList(attributePaths == null ? EMPTY_ATTRIBUTE_PATHS : attributePaths);
	}

	/**
	 * Returns the name of the {@link EntityGraph} configuration to use.
	 *
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the {@link EntityGraphType} of the {@link EntityGraph} to use.
	 *
	 * @return
	 */
	public EntityGraphType getType() {
		return type;
	}

	/**
	 * Returns the attribute node names to be used for this {@link JpaEntityGraph}.
	 *
	 * @return
	 * @since 1.9
	 */
	public List<String> getAttributePaths() {
		return attributePaths;
	}

	/**
	 * Return {@literal true} if this {@link JpaEntityGraph} needs to be generated on-the-fly.
	 *
	 * @return
	 * @since 1.9
	 */
	public boolean isAdHocEntityGraph() {
		return !attributePaths.isEmpty();
	}

	
	@Override
	public String toString() {
		return "JpaEntityGraph [name=" + name + ", type=" + type + ", attributePaths=" + attributePaths.toString() + "]";
	}
}
