package au.edu.une.monitor.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.une.monitor.container.tomcat55.TomcatServerState;

/**
 * Created by IntelliJ IDEA.
 * User: gstewar8
 * Date: Feb 8, 2010
 * Time: 2:55:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class TomcatContainersMonitor implements Controller {
    TomcatServerState tss;
    private String defaultView;

    public TomcatContainersMonitor () {
        tss = new TomcatServerState();
    }
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView(getDefaultView());
        tss.getContexts();

        return mav;
    }

    public String getDefaultView() {
        return defaultView;
    }

    public void setDefaultView(String defaultView) {
        this.defaultView = defaultView;
    }
}
