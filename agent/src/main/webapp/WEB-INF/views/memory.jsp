<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${not empty spaces}">
        <h3>JVM Specific Memory Spaces</h3>
        <%
            String[] spaces = (String[]) request.getAttribute("spaces");

            for (String s : spaces) {
        %>
        <%=s%>:
        <a href="<c:url value="/memory/free" />?space=<%=s%>">Free</a>
        <a href="<c:url value="/memory/max" />?space=<%=s%>">Max</a>
        <a href="<c:url value="/memory/used"/>?space=<%=s%>">Used</a>
        <br/>
        <%
            }
        %>
        <p>
            <h3>JVM Overall Memory Usage</h3>
        <a href="<c:url value="/memory/heapfree"/>">Heap Free</a>
        <a href="<c:url value="/memory/nonheapfree"/>">Non-Heap Free</a>
        </p>
    </c:when>
    <c:when test="${not empty space && metric == 'free'}">
        <%
            Long[] metrics = (Long[]) request.getAttribute("space");
            out.print(100L-metrics[4]);
        %>
    </c:when>
    <c:when test="${not empty space && metric == 'max'}">
        <%
            Long[] metrics = (Long[]) request.getAttribute("space");
            out.print(metrics[2]);
        %>
    </c:when>
    <c:when test="${not empty space && metric == 'used'}">
        <%
            Long[] metrics = (Long[]) request.getAttribute("space");
            out.print(metrics[3]);
        %>
    </c:when>
    <c:when test="${not empty metric && metric == 'nonheapfree'}">
        ${free}
    </c:when>
    <c:when test="${not empty metric && metric == 'heapfree'}">
        ${free}
    </c:when>
    <c:when test="${not empty metric && metric == 'unknown'}">
        The requested metric is unknown
    </c:when>
</c:choose>