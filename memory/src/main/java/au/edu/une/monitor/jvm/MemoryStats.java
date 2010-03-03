package au.edu.une.monitor.jvm;

import au.edu.une.monitor.JvmMemoryStats;

import javax.management.*;
import javax.management.openmbean.CompositeDataSupport;
import java.lang.management.ManagementFactory;

/**
 * Created by IntelliJ IDEA.
 * User: gstewar8
 * Date: Feb 5, 2010
 * Time: 9:16:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class MemoryStats implements JvmMemoryStats {

    private String memoryAddress = "java.lang:type=Memory";
    private MBeanServer mbs;
    private ObjectName memorySpace;

    public MemoryStats () {
        mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            memorySpace = new ObjectName(memoryAddress);
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    public Long getNonHeapPercentage() {
        try {
//            Integer size = (Integer) mbs.getAttribute(memorySpace, "state");
            CompositeDataSupport size = (CompositeDataSupport) mbs.getAttribute(memorySpace,"NonHeapMemoryUsage");

            if(size.containsKey("max") && size.containsKey("used")) {
                Long used = (Long) size.get("used");
                Long max = (Long) size.get("max");

                return java.lang.Math.round((used.doubleValue() / max.doubleValue()) * 100);
            }

        } catch (MBeanException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (AttributeNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ReflectionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public Long getHeapPercentage() {
        try {
//            Integer size = (Integer) mbs.getAttribute(memorySpace, "state");
            CompositeDataSupport size = (CompositeDataSupport) mbs.getAttribute(memorySpace,"HeapMemoryUsage");

            if(size.containsKey("max") && size.containsKey("used")) {
                Long used = (Long) size.get("used");
                Long max = (Long) size.get("max");

                return java.lang.Math.round((used.doubleValue() / max.doubleValue()) * 100);
            }

        } catch (MBeanException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (AttributeNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ReflectionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public Long getNonHeapSize() {
        try {
//            Integer size = (Integer) mbs.getAttribute(memorySpace, "state");
            CompositeDataSupport size = (CompositeDataSupport) mbs.getAttribute(memorySpace,"NonHeapMemoryUsage");

            if(size.containsKey("max")) {
                return (Long) size.get("max");
            }

        } catch (MBeanException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (AttributeNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ReflectionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public Long getHeapSize() {
        try {
//            Integer size = (Integer) mbs.getAttribute(memorySpace, "state");
            CompositeDataSupport size = (CompositeDataSupport) mbs.getAttribute(memorySpace,"HeapMemoryUsage");

            if(size.containsKey("max")) {
                return (Long) size.get("max");
            }

        } catch (MBeanException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (AttributeNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ReflectionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public Long getNonHeapUsed() {
        try {
//            Integer size = (Integer) mbs.getAttribute(memorySpace, "state");
            CompositeDataSupport size = (CompositeDataSupport) mbs.getAttribute(memorySpace,"NonHeapMemoryUsage");

            if(size.containsKey("used")) {
                return (Long) size.get("used");
            }

        } catch (MBeanException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (AttributeNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ReflectionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public Long getHeapUsed() {
        try {
//            Integer size = (Integer) mbs.getAttribute(memorySpace, "state");
            CompositeDataSupport size = (CompositeDataSupport) mbs.getAttribute(memorySpace,"HeapMemoryUsage");

            if(size.containsKey("used")) {
                return (Long) size.get("used");
            }

        } catch (MBeanException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (AttributeNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ReflectionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
