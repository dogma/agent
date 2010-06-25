package au.edu.une.monitor.web;

import au.edu.une.monitor.exceptions.CannotSetStateException;
import au.edu.une.monitor.exceptions.RuntimeConfigInitialisationException;
import au.edu.une.monitor.exceptions.StateNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: mpeters5
 * Date: 25/06/2010
 * Time: 9:20:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class RuntimeConfigDAOPropertiesFileImplTest extends RuntimeConfigDAOIComplianceTests {
    RuntimeConfigDAOPropertiesFileImpl runtimeConfigDAOPropertiesFile = (RuntimeConfigDAOPropertiesFileImpl)runtimeConfigDAO;

    public RuntimeConfigDAOPropertiesFileImplTest(final RuntimeConfigDAO runtimeConfigDAOImpl) {
        super(runtimeConfigDAOImpl);
    }

    /**
     * This tests the situation when the state is asked to be set, but the properties file does not yet exist.
     * The implementation should create the properties file, and then set the state.
     */
    @Test
    public void setStateWhenFileNotExistTest() {
        try {
            deleteFile(runtimeConfigDAOPropertiesFile.getRuntimePropertiesFilename());

            // Perform the operation
            runtimeConfigDAOPropertiesFile.setState(new State(State.DISABLED));
            Assert.assertTrue(runtimeConfigDAOPropertiesFile.currentState.isDisabled());
        } catch (Throwable e) {
            Assert.fail("The properties file should have been created.");
        }
    }

    /**
     * This tests the situation when the state is asked to be set, but the properties file being used
     * is already a directory, and so cannot be used as a properties file.
     * The implementation should throw an exception in this case.
     */
    @Test
    public void setStateWhenFileCannotExistTest() {
        try {
            deleteFile(runtimeConfigDAOPropertiesFile.getRuntimePropertiesFilename());
            // make a directory by the same name as the properties file. This will result in a FNFException when trying to open it as a file
            (new File(runtimeConfigDAOPropertiesFile.getRuntimePropertiesFilename())).mkdirs();

            // Perform the operation
            runtimeConfigDAOPropertiesFile.setState(new State(State.DISABLED));
            Assert.fail("The properties file should already exist as a directory and so an exceptions should have been thrown.");
        } catch (CannotSetStateException e) {
            // Passed the test
        }
        (new File(runtimeConfigDAOPropertiesFile.getRuntimePropertiesFilename())).delete();
    }

    /**
     * Testing the setting of the properties filename, standard conditions
     */
    @Test
    public void setRuntimePropertiesFileNameTest() {
        try {
            runtimeConfigDAOPropertiesFile.setRuntimePropertiesFilename(runtimeConfigDAOPropertiesFile.getRuntimePropertiesFilename());
        } catch (RuntimeConfigInitialisationException e) {
            Assert.fail("The file should have been created OK.");
        }
    }

    /**
     * Testing the setting of the properties filename when current state is set to disabled
     */
    @Test
    public void setRuntimePropertiesFileNameWithDisabledStateTest() {
        try {
            runtimeConfigDAOPropertiesFile.setState(new State(State.DISABLED));
        } catch (CannotSetStateException e) {
            Assert.fail("Cannot set state for some reason...");
        }
        try {
            runtimeConfigDAOPropertiesFile.setRuntimePropertiesFilename(runtimeConfigDAOPropertiesFile.getRuntimePropertiesFilename());
        } catch (RuntimeConfigInitialisationException e) {
            Assert.fail("The file should have been created OK.");
        }
        try {
            Assert.assertEquals(true, runtimeConfigDAOPropertiesFile.getState().isDisabled());
        } catch (StateNotFoundException e) {
            Assert.fail("Cannot find state for some reason...");
        }
    }

//    /**
//     * Testing the setting of the properties filename
//     */
//    @Test
//    public void setRuntimePropertiesFileNameTest() {
//        try {
//            runtimeConfigDAOPropertiesFile.setRuntimePropertiesFilename(runtimeConfigDAOPropertiesFile.getRuntimePropertiesFilename());
//        } catch (RuntimeConfigInitialisationException e) {
//            Assert.fail("The file should have been created OK.");
//        }
//    }

    /**
     * Testing the setting of the properties filename when the filename already exists as a directory
     */
    @Test
    public void setRuntimePropertiesFileNameWhenFileCannotExistTest() {
        // make a directory by the same name as the properties file. This will result in a FNFException when trying to open it as a file
        (new File(runtimeConfigDAOPropertiesFile.getRuntimePropertiesFilename())).mkdirs();
        try {
            runtimeConfigDAOPropertiesFile.setRuntimePropertiesFilename(runtimeConfigDAOPropertiesFile.getRuntimePropertiesFilename());
            Assert.fail("The file already exists as a directory. An exception should have been thrown instead.");
        } catch (RuntimeConfigInitialisationException e) {
        }
        (new File(runtimeConfigDAOPropertiesFile.getRuntimePropertiesFilename())).delete();
    }


    private void deleteFile(String runtimePropertiesFilename) {
        // A File object to represent the filename
        File f = new File(runtimePropertiesFilename);

        // Make sure the file or directory exists and isn't write protected
        if (!f.exists())
            return; // already gone!

        if (!f.canWrite())
            throw new IllegalArgumentException("Delete: write protected: "
                    + runtimePropertiesFilename);

        // If it is a directory, make sure it is empty
        if (f.isDirectory()) {
            String[] files = f.list();
            if (files.length > 0)
                throw new IllegalArgumentException(
                        "Delete: directory not empty: " + runtimePropertiesFilename);
        }

        // Attempt to delete it
        if (!f.delete())
            throw new IllegalArgumentException("Delete: deletion failed");
    }
}
