package org.amdatu.tutorial.weaving;

public class Application {
	public int methodToModify(final String text) {
		System.out.println(text);
		return 0;
	}
}
