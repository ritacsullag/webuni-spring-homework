package com.csullagrita.centralsystem.config;


import com.csullagrita.centralsystem.xmlws.SchoolXmlWs;
import jakarta.xml.ws.Endpoint;
import lombok.RequiredArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WebServiceConfig {

    private final Bus bus;
    private final SchoolXmlWs schoolXmlWs;

    @Bean
    public Endpoint endpoint() {
        Endpoint endpoint = new EndpointImpl(bus, schoolXmlWs);
        //this will be the url of the web service (http://localhost:8081/services/students?wsdl)
        endpoint.publish("/students");

        return endpoint;
    }
}
