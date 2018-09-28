package org.tako.commons.core;

/**
 * A common delegate for detecting a GraalVM native image environment.
 */
abstract class GraalDetector {

	// See https://github.com/oracle/graal/blob/master/sdk/src/org.graalvm.nativeimage/src/org/graalvm/nativeimage/ImageInfo.java
	private static final boolean imageCode = (System.getProperty("org.graalvm.nativeimage.imagecode") != null);


	/**
	 * Return whether this runtime environment lives within a native image.
	 */
	public static boolean inImageCode() {
		return imageCode;
	}

}
