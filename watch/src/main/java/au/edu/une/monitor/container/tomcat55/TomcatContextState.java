package au.edu.une.monitor.container.tomcat55;

import au.edu.une.monitor.Container;
import au.edu.une.monitor.Monitor;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * Created by IntelliJ IDEA.
 * User: gstewar8
 * Date: Feb 5, 2010
 * Time: 9:15:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class TomcatContextState implements Container, Monitor {

    private String context = "Catalina:j2eeType=WebModule,name=//localhost/manager,J2EEApplication=none,J2EEServer=none";
    private String name = "";

    public String name() {
        return name;
    }

    public TomcatContextState(String context,String name) {
        this.context = context;
        this.name = name;
    }

    /**
     * Finds and returns the state of a given context...
     * This method assumes
     *
     * @return
     */
    public Boolean state() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName on = new ObjectName(context);
            Integer state = (Integer) mbs.getAttribute(on, "state");

            if (state == 1) {
                return true;
            }
        } catch (MBeanException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (AttributeNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ReflectionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return false;
    }

    public boolean check() {
        return state();
    }
}
