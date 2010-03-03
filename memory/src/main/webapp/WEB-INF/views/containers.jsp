<%@ page import="java.util.HashMap" %>
<%

    if (request.getAttribute("states") != null) {
        HashMap<String, Boolean> domains = (HashMap<String, Boolean>) request.getAttribute("states");

        if (domains != null) {
            for (String cont : domains.keySet()) {
                out.print(cont + ": " + domains.get(cont) + "<br />");
            }
        }
    } else if (request.getAttribute("contextState") != null) {
        Boolean state = (Boolean) request.getAttribute("contextState");
        if(state) {
            out.print("OK");
        } else {
            out.print("ERROR");
        }
    }
%>