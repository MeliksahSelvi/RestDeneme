package com.meliksah.restdeneme.server.enums;

public enum Title {

    JUNIOR_DEVELOPER("Junior Developer"),
    TEAM_LEAD("Team Lead"),
    CLEANER("Cleaner");

    private final String professionName;

    Title(String professionName) {
        this.professionName = professionName;
    }

    @Override
    public String toString() {
        return professionName;
    }
}
