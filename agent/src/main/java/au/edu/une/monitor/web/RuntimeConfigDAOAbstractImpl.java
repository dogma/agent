package au.edu.une.monitor.web;

import au.edu.une.monitor.exceptions.CannotSetStateException;
import au.edu.une.monitor.exceptions.StateNotFoundException;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: mpeters5
 * Date: 25/06/2010
 * Time: 9:54:24 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class RuntimeConfigDAOAbstractImpl implements RuntimeConfigDAO {
    protected State defaultState;
    protected State currentState;
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    public RuntimeConfigDAOAbstractImpl() {
        setDefaultState(new State(State.ENABLED));
        resetStateToDefault();
    }

    @Override
    abstract public State getState() throws StateNotFoundException;

    @Override
    abstract public void setState(State agentState) throws CannotSetStateException;

    @Override
    public void clearState() {
        // Set current state object to null
        resetStateToDefault();
    }

    public State getDefaultState() {
        return defaultState;
    }

    public void setDefaultState(State defaultState) {
        this.defaultState = defaultState;
    }

    protected void resetStateToDefault() {
        currentState = new State(defaultState.toString());
    }
}
