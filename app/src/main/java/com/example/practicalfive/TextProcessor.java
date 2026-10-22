package com.example.practicalfive;

public class TextProcessor {

    public String processMunicipality(String municipality) {
        if (municipality == null) {
            return null;
        }

        String cleanedMunicipality = municipality.trim();

        if (cleanedMunicipality.isEmpty()) {
            return null;
        }

        if (cleanedMunicipality.equalsIgnoreCase("Beijing")) {
            return "Beijing";
        } else if (cleanedMunicipality.equalsIgnoreCase("Shanghai")) {
            return "Shanghai";
        } else if (cleanedMunicipality.equalsIgnoreCase("Tianjin")) {
            return "Tianjin";
        } else if (cleanedMunicipality.equalsIgnoreCase("Chongqing")) {
            return "Chongqing";
        }

        return null;
    }
}