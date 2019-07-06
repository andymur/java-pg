package com.andymur.pg.java.jmx;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * Created by andymur on 10/30/17.
 */
public class JmxRunner {
    public static void main(String[] args) throws MalformedObjectNameException,
            NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, InterruptedException {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        mBeanServer.registerMBean(new MySimple(), new ObjectName("com.andymur", "simpleMBean", "simpleMBean"));
    }
}
