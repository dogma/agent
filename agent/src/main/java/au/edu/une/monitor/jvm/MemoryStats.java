package au.edu.une.monitor.jvm;

import au.edu.une.monitor.JvmMemoryStats;

import javax.management.*;
import javax.management.openmbean.CompositeDataSupport;
import java.lang.management.ManagementFactory;
import java.util.Set;
import java.util.ArrayList;

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

    public MemoryStats() {
        mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            memorySpace = new ObjectName(memoryAddress);
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public String[] getMemorySpaces() {
        try {
            ObjectName query = new ObjectName("java.lang:type=MemoryPool,*");
            Set<ObjectName> ons = mbs.queryNames(query, null);

            ArrayList<String> spaces = new ArrayList<String>();

            for (ObjectName on : ons) {
                spaces.add(on.getKeyProperty("name"));
//                System.out.println("Name: " + on.getKeyProperty("name"));
//                System.out.println("Object : " + on.getCanonicalName());
            }
            return spaces.toArray(new String[spaces.size()]);

        } catch (MalformedObjectNameException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }

    /**
     * Returns an array of longs containing memory usage. The types of memory usage are (in array order):
     * 0: committed
     * 1: init
     * 2: max
     * 3: used
     * 4: free (This is not an intial statistic. This is generated from used/max)
     * @param name
     * @return
     */
    public Long[] getMemorySpace(String name) {
        try {
            ObjectName on = new ObjectName("java.lang:type=MemoryPool,name=" + name);
            CompositeDataSupport usage = (CompositeDataSupport) mbs.getAttribute(on, "Usage");

            Long[] l = new Long[5];
            l[0] = (Long) usage.get("committed");
            l[1] = (Long) usage.get("init");
            l[2] = (Long) usage.get("max");
            l[3] = (Long) usage.get("used");
            l[4] = (((Long) usage.get("used") * 100L) / (Long) usage.get("max"));

            return l;
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }


    public Long getNonHeapPercentage() {
        try {
//            Integer size = (Integer) mbs.getAttribute(memorySpace, "state");
            CompositeDataSupport size = (CompositeDataSupport) mbs.getAttribute(memorySpace, "NonHeapMemoryUsage");

            if (size.containsKey("max") && size.containsKey("used")) {
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
            CompositeDataSupport size = (CompositeDataSupport) mbs.getAttribute(memorySpace, "HeapMemoryUsage");

            if (size.containsKey("max") && size.containsKey("used")) {
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
            CompositeDataSupport size = (CompositeDataSupport) mbs.getAttribute(memorySpace, "NonHeapMemoryUsage");

            if (size.containsKey("max")) {
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
            CompositeDataSupport size = (CompositeDataSupport) mbs.getAttribute(memorySpace, "HeapMemoryUsage");

            if (size.containsKey("max")) {
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
            CompositeDataSupport size = (CompositeDataSupport) mbs.getAttribute(memorySpace, "NonHeapMemoryUsage");

            if (size.containsKey("used")) {
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
            CompositeDataSupport size = (CompositeDataSupport) mbs.getAttribute(memorySpace, "HeapMemoryUsage");

            if (size.containsKey("used")) {
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
