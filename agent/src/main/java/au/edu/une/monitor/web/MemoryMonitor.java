package au.edu.une.monitor.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.une.monitor.jvm.MemoryStats;
import au.edu.une.monitor.jvm.NamedMemorySpaceStats;

import java.util.HashMap;
import java.util.ArrayList;

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

    public MemoryMonitor() {
        permspace = new NamedMemorySpaceStats("java.lang:type=MemoryPool,name=CMS Perm Gen");
    }

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView(view);

        if (request.getAttribute("space") != null && request.getParameter("space") != null) {
            mav.addObject("metric",request.getAttribute("space"));
            mav.addObject("space", mstats.getMemorySpace(request.getParameter("space")));
        } else if (request.getAttribute("space") != null) {
            //At this stage we are most likely looking for either heap free or non heap free
            if(request.getAttribute("space").equals("nonheapfree")) {
                mav.addObject("metric","nonheapfree");
                mav.addObject("free",100-mstats.getNonHeapPercentage());
            } else if(request.getAttribute("space").equals("heapfree")) {
                mav.addObject("metric","heapfree");
                mav.addObject("free",100-mstats.getHeapPercentage());
            } else {
                mav.addObject("metric","unknown");
            }
        } else {
            String[] spaces = mstats.getMemorySpaces();
            mav.addObject("spaces", spaces);

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
