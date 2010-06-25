<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Configure Agent State</h2>

<c:if test="${not empty newState}">
<p>
    You have requested to set the state of the Agent to '<strong>${newState}</strong>'.
</p>
</c:if>

<p>
The current state of the Agent is: '<strong>${currentState}</strong>'
</p>

<c:if test="${currentState eq 'enabled'}">
    | <a href="${agentStateUrl}/disable">Disable</a>
</c:if>
<c:if test="${currentState eq 'disabled'}">
    | <a href="${agentStateUrl}/enable">Enable</a>
</c:if>
|
<a href="${agentStateUrl}">Report State</a>
|
<a href="${agentIndexUrl}">Agent Index</a>
|