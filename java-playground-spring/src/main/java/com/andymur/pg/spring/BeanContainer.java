package com.andymur.pg.spring;

public class BeanContainer {

	Bean[] beans;

	public BeanContainer(Bean bean) {
		this.beans = new Bean[] {bean};
	}

	public BeanContainer(Bean... beans) {
		this.beans = beans;
	}

	public void sayHi() {
		for (final Bean bean: beans) {
			bean.sayHi();
		}
	}
}
