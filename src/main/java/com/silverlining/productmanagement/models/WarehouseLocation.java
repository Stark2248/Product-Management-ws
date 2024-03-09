package com.silverlining.productmanagement.models;

import java.util.Objects;

public enum WarehouseLocation {
    DELHI("DEL"),
    MUMBAI("MUM"),
    BANGALORE("BLR"),
    CHENNAI("CHE"),
    KOLKATA("KOL"),
    HYDERABAD("HYD"),
    AHMEDABAD("AMD"),
    PUNE("PUN"),
    JAIPUR("JAI"),
    SURAT("SUR");

    private final String abbreviation;

    WarehouseLocation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public static WarehouseLocation findByName(String name) {
        WarehouseLocation result = null;
        for (WarehouseLocation location : values()) {
            if (Objects.equals(name, location.getAbbreviation()) || Objects.equals(name, location.name())) {
                result = location;
                break;
            }
        }
        return result;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
