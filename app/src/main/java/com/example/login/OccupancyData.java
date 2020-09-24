package com.example.login;

public class OccupancyData extends counterPage{
    public String businessName;
    public String email;
    public String notes;
    public String open_close;
    public String occupancyLevel;

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setOpen_close(String open_close) {
        this.open_close = open_close;
    }

    public void setOccupancyLevel(String occupancyLevel) {
        this.occupancyLevel = occupancyLevel;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getEmail() {
        return email;
    }

    public String getNotes() {
        return notes;
    }

    public String getOpen_close() {
        return open_close;
    }

    public String getOccupancyLevel() {
        return occupancyLevel;
    }

    public OccupancyData(String businessName, String email, String notes, String open_close, String occupancyLevel) {
        this.businessName = businessName;
        this.email = email;
        this.notes = notes;
        this.open_close = open_close;
        this.occupancyLevel = occupancyLevel;
    }
}
