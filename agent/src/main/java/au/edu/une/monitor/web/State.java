package au.edu.une.monitor.web;

/**
 * Created by IntelliJ IDEA.
 * User: mpeters5
 * Date: 22/06/2010
 * Time: 2:25:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class State {
    Boolean enabled = true, disabled=false;
    final static String ENABLED = "enabled";
    final static String DISABLED = "disabled";

    State() {
        this.enable();
    }
    
    State(String defaultState) {
        if (ENABLED.equals(defaultState)) {
            this.enable();
        }
        if (DISABLED.equals(defaultState)) {
            this.disable();
        }
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void enable() {
        enabled = true;
        disabled = !enabled;
    }

    public Boolean isDisabled() {
        return disabled;
    }

    public void disable() {
        disabled = true;
        enabled = !disabled;
    }

    public String toString() {
        if (isEnabled()) {
            return ENABLED;
        } else {
           return DISABLED;
        }
    }
}
