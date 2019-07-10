package com.andymur.pg.unittests.testrules;

import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.lang.reflect.Field;

/**
 * I'm trying to play a bit with JUnit rules
 *
 * http://blog.qatools.ru/junit/junit-rules-tutorial
 */
public class PgRule implements TestRule {

    @Override
    public Statement apply(Statement base, Description description) {
        try {
            if (base instanceof InvokeMethod) {
                InvokeMethod invokeMethod = (InvokeMethod) base;
                System.out.println("Going to invoke method...");
                Field fTestMethod = invokeMethod.getClass().getDeclaredField("fTestMethod");
                fTestMethod.setAccessible(true);
                FrameworkMethod frameworkMethod = (FrameworkMethod) fTestMethod.get(invokeMethod);
                if (!frameworkMethod.getName().contains("Softly")) {
                    throw new IllegalStateException("Non-soft methods are not supported!");
                }
            }
            base.evaluate();
            if (base instanceof InvokeMethod) {
                System.out.println("Method has been executed...");
            }
        } catch (Throwable throwable) {
            throw new RuntimeException("Something wrong with this rule!", throwable);
        }
        return base;
    }
}
