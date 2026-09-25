package com.myprojects.assignment.models;

import java.util.List;

public class Cabinet {
    private String id;
    private String name;
    private String description;
    private List<Shelf> shelves;

    // Constructors, getters, and setters
    public Cabinet(String id, String name, String description, List<Shelf> shelves) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.shelves = shelves;
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

    public List<Shelf> getShelves() {
        return shelves;
    }

    public void setShelves(List<Shelf> shelves) {
        this.shelves = shelves;
    }
}
