package com.api.day2;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private String name;
    private String location;

    @JsonProperty("Phone")   // ✅ force the JSON key to be "Phone"
    private String phone;

    private String[] courses;

    public User(String name, String location, String phone, String[] courses) {
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.courses = courses;
    }

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String[] getCourses() { return courses; }
    public void setCourses(String[] courses) { this.courses = courses; }
}