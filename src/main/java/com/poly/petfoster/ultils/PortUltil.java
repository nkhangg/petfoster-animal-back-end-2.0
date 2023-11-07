package com.poly.petfoster.ultils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;


@Component
public class PortUltil {

    
    @Autowired
    private Environment environment;

    private String port;

    /**
     * Get port.
     *
     * @return
     */
    public String getPort() {
        if (port == null) port = environment.getProperty("local.server.port");
        return port;
    }

    public String getServerUrlPrefi() {
        return "http://" + InetAddress.getLoopbackAddress().getHostName() + ":" + getPort();
    }

    public String getUrlImage(String name) {
        return getServerUrlPrefi()  + "/images/" + name;
    }

    
}
