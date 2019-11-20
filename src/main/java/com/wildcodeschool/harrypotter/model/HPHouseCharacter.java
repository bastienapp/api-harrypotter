package com.wildcodeschool.harrypotter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HPHouseCharacter {

    @JsonProperty("_id")
    private String id;

    private String name;

    public HPHouseCharacter() {
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
}
