package com.andymur.pg.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SmallSpringContextRunner {

	public static void main(String[] args) {
		ApplicationContext context = null;
		context = new ClassPathXmlApplicationContext(
					"beans.xml");


		Bean bob = context.getBean("bob", Bean.class);
		Map<String, List<ExternalInterfaceType>> config = context.getBean("companyExternalInterfaces", HashMap.class);
		System.out.println(config);

		ExternalInterfaceType externalInterfaceType = context.getBean("orderApiCsv", ExternalInterfaceType.class);
		System.out.println(externalInterfaceType);

		final BeanContainer beanContainer = context.getBean("beanContainer", BeanContainer.class);

		beanContainer.sayHi();
	}
}
