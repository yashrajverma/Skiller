package com.yashraj.skiller.model;

public class OrderHistoryModel {
    private String charges;
    private String description;
    private String endingDate;
    private String location;
    private String vendorName;
    private String startingDate;

    public OrderHistoryModel(String charges, String description, String endingDate, String location, String vendorName, String startingDate) {
        this.charges = charges;
        this.description = description;
        this.endingDate = endingDate;
        this.location = location;
        this.vendorName = vendorName;
        this.startingDate = startingDate;
    }

    public OrderHistoryModel() {

    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }
}
