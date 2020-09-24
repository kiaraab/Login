package com.example.login;

public class OccupancyData {
    public String businessName;
    public String email;
    public String notes;
    public String open_close;
    public String occupancy_level;

    public OccupancyData(String businessName, String email, String notes, String open_close, String occupancy_level) {
        this.businessName = businessName;
        this.email = email;
        this.notes = notes;
        this.open_close = open_close;
        this.occupancy_level = occupancy_level;
    }
}
