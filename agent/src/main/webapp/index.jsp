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
    Server Info: <%= application.getServerInfo() %><br/>
    ServerName: <%= request.getServerName() %><br/>
    Server: <%= request.getLocalName() + ":" + request.getLocalPort()%>
</div>
<div id="manage">
    <h3>Manage</h3>
    <a href="/manage/state">Set state</a>
    <a href="/manage/forcegc">Force GC</a>
</div>
</body>
</html>