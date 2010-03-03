package au.edu.une.monitor.container.tomcat55;

import au.edu.une.monitor.WebModuleLoader;

import javax.management.*;
import javax.management.openmbean.CompositeDataSupport;
import java.lang.management.ManagementFactory;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: gerwood
 * Date: 09/02/2010
 * Time: 9:14:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebModuleStats implements WebModuleLoader {

    private HashMap<String,Long> processingTimesPollMemory;
    //The query string needed to locate all current 'WebModule's
    private String moduleQuery = "Catalina:j2eeType=WebModule,J2EEApplication=none,J2EEServer=none,name=";
    private MBeanServer mbs;

    public WebModuleStats() {
        mbs = ManagementFactory.getPlatformMBeanServer();
        processingTimesPollMemory =  new HashMap<String, Long>();
    }

    public Boolean getState(String module) {
        try {
            ObjectName moduleON = new ObjectName(moduleQuery + module);
            Integer state = (Integer) mbs.getAttribute(moduleON, "state");
            if (state > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long getProcessingTime(String module) {
        try {
            ObjectName moduleON = new ObjectName(moduleQuery + module);
            Long pTime = (Long) mbs.getAttribute(moduleON, "processingTime");
            return pTime;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long getProcessingTimeSinceLastPoll(String module) {
        Long time = getProcessingTime(module);
        Long finalTime = time;
        if(processingTimesPollMemory.containsKey(module)) {
            finalTime = time - processingTimesPollMemory.get(module);
        }
        processingTimesPollMemory.put(module,time);

        return finalTime;
    }

    public String[] getModules() {
        try {
            ObjectName query = new ObjectName(moduleQuery + "*");
            Set<ObjectName> ons = mbs.queryNames(query, null);

            ArrayList<String> spaces = new ArrayList<String>();

            for (ObjectName on : ons) {
                spaces.add(on.getKeyProperty("name"));
            }
            return spaces.toArray(new String[spaces.size()]);

        } catch (MalformedObjectNameException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }

    public String[] getModuleServlets(String module) {
        try {
            if(module == null) { return null; }
            ObjectName moduleON = new ObjectName(moduleQuery + module);
            String[] servlets = (String[]) mbs.getAttribute(moduleON, "servlets");

            ArrayList<String> spaces = new ArrayList<String>();

            for (String s: servlets) {
                System.out.println("Servlet: "+s);
                ObjectName on = new ObjectName(s);
                System.out.println("Name: "+on.getKeyProperty("name"));
                spaces.add(on.getKeyProperty("name"));
            }
            return spaces.toArray(new String[spaces.size()]);
            
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }
}
