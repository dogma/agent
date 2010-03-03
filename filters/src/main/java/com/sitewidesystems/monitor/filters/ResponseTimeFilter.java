package com.sitewidesystems.monitor.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.management.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Date;
import java.lang.management.ManagementFactory;

/**
 * This Filter is used to track the time it took the system to execute a request.
 * It aggregates this information into a set of averages over a space of one thousand
 * possible response time categories. This is done to save on potential memory
 * usage while still giving the ability to break down the response times with enough
 * resolution to give a reasonable view of distribution.
 *
 * @author gerwood
 */
public class ResponseTimeFilter implements Filter, ResponseTimeFilterMBean {
    private FilterConfig fC;
    private long timingCounters[];
    private HashMap<Date, long[]> timingCountersHistory;
    private HashMap<String, long[]> significantOutliers;

    //The scale of the counter. I.E. are response times grouped by
    //1 millisecond,
    //10 milliseconds,
    //100 millisecons etc...
    private long resolutionMulitplier = 100L;

    //The maximum number of places to track.
    //This in effect becomes the granularity of the counter...
    private int countersMax = 1000;

    //The maximum number of counter 'historys' to keep...
    private int maxHistory = 6;
    private int maxOutliers = 100;
    private boolean swapOutliers = false;

    public ResponseTimeFilter () {
    }

    public void init(FilterConfig filterConfig) throws ServletException {

        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("Monitor:area="+filterConfig.getServletContext().getContextPath()+",filter=responseTimes");
            mbs.registerMBean(this, name);
//            on = new ObjectName("Monitor:filter=" + filterConfig.getServletContext().getContextPath());
//            mbs.createMBean(ResponseTimeFilter.class.getName(), on);
//            MBeanInfo mbi = mbs.getMBeanInfo(on);

        } catch (InstanceAlreadyExistsException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (MBeanException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NotCompliantMBeanException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //Currently nothing to do here...
        timingCounters = new long[getCountersMax()];
        timingCountersHistory = new HashMap<Date, long[]>();
        significantOutliers = new HashMap<String, long[]>();
        this.fC = filterConfig;

        if (filterConfig.getInitParameter("resolution") != null) {
            resolutionMulitplier = Long.parseLong(filterConfig.getInitParameter("resolution"));
        }

        if (filterConfig.getInitParameter("history") != null) {
            maxHistory = Integer.parseInt(filterConfig.getInitParameter("history"));
        }

        if (filterConfig.getInitParameter("outliers") != null) {
            maxOutliers = Integer.parseInt(filterConfig.getInitParameter("outliers"));
        }

        if (filterConfig.getInitParameter("scope") != null) {
            countersMax = Integer.parseInt(filterConfig.getInitParameter("scope"));
        }

        if (filterConfig.getInitParameter("swapOutliers") != null) {
            swapOutliers = Boolean.parseBoolean(filterConfig.getInitParameter("swapOutliers"));
        }

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        //Need to initialise a timer count...
        Long time = System.currentTimeMillis();
        //Need to call the next link in the chain...
        filterChain.doFilter(servletRequest, servletResponse);

        time = System.currentTimeMillis() - time;

        //Once we have a 'time spent processing request' (don't forget this will exclude some of the server side
        //processing, such as time spent by any load balancer, time spent by tomcat deciding what to do with the request
        //and of course any time spent in filters 'above' this one).
        //Anyway, once we have a time we need to sort it and store it...
        addTime(time, request.getContextPath() + request.getRequestURI());
    }

    public void destroy() {
        //Need to clean up data...
        this.fC = null;
    }

    protected void initCounter() {
        for (int i = 0; i < getCountersMax(); i++) {
            timingCounters[i] = 0L;
        }
    }

    public int getScope() {
        return countersMax;
    }

    public HashMap<Date, long[]> getHistory() {
        return timingCountersHistory;
    }

    /**
     * This method is used to record a
     *
     * @param requestMilliseconds
     * @param url
     */
    protected void addTime(Long requestMilliseconds, String url) {
        System.out.println("Timing: "+requestMilliseconds);
        int category = (int) (requestMilliseconds / getResolutionMulitplier());
        if (category <= getCountersMax() && category >= 0) {
            timingCounters[category]++;
        } else {
            //It's far to big and it should probably count as an outlier...
            if (significantOutliers.containsKey(url)) {
                significantOutliers.get(url)[0] += requestMilliseconds;
                significantOutliers.get(url)[1]++;
            } else if (significantOutliers.size() < getMaxOutliers()) {
                long avg[] = new long[2];
                avg[0] = requestMilliseconds;
                avg[1] = 1;
                significantOutliers.put(url, avg);
            } else if (isSwapOutliers()) {
                //Finally if swapOutliers is enabled remove a 'less significant' outlier
                //and add this one in.
                //For the moment this is done based on the lowest average.
                String finalCandidate = null;
                Long finalCandidateAvg = null;
                for (String s : significantOutliers.keySet()) {
                    long avg = significantOutliers.get(s)[0] / significantOutliers.get(s)[1];
                    if (avg < requestMilliseconds && (avg < finalCandidateAvg || finalCandidateAvg == null)) {
                        finalCandidate = s;
                        finalCandidateAvg = avg;
                    }
                }

                if (finalCandidate != null) {
                    significantOutliers.remove(finalCandidate);
                    long avg[] = new long[2];
                    avg[0] = requestMilliseconds;
                    avg[1] = 1;
                    significantOutliers.put(url, avg);
                }
            }

        }

    }

    public long getResolutionMulitplier() {
        return resolutionMulitplier;
    }

    public void setResolutionMulitplier(long resolutionMulitplier) {
        this.resolutionMulitplier = resolutionMulitplier;
    }

    public int getCountersMax() {
        return countersMax;
    }

    public void setCountersMax(int countersMax) {
        this.countersMax = countersMax;
    }

    public int getMaxHistory() {
        return maxHistory;
    }

    public void setMaxHistory(int maxHistory) {
        this.maxHistory = maxHistory;
    }

    public int getMaxOutliers() {
        return maxOutliers;
    }

    public void setMaxOutliers(int maxOutliers) {
        this.maxOutliers = maxOutliers;
    }

    public boolean isSwapOutliers() {
        return swapOutliers;
    }

    public void setSwapOutliers(boolean swapOutliers) {
        this.swapOutliers = swapOutliers;
    }

    public long getResolution() {
        return resolutionMulitplier;
    }

    public long[] getTiming() {
        return timingCounters;
    }

    
}
