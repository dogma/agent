package au.edu.une.monitor.web;

import au.edu.une.monitor.exceptions.RuntimeConfigInitialisationException;
import au.edu.une.monitor.exceptions.StateNotFoundException;
import au.edu.une.monitor.exceptions.CannotSetStateException;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: mpeters5
 * Date: 22/06/2010
 * Time: 2:31:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuntimeConfigDAOPropertiesFile implements RuntimeConfigDAO {
    private String runtimePropertiesFilename = "/tmp/agent.runtime.properties";
    private Properties runtimeProperties = null;
    private State defaultState = new State(State.ENABLED);
    private State currentState = new State(defaultState.toString());
    private final static String PROPERTY_KEY_FOR_CURRENT_STATE = "currentState";
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private static final String PROP_FILE_COMMENT = "Runtime configuration of Agent";

    @Override
    public State getState() throws StateNotFoundException {
        // If value currently unknown, get from properties
        if (runtimeProperties == null) {
            logger.debug("Null runtime properties when getting state. Attempting an init...");
            try {
                initRuntimeProperties();
            } catch (RuntimeConfigInitialisationException e) {
                throw new StateNotFoundException("While getting runtime config state, could not init runtime properties.", e);
            }
            currentState = new State(runtimeProperties.getProperty(PROPERTY_KEY_FOR_CURRENT_STATE));
        }

        logger.debug("Returning currentState of "+currentState);
        // Return known value
        return currentState;
    }

    @Override
    public void setState(State agentState) throws CannotSetStateException {
        // Update both currentState object and property in properties file
        currentState = agentState;
        if (runtimeProperties == null) {
            try {
                initRuntimeProperties();
            } catch (RuntimeConfigInitialisationException e) {
                throw new CannotSetStateException("While setting runtime config state, could not init runtime properties.", e);
            }
        }
        runtimeProperties.setProperty(PROPERTY_KEY_FOR_CURRENT_STATE, currentState.toString());

        // Persist runtime properties
        FileOutputStream out;
        try {
             out = new FileOutputStream(runtimePropertiesFilename);
        } catch (FileNotFoundException e) {
            throw new CannotSetStateException("Could not persist state because properties file not found at "+runtimePropertiesFilename+".", e);
        }
        try {
            runtimeProperties.store(out, PROP_FILE_COMMENT);
            out.close();
        } catch (IOException e) {
            throw new CannotSetStateException("Could not persist state because of an IOException while writing to properties file.", e);
        }
    }

    private void initRuntimeProperties() throws RuntimeConfigInitialisationException {
        runtimeProperties = new Properties();
        // Load from properties file;
        FileInputStream in;
        try { // Try opening the properties file
            in = new FileInputStream(runtimePropertiesFilename);
        } catch (FileNotFoundException e) {
            // Try creating the properties file
            FileOutputStream out;
            try { // Try creating the properties file
                 out = new FileOutputStream(runtimePropertiesFilename);
            } catch (FileNotFoundException fnfe) {
                throw new RuntimeConfigInitialisationException("While initialising runtime properties, could not create properties file at "+runtimePropertiesFilename+".", fnfe);
            }
            try { // Try writing the properties file
                runtimeProperties.store(out, PROP_FILE_COMMENT);
                out.close();
            } catch (IOException ioe) {
                throw new RuntimeConfigInitialisationException("While initialising runtime properties, encountered an IOException when writing to properties file.", ioe);
            }
            try { // try opening the properties file again, now that it has been created
                in = new FileInputStream(runtimePropertiesFilename);
            } catch (FileNotFoundException fnfe) {
                throw new RuntimeConfigInitialisationException("While initialising runtime properties, could not open properties file at "+runtimePropertiesFilename+".", fnfe);
            }
        }

        try { // By this point, we have a FileInputStream, so let try to load it
            runtimeProperties.load(in);
        } catch (IOException e) {
            throw new RuntimeConfigInitialisationException("While initialising runtime properties, encountered IOException while loading properties from opened properties file.", e);
        }
    }

    public String getRuntimePropertiesFilename() {
        return runtimePropertiesFilename;
    }

    public void setRuntimePropertiesFilename(String runtimePropertiesFilename) {
        this.runtimePropertiesFilename = runtimePropertiesFilename;
    }

    public State getDefaultState() {
        return defaultState;
    }

    public void setDefaultState(State defaultState) {
        this.defaultState = defaultState;
    }

}
