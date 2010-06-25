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
    /**
     *
     * @return State object representing the Agent's currently set state.
     * Will return an enabled state if no state has yet been specifically set
     * @throws StateNotFoundException
     */
    State getState() throws StateNotFoundException;

    /**
     *
     * @param agentState
     * @throws CannotSetStateException
     */
    void setState(State agentState) throws CannotSetStateException;

    /**
     * Clears the current state including any state which has been persisted by the implementation
     */
    void clearState();
}
