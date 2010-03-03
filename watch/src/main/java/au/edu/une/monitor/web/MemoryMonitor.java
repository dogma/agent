package au.edu.une.monitor.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.une.monitor.jvm.MemoryStats;
import au.edu.une.monitor.jvm.NamedMemorySpaceStats;

import java.util.HashMap;

/**
 * Used to return the memory stats of the current jvm...
 *
 * @author gstewar8
 *         Date: Feb 5, 2010
 *         Time: 12:33:25 PM
 */
public class MemoryMonitor implements Controller {
    private MemoryStats mstats = new MemoryStats();
    private NamedMemorySpaceStats permspace;
    private String view;

    public MemoryMonitor () {
        permspace = new NamedMemorySpaceStats("java.lang:type=MemoryPool,name=CMS Perm Gen");
    }

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView(view);

        if (request.getAttribute("space") != null) {
            if (request.getAttribute("space").equals("heapConsumed")) {
                mav.addObject("name", "PercentageOfHeapFree");
                mav.addObject("value", mstats.getHeapPercentage());
            } else if (request.getAttribute("space").equals("nonHeapConsumed")) {
                mav.addObject("name", "PercentageOfNonHeapFree");
                mav.addObject("value", mstats.getNonHeapPercentage());
            } else if (request.getAttribute("space").equals("nonHeapSize")) {
                mav.addObject("name", "NonHeapSize");
                mav.addObject("value", mstats.getNonHeapSize());
            } else if (request.getAttribute("space").equals("heap")) {
                mav.addObject("name", "HeapSize");
                mav.addObject("value", mstats.getHeapSize());
            } else if (request.getAttribute("space").equals("heapUsed")) {
                mav.addObject("name", "HeadUsed");
                mav.addObject("value", mstats.getHeapUsed());
            } else if (request.getAttribute("space").equals("nonHeapUsed")) {
                mav.addObject("name", "NonHeapUsed");
                mav.addObject("value", mstats.getNonHeapUsed());
            } else if (request.getAttribute("space").equals("permSize")) {
                mav.addObject("name", "PermGenSize");
                mav.addObject("value", permspace.getSize());
            } else if (request.getAttribute("space").equals("permUsed")) {
                mav.addObject("name", "PermGenUsed");
                mav.addObject("value", permspace.getUsed());
            } else if (request.getAttribute("space").equals("permConsumed")) {
                mav.addObject("name", "PermGenPerctageUsed");
                mav.addObject("value", permspace.getPercentageUsed());
            } else if (request.getAttribute("space").equals("heapFree")) {
                mav.addObject("name", "HeapFree");
                mav.addObject("value", 100 - mstats.getHeapPercentage());
            } else if (request.getAttribute("space").equals("nonHeapFree")) {
                mav.addObject("name", "NonHeapFree");
                mav.addObject("value", 100 - mstats.getNonHeapPercentage());
            } else if (request.getAttribute("space").equals("permFree")) {
                mav.addObject("name", "PermFree");
                mav.addObject("value", 100 - permspace.getPercentageUsed());
            }

        } else {
            HashMap<String, Long> memory = new HashMap<String, Long>();
            memory.put("heap", mstats.getHeapSize());
            memory.put("nonheap", mstats.getNonHeapSize());
            mav.addObject("memory", memory);
        }

        return mav;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
