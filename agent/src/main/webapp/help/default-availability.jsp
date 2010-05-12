<html>
<head>
    <title>Configuration - Availability - Agent</title>
    <link href="./css/default.css" type="text/css" rel="stylesheet" />
</head>
<body>
<h1>Agent Help</h1>
<div>
<a href="index.jsp">Index</a>
</div>
<h2>Configuring overall availability</h2>
<p>
    The availability link provides a single availabilty result across the entire server instance. This is
    a check configured to look at 0 or more contexts and return 'CRITICAL' if 1 or more have failed.
</p>
<p>
    You may not want every context in the instance to be checked. As a result you can configure the
    agent to check specific contexts. To do this you need to modify the <code>WEB-INF/conf/availability.xml</code> file.
</p>
<h5>availabilityController configuration.</h5>
<code>
    &lt;bean id="availabilityController" class="au.edu.une.monitor.web.AvailabilityMonitor"&gt;
        &lt;property name="view" value="status"/&gt;
        &lt;property name="monitors"&gt;
            &lt;map&gt;
                &lt;entry key="portal" value-ref="manager" /&gt;
            &lt;/map&gt;
        &lt;/property&gt;
    &lt;/bean&gt;
</code>

<code>
    <bean id="manager" class="au.edu.une.monitor.container.tomcat55.TomcatContextState">
        <constructor-arg index="0"
                         value="Catalina:j2eeType=WebModule,name=//localhost/manager,J2EEApplication=none,J2EEServer=none"/>
        <constructor-arg index="1" value="manager"/>
    </bean>

</code>
</body>
</html>