<html>
<head><title>Agent</title></head>
<body>
<div id="menu">
    <h3>Menu</h3>
    <a href="./available">Available</a>
    <a href="./context">Running contexts</a>
    <a href="./memory">Memory</a>
    <a href="./help/">Help</a>

</div>
<div id="info">
    <h3>Server Info:</h3>
    <div id="server-info"><label class="label">Server Info:</label> <%= application.getServerInfo() %></div>
    <div id="server-name"><label>ServerName:</label> <%= request.getServerName() %></div>
    <div id="local-name"><label>Server:</label> <%= request.getLocalName() + ":" + request.getLocalPort()%></div>
    <div id="agent-version"><label>Agent Version:</label>${version}</div>
</div>
<div id="manage">
    <h3>Manage</h3>
    <a href="/manage/state">Set state</a>
    <a href="/manage/forcegc">Force GC</a>
</div>
</body>
</html>