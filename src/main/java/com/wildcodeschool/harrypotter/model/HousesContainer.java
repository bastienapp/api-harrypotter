package com.wildcodeschool.harrypotter.model;

import java.util.List;

public class HousesContainer {

    private List<HPHouseCharacter> members;

    public HousesContainer() {
    }

    public List<HPHouseCharacter> getMembers() {
        return members;
    }

    public void setMembers(List<HPHouseCharacter> members) {
        this.members = members;
    }
}
