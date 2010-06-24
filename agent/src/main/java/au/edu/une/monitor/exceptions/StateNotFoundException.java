package au.edu.une.monitor.exceptions;

import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: mpeters5
 * Date: 22/06/2010
 * Time: 2:50:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class StateNotFoundException extends Throwable {
    public StateNotFoundException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
