package au.edu.une.monitor;

/**
 * Created by IntelliJ IDEA.
 * User: gerwood
 * Date: 09/02/2010
 * Time: 9:10:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface WebModuleLoader {

    public String[] getModules();

    public String[] getModuleServlets(String module);
    public Boolean getState(String module);
    public Long getProcessingTime(String module);
    public Long getProcessingTimeSinceLastPoll(String module);

}
