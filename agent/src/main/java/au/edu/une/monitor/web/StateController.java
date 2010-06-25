package au.edu.une.monitor.web;

import au.edu.une.monitor.exceptions.CannotSetStateException;
import au.edu.une.monitor.exceptions.StateNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mpeters5
 * Date: 22/06/2010
 * Time: 11:55:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class StateController implements Controller {
    private String view;
    private RuntimeConfigDAO runtimeConfigDAO;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public void setView(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        State newState = null;
        State currentState = null;
        String[] targets = request.getRequestURI().split("/");
        String agentStateUrl = "/"+targets[1]+"/"+targets[2]; 
        String agentIndexUrl = "/"+targets[1];
        ModelAndView mav = new ModelAndView(getView());

        try {
        if ("disable".equals(targets[3])) {
            // Disable agent and persist setting
            newState = new State(State.DISABLED);
            try {
                runtimeConfigDAO.setState(newState);
            } catch (CannotSetStateException e) {
                logger.error(e);
            }
        }
        if ("enable".equals(targets[3])) {
            // Enable agent and persist setting
            newState = new State(State.ENABLED);
            try {
                runtimeConfigDAO.setState(newState);
            } catch (CannotSetStateException e) {
                logger.error(e);
            }
        }
        } catch(ArrayIndexOutOfBoundsException aioobe) {
            // No prob here. This means no new state was specified to be set, that is all.
        }

        try {
            currentState = runtimeConfigDAO.getState();
        } catch (StateNotFoundException e) {
            throw new Exception("Could not determine the currently set state of the agent.", e);
        }

        mav.addObject("currentState", currentState);
        mav.addObject("newState", newState);
        mav.addObject("agentStateUrl", agentStateUrl);
        mav.addObject("agentIndexUrl", agentIndexUrl);
        return mav;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public RuntimeConfigDAO getRuntimeConfigDAO() {
        return runtimeConfigDAO;
    }

    public void setRuntimeConfigDAO(RuntimeConfigDAO runtimeConfigDAO) {
        this.runtimeConfigDAO = runtimeConfigDAO;
    }
}
