package au.edu.une.monitor.exceptions;

import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: mpeters5
 * Date: 24/06/2010
 * Time: 10:22:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class RuntimeConfigInitialisationException extends Throwable {
    public RuntimeConfigInitialisationException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
