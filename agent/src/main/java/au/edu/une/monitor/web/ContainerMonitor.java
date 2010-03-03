package au.edu.une.monitor.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.une.monitor.Container;
import au.edu.une.monitor.WebModuleLoader;
import au.edu.une.monitor.container.tomcat55.WebModuleStats;
import au.edu.une.monitor.jvm.NamedMemorySpaceStats;

import java.util.List;
import java.util.HashMap;

/**
 * Used to check on the status of containers being run.
 * User: gstewar8
 * Date: Feb 4, 2010
 * Time: 6:00:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContainerMonitor implements Controller {

    private List<Container> containers;
    private String defaultView;
    private WebModuleLoader mstats;

    public ContainerMonitor() {
        mstats = new WebModuleStats();
    }

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView(defaultView);

        if (request.getParameter("context") != null) {
            String context = request.getParameter("context");
            String metric = (String) request.getAttribute("metric");

            if(metric.equals("state")) {
                mav.addObject("state",mstats.getState(context));
            } else if(metric.equals("servlets")) {
                mav.addObject("servlets", mstats.getModuleServlets(context));
            } else if(metric.equals("processingTime")) {
                mav.addObject("processingTime", mstats.getProcessingTime(context));
            } else if(metric.equals("processingTimeSincePoll")) {
                mav.addObject("interimProcessing", mstats.getProcessingTimeSinceLastPoll(context));
            }

            mav.addObject("context",context);
        } else {
            String[] spaces = mstats.getModules();
            mav.addObject("modules", spaces);
        }

        return mav;
    }


    public List<Container> getContainers() {
        return containers;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    public String getDefaultView() {
        return defaultView;
    }

    public void setDefaultView(String defaultView) {
        this.defaultView = defaultView;
    }
}
