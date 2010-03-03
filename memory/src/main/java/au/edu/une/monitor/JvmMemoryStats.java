package au.edu.une.monitor;

/**
 * Created by IntelliJ IDEA.
 * User: gstewar8
 * Date: Feb 5, 2010
 * Time: 9:16:36 AM
 * To change this template use File | Settings | File Templates.
 */
public interface JvmMemoryStats {

    public Long getNonHeapPercentage();
    public Long getHeapPercentage();

    public Long getNonHeapSize();
    public Long getHeapSize();

    public Long getNonHeapUsed();
    public Long getHeapUsed();

}
