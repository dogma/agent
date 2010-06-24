package au.edu.une.monitor.exceptions;

import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: mpeters5
 * Date: 22/06/2010
 * Time: 3:03:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class CannotSetStateException extends Throwable {
    public CannotSetStateException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
