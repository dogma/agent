package au.edu.une.monitor.jvm;

import javax.management.*;
import javax.management.openmbean.CompositeDataSupport;
import java.lang.management.ManagementFactory;

/**
 * Created by IntelliJ IDEA.
 * User: gstewar8
 * Date: Feb 5, 2010
 * Time: 1:03:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class NamedMemorySpaceStats {


    private String memoryAddress = "";
    private MBeanServer mbs;
    private ObjectName memorySpace;

    public NamedMemorySpaceStats(String space) {
        memoryAddress = space;
        mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            memorySpace = new ObjectName(getMemoryAddress());
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public Long getPercentageUsed() {
        try {
//            Integer size = (Integer) mbs.getAttribute(memorySpace, "state");
            CompositeDataSupport size = (CompositeDataSupport) mbs.getAttribute(memorySpace,"Usage");

            if(size.containsKey("max") && size.containsKey("used")) {
                Long used = (Long) size.get("used");
                Long max = (Long) size.get("max");

                System.out.println("Used: "+used.doubleValue()+" Max: "+max.doubleValue());
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

    public Long getSize() {
        try {
//            Integer size = (Integer) mbs.getAttribute(memorySpace, "state");
            CompositeDataSupport size = (CompositeDataSupport) mbs.getAttribute(memorySpace,"Usage");

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

    public Long getUsed() {
        try {
//            Integer size = (Integer) mbs.getAttribute(memorySpace, "state");
            CompositeDataSupport size = (CompositeDataSupport) mbs.getAttribute(memorySpace,"Usage");

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

    public Long getInit() {
        try {
//            Integer size = (Integer) mbs.getAttribute(memorySpace, "state");
            CompositeDataSupport size = (CompositeDataSupport) mbs.getAttribute(memorySpace,"Usage");

            if(size.containsKey("init")) {
                return (Long) size.get("init");
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

    public Long getCommitted() {
        try {
//            Integer size = (Integer) mbs.getAttribute(memorySpace, "state");
            CompositeDataSupport size = (CompositeDataSupport) mbs.getAttribute(memorySpace,"Usage");

            if(size.containsKey("committed")) {
                return (Long) size.get("committed");
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

    public String getMemoryAddress() {
        return memoryAddress;
    }

    public void setMemoryAddress(String memoryAddress) {
        this.memoryAddress = memoryAddress;
    }
}
