tee-servlet-filter
==================

A simple HTTP request/response servlet filter

Usage:

Configure the filter in Your web.xml:

  <filter>
		<filter-name>regRspServletFilter</filter-name>
		<filter-class>pl.maciejkizlich.tools.filter.ReqRspServletFilter</filter-class>
	</filter>
	 
	<filter-mapping>
		<filter-name>regRspServletFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
	
