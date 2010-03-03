package au.edu.une.monitor.container.tomcat55;

import javax.management.*;
import java.util.List;
import java.util.Hashtable;
import java.util.Set;
import java.lang.management.ManagementFactory;

/**
 * Created by IntelliJ IDEA.
 * User: gstewar8
 * Date: Feb 8, 2010
 * Time: 2:52:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class TomcatServerState {

    private String context = "Catalina:j2eeType=WebModule,J2EEApplication=none,J2EEServer=none";

    public List<String> getContexts () {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName on = new ObjectName(context);
            System.out.println(" canonical "+on.getCanonicalKeyPropertyListString());
            System.out.println(" key property "+on.getKeyPropertyListString());
            System.out.println(" list "+on.getKeyPropertyList());

            Hashtable<String,String> props = on.getKeyPropertyList();

            ObjectName onq = new ObjectName("Catalina:j2eeType=WebModule");
            Set<ObjectName> names = mbs.queryNames(onq,null);
            for(ObjectName o: names) {
                System.out.println("Found: "+o.getCanonicalName());
            }
            mbs.queryMBeans(onq,null);

            for(String key: props.keySet()) {
                System.out.println("Key: "+key+", "+props.get(key));
            }
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }
}
