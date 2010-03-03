package au.edu.une.monitor;

/**
 * Created by IntelliJ IDEA.
 * User: gerwood
 * Date: 09/02/2010
 * Time: 10:00:42 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ServletStats {

    public Integer getErrorCount(String module,String servlet);
    public Long getMaxTime(String module, String servlet);
    public Long getMinTime(String module, String servlet);
    public Long getAvgRequestTime(String module, String servlet);
    public Integer getRequests(String module, String servlet);
    public Long getProcessingTime(String module, String servlet);

    public Long getProcessingTimeSinceLastPoll(String module, String servlet);
    public Long getRequestsSinceLastPoll(String module, String servlet);
    public Long getAvgRequestTimeSinceLastPoll(String module, String servlet);
}
