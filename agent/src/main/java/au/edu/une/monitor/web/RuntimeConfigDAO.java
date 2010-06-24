package au.edu.une.monitor.web;

import au.edu.une.monitor.exceptions.StateNotFoundException;
import au.edu.une.monitor.exceptions.CannotSetStateException;

/**
 * Created by IntelliJ IDEA.
 * User: mpeters5
 * Date: 22/06/2010
 * Time: 2:22:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RuntimeConfigDAO {
    State getState() throws StateNotFoundException;
    void setState(State agentState) throws CannotSetStateException;
}
