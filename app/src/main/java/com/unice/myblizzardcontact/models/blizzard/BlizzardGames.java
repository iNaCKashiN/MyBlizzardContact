package com.unice.myblizzardcontact.models.blizzard;

/**
 * Created by KashiN (ALLENA Johann), DRAVET Jean-BAPTISTE,
 * on 14/05/2016,
 * for My Blizzard Contact.
 * <p/>
 * --
 * <p/>
 * This is a enum file who represent all blizzard games.
 */
public enum BlizzardGames {
    //-----------------------------------------------
    //Fields

    WOW("World Of Warcraft", "Wow"),
    HOTS("Heroes of the storm", "Hots"),
    DIABLO("Diablo", "Diablo"),
    STARCRAFT("Star Craft", "Starcraft"),
    HEARTHSTONE("Hearthstone", "Hearthstone"),
    OVERWATCH("Overwatch", "Overwatch");

    // Represent the name of the blizzard game.
    private String name = "";

    // Represent the abbreviation of the blizzard game.
    private String abbreviation = "";

    //-----------------------------------------------
    //Constructor

    /**
     * Create a new {@code BlizzardGames} enum.
     *
     * @param name         The name of the blizzard game.
     * @param abbreviation The abbreviation of the blizzard game.
     */
    BlizzardGames(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    //-----------------------------------------------
    //Getters & Setters

    /**
     * Get the name of the blizzard game.
     *
     * @return Return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the abbreviation of the blizzard game.
     *
     * @return Return the abbreviation.
     */
    public String getAbbreviation() {
        return abbreviation;
    }

}
