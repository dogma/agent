package au.edu.une.monitor.web;

import au.edu.une.monitor.exceptions.StateNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.une.monitor.Monitor;

import java.util.HashMap;
import java.util.Set;

/**
 * The AvailabilityMonitor reads from a
 * User: gstewar8
 * Date: Feb 4, 2010
 * Time: 4:11:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class AvailabilityMonitor implements Controller {
    private String view;
    private HashMap<String, Monitor> monitors;
    private RuntimeConfigDAO runtimeConfigDAO;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView(getView());

        mav.addObject("status", "OK");
        try {
            if (runtimeConfigDAO.getState().isDisabled()) {
                mav.addObject("status", State.DISABLED.toUpperCase());
                response.setStatus(500);
            }
        } catch (StateNotFoundException e) {
            logger.error("Could not retrieve currently configured state of agent. "+e.getMessage());
        }
        if (monitors != null) {
            Set<String> keys = monitors.keySet();
            for (String s : keys) {
                if (!monitors.get(s).check()) {
                    mav.addObject("status", "CRITICAL");
                    response.setStatus(500);
                    return mav;
                }
            }
        }
        return mav;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public HashMap<String, Monitor> getMonitors() {
        return monitors;
    }

    public void setMonitors(HashMap<String, Monitor> monitors) {
        this.monitors = monitors;
    }

    public RuntimeConfigDAO getRuntimeConfigDAO() {
        return runtimeConfigDAO;
    }

    public void setRuntimeConfigDAO(RuntimeConfigDAO runtimeConfigDAO) {
        this.runtimeConfigDAO = runtimeConfigDAO;
    }

}
