tee-servlet-filter
==================

A simple HTTP request/response servlet filter

Usage:

Configure the filter in Your web.xml:

    <filter>
        <filter-name>regRspServletFilter</filter-name>
        <filter-class>pl.maciejkizlich.tools.filter.ReqRspServletFilter</filter-class>
    </filter>
    
And then register logger:

    <logger name="pl.maciejkizlich.tools.filter.ReqRspServletFilter">
        <appender-ref ref="FILE" />
    </logger>
	
