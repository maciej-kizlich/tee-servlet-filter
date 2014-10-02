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
	
Example of logged message:


    [14:43:33.879] 
    [sessionId = 26E1E80A6FEB26F7F8A15D145EE430DA] [HTTP Request] 
    GET /backend/groups/1 HTTP/1.1
    host: localhost:8080
    user-agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:32.0) Gecko/20100101 Firefox/32.0
    accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
    accept-language: null
    accept-encoding: gzip, deflate
    content-type: application/json
    cookie: JSESSIONID=26E1E80A6FEB26F7F8A15D145EE430DA; __utma=111872281.469739585.1407158720.1407158720.1407158720.1;     __utmz=111872281.1407158720.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none);          JSESSIONID.ae922692=10g6j98szu8qb4l6lxaemuxn7; screenResolution=1920x1080; 
    JSESSIONID.5307d452=9hselo24kxhatnum40a1rc9f;     JSESSIONID.47ab811b=plzo4u4sgdgf13w2mgw6qp0pr; JSESSIONID.232d130d=1xn18ewxkfc8zbpftu8v9ln1x
    connection: keep-alive
    Content: ---

    [HTTP Response] 
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Thu, 02 Oct 2014 12:43:33 GMT
    Response status: 200 OK
    Response body: 
    {
      "groupId": 1,
      "description": "testowa grupa",
      "createDate": 1412253800954,
      "links": [
        {
          "rel": "self",
          "href": "http://localhost:8080/backend/groups/1"
        },
        {
          "rel": "edit",
          "href": "http://localhost:8080/backend/groups/1"
        }
      ]
    }
