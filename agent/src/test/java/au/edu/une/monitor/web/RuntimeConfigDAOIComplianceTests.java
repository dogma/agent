package au.edu.une.monitor.web;

import au.edu.une.monitor.exceptions.CannotSetStateException;
import au.edu.une.monitor.exceptions.StateNotFoundException;
import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: mpeters5
 * Date: 24/06/2010
 * Time: 12:01:11 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(Parameterized.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:/au/edu/une/monitor/test-config.xml"})
//@RunWith(ApplicationContext.class)
public class RuntimeConfigDAOIComplianceTests {
    RuntimeConfigDAO runtimeConfigDAO;
    private static Logger logger = Logger.getLogger(RuntimeConfigDAOIComplianceTests.class.getName());

    // Constructors
    public RuntimeConfigDAOIComplianceTests(final RuntimeConfigDAO runtimeConfigDAOImpl) {
        runtimeConfigDAO = runtimeConfigDAOImpl;
    }

    @Parameterized.Parameters
    public static Collection implementations() {
        ApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] {"classpath:au/edu/une/monitor/test-config.xml"});
        Collection impls = (Collection)appContext.getBean("runtimeConfigDAOImpls");
        Collection implsAsArrays = new ArrayList();
        for (Object impl : impls) {
            implsAsArrays.add(new Object[] {impl});
        }
        return implsAsArrays;
    }

    @Before
    public void setUp() {
        runtimeConfigDAO.clearState();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void getFreshStateTest() { // Expecting default state of enabled to be returned
        try {
            Assert.assertEquals(true, runtimeConfigDAO.getState().isEnabled());
        } catch (StateNotFoundException e) {
            Assert.fail("Should have returned a default state of enabled. "+e);
        }
    }

    @Test
    public void getStateAfterEnabledTest() { // Expecting set state of enabled to be returned
        try {
            runtimeConfigDAO.setState(new State(State.ENABLED));
        } catch (CannotSetStateException e) {
            Assert.fail("Exception encountered while setting state to enabled");
        }
        try {
            Assert.assertTrue(runtimeConfigDAO.getState().isEnabled());
        } catch (StateNotFoundException e) {
            Assert.fail("Exception encountered while getting current state");
        }
    }

    @Test
    public void getStateAfterDisabledTest() { // Expecting set state of disabled to be returned
        try {
            runtimeConfigDAO.setState(new State(State.DISABLED));
        } catch (CannotSetStateException e) {
            Assert.fail("Exception encountered while setting state to disabled");
        }
        try {
            Assert.assertFalse(runtimeConfigDAO.getState().isEnabled());
        } catch (StateNotFoundException e) {
            Assert.fail("Exception encountered while getting current state");
        }
    }

    @Test
    public void clearStateTest() {
        runtimeConfigDAO.clearState();
        // Default state after clearing should be enabled
        try {
            Assert.assertTrue(runtimeConfigDAO.getState().isEnabled());
        } catch (StateNotFoundException e) {
            Assert.fail("Unexpected exception. State should have been found, but was not.");
        }
    }
}
