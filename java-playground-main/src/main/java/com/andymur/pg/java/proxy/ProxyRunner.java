package com.andymur.pg.java.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by andymur on 10/7/17.
 */
public class ProxyRunner {

    public static void main(String[] args) {
        SimpleService service = createSimpleServiceProxy(new SimpleServiceImpl());
        service.sayHello();
    }

    static SimpleService createSimpleServiceProxy(SimpleService service) {
        return (SimpleService) Proxy.newProxyInstance(ProxyRunner.class.getClassLoader(),
                new Class[]{SimpleService.class},
                new Handler(service));
    }

    static class Handler implements InvocationHandler {

        private final Object original;

        public Handler(Object original) {
            this.original = original;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Get pawned");
            return method.invoke(original, args);
        }
    }

}
