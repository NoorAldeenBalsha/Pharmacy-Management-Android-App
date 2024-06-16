package com.myprojects.assignment.features;

import java.io.Serializable;

public class CardData implements Serializable {
    private int imageResource;
    private String label;
    private String description;
    private String id; // Added field for navigation

    public CardData( String label, String description, String id) {
        this.label = label;
        this.description = description;
        this.id = id; // Initialize the new field
    }

    // Getters

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
