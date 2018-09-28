package org.tako.commons.tests.sample.beans;

/**
 * Simple bean used to test dependency checking.
 *
 * @since 04.09.2003
 */
public class DependenciesBean  {

	private int age;

	private String name;

	private TestBean spouse;


	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setSpouse(TestBean spouse) {
		this.spouse = spouse;
	}

	public TestBean getSpouse() {
		return spouse;
	}

}
