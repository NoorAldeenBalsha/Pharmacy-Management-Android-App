package com.myprojects.assignment.models;

import java.util.List;

public class Shelf {
    private String id;
    private String name;
    private String description;
    private List<Medicine> medicines;

    // Constructors, getters, and setters
    public Shelf(String id, String name, String description, List<Medicine> medicines) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.medicines = medicines;
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

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }
}
