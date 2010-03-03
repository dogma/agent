<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${not empty modules}">
        <h3>Deployed Applications (contexts)</h3>
        <%
            String[] modules = (String[]) request.getAttribute("modules");

            for (String s : modules) {
        %>
        <span style="font-weight: bold;"><%=s%></span>:
        <a href="<c:url value="/context/state" />?context=<%=s%>">Available</a>
        <a href="<c:url value="/context/servlets" />?context=<%=s%>">Servlets</a>
        <a href="<c:url value="/context/processingTime" />?context=<%=s%>">Processing Time (ms)</a>
        <a href="<c:url value="/context/processingTimeSincePoll" />?context=<%=s%>">Iterim Processing Time (ms)</a>
        <br/>
        <%
            }
        %>
    </c:when>
    <c:when test="${not empty state}">
        <c:choose>
            <c:when test="${state == true}">
                OK
            </c:when>
            <c:when test="${state == false}">
                CRITICAL
            </c:when>
            <c:otherwise>ERROR</c:otherwise>
        </c:choose>
    </c:when>
    <c:when test="${not empty processingTime}">${processingTime}</c:when>
    <c:when test="${not empty interimProcessing}">${interimProcessing}</c:when>
    <c:when test="${not empty servlets}">
        <%
            String[] modules = (String[]) request.getAttribute("servlets");

            for (String s : modules) {
        %>
        <%=s%> : 
        <a href="<c:url value="/servlet/minTime"/>?context=${context}&servlet=<%=s%>">Min</a>
        <a href="<c:url value="/servlet/maxTime"/>?context=${context}&servlet=<%=s%>">Max</a>
        <a href="<c:url value="/servlet/avg"/>?context=${context}&servlet=<%=s%>">Avg</a>
        <a href="<c:url value="/servlet/errors"/>?context=${context}&servlet=<%=s%>">Errors</a>
        <a href="<c:url value="/servlet/processingTime"/>?context=${context}&servlet=<%=s%>">Processing Time</a>
        <a href="<c:url value="/servlet/requests"/>?context=${context}&servlet=<%=s%>">Requests</a>
        
        <a href="<c:url value="/servlet/avgSincePoll"/>?context=${context}&servlet=<%=s%>">Average Since Last Poll</a>
        <a href="<c:url value="/servlet/requestsSincePoll"/>?context=${context}&servlet=<%=s%>">Requests Since Last Poll</a>
        <a href="<c:url value="/servlet/processingSincePoll"/>?context=${context}&servlet=<%=s%>">Processing Time Since Last Poll</a>
        <br/>
        <%
            }
        %>
    </c:when>
</c:choose>

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
            out.print("CRITICAL");
        }
    }
%>