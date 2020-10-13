package com.yashraj.skiller.model;

public class Service_class {
    String service;
    int image;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Service_class() {
    }

    public Service_class(String service, int image) {
        this.service = service;
        this.image = image;
    }
}
