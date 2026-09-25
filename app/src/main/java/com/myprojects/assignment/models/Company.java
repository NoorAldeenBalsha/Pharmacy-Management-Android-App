package com.myprojects.assignment.models;

import java.util.List;

public class Company {
    private String id;
    private String name;
    private String description;
    private List<Cabinet> cabinets;

    // Constructors, getters, and setters
    public Company(String id, String name, String description, List<Cabinet> cabinets) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cabinets = cabinets;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Cabinet> getCabinets() {
        return cabinets;
    }

    public void setCabinets(List<Cabinet> cabinets) {
        this.cabinets = cabinets;
    }
}
