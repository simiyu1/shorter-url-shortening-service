package com.shorterurl.shorterurl.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Click {
    @Id
    private String id;
    private String urlMappingId;
    private String ipAddress;
    private String location;

    public Click() {
    }

    public Click(String urlMappingId, String ipAddress, String location) {
        this.urlMappingId = urlMappingId;
        this.ipAddress = ipAddress;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlMappingId() {
        return urlMappingId;
    }

    public void setUrlMappingId(String urlMappingId) {
        this.urlMappingId = urlMappingId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
