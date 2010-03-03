package au.edu.une.monitor.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.une.monitor.ServletStats;
import au.edu.une.monitor.container.tomcat55.TCServletStats;

/**
 * Created by IntelliJ IDEA.
 * User: gerwood
 * Date: 09/02/2010
 * Time: 10:41:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServletsMonitor implements Controller {

    private String defaultView;
    private String indexView;
    private ServletStats sstats;

    public ServletsMonitor () {
        sstats = new TCServletStats();
    }

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView(getDefaultView());

        if (request.getParameter("context") != null) {
            String context = request.getParameter("context");
            String servlet = request.getParameter("servlet");
            String metric = (String) request.getAttribute("metric");

            if(metric.equals("avg")) {
                mav.addObject("status",sstats.getAvgRequestTime(context,servlet));
            } else if(metric.equals("maxTime")) {
                mav.addObject("status", sstats.getMaxTime(context,servlet));
            } else if(metric.equals("minTime")) {
                mav.addObject("status", sstats.getMinTime(context,servlet));
            } else if(metric.equals("errors")) {
                mav.addObject("status", sstats.getErrorCount(context,servlet));
            } else if(metric.equals("processingTime")) {
                mav.addObject("status", sstats.getProcessingTime(context,servlet));
            } else if(metric.equals("requests")) {
                mav.addObject("status", sstats.getRequests(context,servlet));

            } else if(metric.equals("avgSincePoll")) {
                mav.addObject("status", sstats.getAvgRequestTimeSinceLastPoll(context,servlet));
            } else if(metric.equals("processingSincePoll")) {
                mav.addObject("status", sstats.getProcessingTimeSinceLastPoll(context,servlet));
            } else if(metric.equals("requestsSincePoll")) {
                mav.addObject("status", sstats.getRequestsSinceLastPoll(context,servlet));
            }
        } else {
            mav.setViewName(indexView);
        }

        return mav;
    }

    public String getDefaultView() {
        return defaultView;
    }

    public void setDefaultView(String defaultView) {
        this.defaultView = defaultView;
    }
}
