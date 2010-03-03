package au.edu.une.monitor;

/**
 * Created by IntelliJ IDEA.
 * User: gstewar8
 * Date: Feb 4, 2010
 * Time: 4:12:34 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Monitor {

    /**
     * This interface is defined to allow a class to implement a check method
     * that will return a true of false based on an internal health check.
     * @return True/False
     */
    public boolean check();
}
