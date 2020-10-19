package com.yashraj.skiller.model;

public class Service_class {
    String service;
    String image;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Service_class() {
    }

    public Service_class(String service, String image) {
        this.service = service;
        this.image = image;
    }
}
