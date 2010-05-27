package au.edu.une.monitor.utility;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Used to inject beans into the Application Context (Attributes).
 * @author gerwood
 * Date: May 27, 2010
 * Time: 6:09:17 PM
 */
public class WebAppSetup implements ApplicationContextAware, ApplicationListener {

    WebApplicationContext wac;
    private HashMap<String,Object> toSet;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        System.out.println("Wiring app settings into startup: "+applicationContext);
        wac = (WebApplicationContext) applicationContext;

    }

    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof ContextRefreshedEvent) {
            Set keys = toSet.keySet();
            Iterator i = keys.iterator();
            ServletContext sc = wac.getServletContext();
            while(i.hasNext()) {
                String key = (String) i.next();
                //logger.trace("Setting "+key+" - "+toSet.get(key));
                sc.setAttribute(key,toSet.get(key));
            }
        }

        //Added to ensure that no references are kept to the application context.
        if(event instanceof ContextClosedEvent) {
            wac = null;
            toSet = null;
        }
    }

    public HashMap<String, Object> getToSet() {
        return toSet;
    }

    /**
     * This is a hashmap of objects that will be put into the application context when the application starts.
     * It is triggered on either ContextStartedEvent or ContextRefreshedEvent.
     * @param toSet
     */
    public void setToSet(HashMap<String, Object> toSet) {
        this.toSet = toSet;
    }
}
