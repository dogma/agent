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
    <span id="server-info">Server Info: <%= application.getServerInfo() %></span><br/>
    <span id="server-name">ServerName: <%= request.getServerName() %></span><br/>
    <span id="server">Server: <%= request.getLocalName() + ":" + request.getLocalPort()%></span><br />
    <span id="agent-version">Agent Version: ${version}</span>
</div>
<div id="manage">
    <h3>Manage</h3>
    <a href="/manage/state">Set state</a>
    <a href="/manage/forcegc">Force GC</a>
</div>
</body>
</html>