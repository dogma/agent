package au.edu.une.monitor.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.une.monitor.Container;

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

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();

        mav.setViewName(getDefaultView());

        if(request.getAttribute("context") != null) {
            for(Container c: containers) {
                if(c.name().equals((String) request.getAttribute("context"))) {
                    mav.addObject("contextName",c.name());
                    mav.addObject("contextState",c.state());
                    return mav;
                }
            }
        } else {
            System.out.println("Running generic monitoring");
            HashMap<String,Boolean> states = new HashMap<String, Boolean>();
            for(Container c: containers) {
                states.put(c.name(), c.state());
            }

            mav.addObject("states",states);

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
