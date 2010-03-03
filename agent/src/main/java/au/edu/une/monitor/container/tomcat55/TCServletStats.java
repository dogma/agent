package au.edu.une.monitor.container.tomcat55;

import au.edu.une.monitor.ServletStats;

import javax.management.ObjectName;
import javax.management.MBeanServer;
import java.util.HashMap;
import java.lang.management.ManagementFactory;

/**
 * Created by IntelliJ IDEA.
 * User: gerwood
 * Date: 09/02/2010
 * Time: 10:00:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class TCServletStats implements ServletStats {

    private HashMap<String,Long> requestsCache;
    private HashMap<String,Long> processingCache;
    private HashMap<String,Long[]> avgCache;

    private String moduleURI = "Catalina:j2eeType=Servlet,J2EEApplication=none,J2EEServer=none,WebModule=";
    private MBeanServer mbs;

    public TCServletStats () {
        mbs = ManagementFactory.getPlatformMBeanServer();

        requestsCache = new HashMap<String, Long>();
        processingCache = new HashMap<String, Long>();
        avgCache = new HashMap<String, Long[]>();
    }

    public Integer getErrorCount(String module, String servlet) {
        try {
            ObjectName moduleON = new ObjectName(moduleURI + module+",name="+servlet);
            return (Integer) mbs.getAttribute(moduleON, "errorCount");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long getMaxTime(String module, String servlet) {
        try {
            ObjectName moduleON = new ObjectName(moduleURI + module+",name="+servlet);
            return (Long) mbs.getAttribute(moduleON, "maxTime");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long getMinTime(String module, String servlet) {
        try {
            ObjectName moduleON = new ObjectName(moduleURI + module+",name="+servlet);
            return (Long) mbs.getAttribute(moduleON, "minTime");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long getProcessingTime(String module, String servlet) {
        try {
            ObjectName moduleON = new ObjectName(moduleURI + module+",name="+servlet);
            return (Long) mbs.getAttribute(moduleON, "processingTime");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long getProcessingTimeSinceLastPoll(String module, String servlet) {
        Long time = getProcessingTime(module,servlet);
        Long finalTime = time;
        if(processingCache.containsKey(module+servlet)) {
            finalTime = time - processingCache.get(module+servlet);
        }
        processingCache.put(module+servlet,time);

        return finalTime;
    }

    public Integer getRequests(String module, String servlet) {
        try {
            ObjectName moduleON = new ObjectName(moduleURI + module+",name="+servlet);
            return (Integer) mbs.getAttribute(moduleON, "requestCount");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long getRequestsSinceLastPoll(String module, String servlet) {
        Long time = getProcessingTime(module,servlet);
        Long finalTime = time;
        if(requestsCache.containsKey(module+servlet)) {
            finalTime = time - requestsCache.get(module+servlet);
        }
        requestsCache.put(module+servlet,time);

        return finalTime;
    }

    public Long getAvgRequestTime(String module, String servlet) {
        try {
            if(getRequests(module,servlet) == 0) { return 0L; }
            return getProcessingTime(module,servlet) / getRequests(module, servlet);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long getAvgRequestTimeSinceLastPoll(String module, String servlet) {
        Long data[] = new Long[2];
        data[0] = getProcessingTime(module, servlet);
        data[1] = getRequests(module,servlet).longValue();
        Long finalData[] = new Long[2];
        if(avgCache.containsKey(module+servlet)) {
            finalData[0] = data[0] - avgCache.get(module+servlet)[0];
            finalData[1] = data[1] - avgCache.get(module+servlet)[1];
        } else {
            finalData = data;
        }
        avgCache.put(module+servlet,data);

        if(finalData[1] == 0) { return 0L; }
        return finalData[0] / finalData[1];
    }
}
