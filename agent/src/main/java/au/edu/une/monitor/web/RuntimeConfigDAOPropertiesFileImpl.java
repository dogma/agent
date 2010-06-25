package au.edu.une.monitor.web;

import au.edu.une.monitor.exceptions.RuntimeConfigInitialisationException;
import au.edu.une.monitor.exceptions.StateNotFoundException;
import au.edu.une.monitor.exceptions.CannotSetStateException;

import java.io.*;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: mpeters5
 * Date: 22/06/2010
 * Time: 2:31:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuntimeConfigDAOPropertiesFileImpl extends RuntimeConfigDAOAbstractImpl {
    private String runtimePropertiesFileDirectory = "/tmp";
    private String runtimePropertiesFileName = "agent.runtime.properties";
    private String runtimePropertiesFile = runtimePropertiesFileDirectory+"/"+runtimePropertiesFileName;
    private Properties runtimeProperties = null;
    private final static String PROPERTY_KEY_FOR_CURRENT_STATE = "currentState";
    private static final String PROP_FILE_COMMENT = "Runtime configuration of Agent";

    public RuntimeConfigDAOPropertiesFileImpl() throws RuntimeConfigInitialisationException {
        super();
        initRuntimeProperties();
    }

    @Override
    public State getState() throws StateNotFoundException {
        logger.debug("Returning currentState of "+currentState);
        // Return known value
        return currentState;
    }

    @Override
    public void setState(State agentState) throws CannotSetStateException {
        // Update both currentState object and property in properties file
        currentState = agentState;

        // Assuming runtimeProperties is never null
        runtimeProperties.setProperty(PROPERTY_KEY_FOR_CURRENT_STATE, currentState.toString());

        // Persist runtime properties
        FileOutputStream out;
        try {
             out = new FileOutputStream(runtimePropertiesFile);
        } catch (FileNotFoundException e) {
                throw new CannotSetStateException("Could not persist state because of an exception while accessing the properties file: "+ runtimePropertiesFile, e);
        }

        try {
            runtimeProperties.store(out, PROP_FILE_COMMENT);
            out.close();
        } catch (IOException e) {
            throw new CannotSetStateException("Could not persist state because of an IOException while writing to properties file.", e);
        }
    }

    @Override
    public void clearState() {
        // Remove properties file
        String fileName = runtimePropertiesFile;
        // A File object to represent the filename
        File f = new File(fileName);

        // Clear the in-memory object state
        super.clearState();

        // Make sure the file or directory exists and isn't write protected
        if (!f.exists())
          return; // File not there to delete

        if (!f.canWrite())
          throw new IllegalArgumentException("Problem clearing state: Cannot remove properties file, it is write protected: "
              + fileName);

        // If it is a directory, make sure it is empty
        if (f.isDirectory()) {
            throw new IllegalArgumentException(
                "Problem clearing state: specified properties file is actually a directory: " + fileName);
        }

        // Attempt to delete it
        if (!f.delete())
          throw new IllegalArgumentException("Problem clearing state: deletion of properties file failed: "+fileName);

    }

    private void initRuntimeProperties() throws RuntimeConfigInitialisationException {
        runtimeProperties = new Properties();
        // Load from properties file;
        FileInputStream in;
        try { // Try opening the properties file
            in = new FileInputStream(runtimePropertiesFile);
        } catch (FileNotFoundException e) {
            // Try creating the properties file
            FileOutputStream out;
            try { // Try creating the properties file
                 out = new FileOutputStream(runtimePropertiesFile);
            } catch (FileNotFoundException fnfe) {
                setRuntimePropertiesToCurrentState();
                throw new RuntimeConfigInitialisationException("While initialising runtime properties, could not create properties file at "+ runtimePropertiesFile +".", fnfe);
            }
            try { // Try writing the properties file
                runtimeProperties.store(out, PROP_FILE_COMMENT);
                out.close();
            } catch (IOException ioe) {
                setRuntimePropertiesToCurrentState();
                throw new RuntimeConfigInitialisationException("While initialising runtime properties, encountered an IOException when writing to properties file.", ioe);
            }
            try { // try opening the properties file again, now that it has been created
                in = new FileInputStream(runtimePropertiesFile);
            } catch (FileNotFoundException fnfe) {
                setRuntimePropertiesToCurrentState();
                throw new RuntimeConfigInitialisationException("While initialising runtime properties, could not open properties file at "+ runtimePropertiesFile +".", fnfe);
            }
        }

        try { // By this point, we have a FileInputStream, so let try to load it
            runtimeProperties.load(in);
            in.close();
        } catch (IOException e) {
            setRuntimePropertiesToCurrentState();
            throw new RuntimeConfigInitialisationException("While initialising runtime properties, encountered IOException while loading properties from opened properties file.", e);
        }
    }

    protected String getRuntimePropertiesFile() {
        return runtimePropertiesFile;
    }

    private void constructRuntimePropertiesFile() throws RuntimeConfigInitialisationException {
        runtimePropertiesFile = runtimePropertiesFileDirectory + "/" + runtimePropertiesFileName;
        initRuntimeProperties();
    }

    private void setRuntimePropertiesToCurrentState() {
        runtimeProperties = new Properties();
        runtimeProperties.setProperty(PROPERTY_KEY_FOR_CURRENT_STATE, currentState.toString());
    }

    protected String getRuntimePropertiesFileName() {
        return runtimePropertiesFileName;
    }

    public void setRuntimePropertiesFileName(String runtimePropertiesFilename) throws RuntimeConfigInitialisationException {
        if (runtimePropertiesFilename.startsWith("/")) {
            this.runtimePropertiesFileName = runtimePropertiesFilename.substring(1);
        } else {
            this.runtimePropertiesFileName = runtimePropertiesFilename;
        }
        constructRuntimePropertiesFile();
    }

    protected String getRuntimePropertiesFileDirectory() {
        return runtimePropertiesFileDirectory;
    }

    public void setRuntimePropertiesFileDirectory(String runtimePropertiesFileDirectory) throws RuntimeConfigInitialisationException {
        if (runtimePropertiesFileDirectory.endsWith("/")) {
            this.runtimePropertiesFileDirectory = runtimePropertiesFileDirectory.substring(0, runtimePropertiesFileDirectory.length()-1);
        } else {
            this.runtimePropertiesFileDirectory = runtimePropertiesFileDirectory;
        }
        constructRuntimePropertiesFile();
    }
}
