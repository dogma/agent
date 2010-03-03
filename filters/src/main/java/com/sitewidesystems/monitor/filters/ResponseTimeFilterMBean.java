package com.sitewidesystems.monitor.filters;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: gerwood
 * Date: 08/02/2010
 * Time: 10:36:51 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ResponseTimeFilterMBean {

    public long[] getTiming();
    public long getResolution();
    public int getScope();
    public HashMap<Date,long[]> getHistory();
}
