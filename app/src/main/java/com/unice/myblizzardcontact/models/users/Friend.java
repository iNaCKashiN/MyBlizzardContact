package com.unice.myblizzardcontact.models.users;

import com.unice.myblizzardcontact.MyBlizzardContact;
import com.unice.myblizzardcontact.models.blizzard.BTag;
import com.unice.myblizzardcontact.models.blizzard.BlizzardGames;

import java.sql.SQLOutput;
import java.util.ArrayList;

/**
 * Created by KashiN (ALLENA Johann), DRAVET Jean-BAPTISTE,
 * on 14/05/2016,
 * for My Blizzard Contact.
 * <p/>
 * --
 * <p/>
 * This class represent a friend of a user.
 */
public class Friend extends User {
    //-----------------------------------------------
    //Fields

    // Represent if the friend is favorite.
    private boolean favorite;

    // Represent the comment of the friend.
    private String comment;

    //-----------------------------------------------
    //Constructor

    /**
     * Create a new {@code Friend} instance.
     */
    public Friend(int id) {
        super(id);
        this.photoPath = "/storage/emulated/0/Android/data/com.unice.myblizzardcontact/files/Pictures/friend_" + id + "_photo.jpg";
    }

    /**
     * Create a new {@code Friend} instance.
     * Default value.
     */
    public Friend() {
        super();
        this.favorite = false;
        this.comment = "Default comment Friend";
    }

    //-----------------------------------------------
    //Getters & Setters

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public void setId(int id) {
        super.setId(id);
        this.photoPath = "/storage/emulated/0/Android/data/com.unice.myblizzardcontact/files/Pictures/friend_" + id + "_photo.jpg";
    }

    //-----------------------------------------------
    //Methods

    /**
     * Setup the friend with a String.
     * String format === "phone;name;firstname;Email;Sex;pseudo;Btag;Game1,Game2,game3;favoriteGame;Description"
     *
     * @param value The value for the friend.
     */
    public void setupWith(String value) {
        String temp = value.replace("{@MyBlizzardContact}", "");
        String[] tempString = temp.split(";");
        System.out.println(tempString.length + "-" + temp);
        if (tempString.length == 10) {
            phoneNumber = new PhoneNumber(tempString[0]);
            name = tempString[1];
            firstName = tempString[2];
            email = new Email(tempString[3]);

            if (tempString[4].equals("1")) {
                male = true;
            } else {
                male = false;
            }
            pseudo = tempString[5];
            bTag = new BTag(tempString[6]);

            for (String s : tempString[7].split(",")) {
                if (s.equals(BlizzardGames.DIABLO.getAbbreviation())) {
                    games.add(BlizzardGames.DIABLO);
                } else if (s.equals(BlizzardGames.WOW.getAbbreviation())) {
                    games.add(BlizzardGames.WOW);
                } else if (s.equals(BlizzardGames.HOTS.getAbbreviation())) {
                    games.add(BlizzardGames.HOTS);
                } else if (s.equals(BlizzardGames.OVERWATCH.getAbbreviation())) {
                    games.add(BlizzardGames.OVERWATCH);
                } else if (s.equals(BlizzardGames.HEARTHSTONE.getAbbreviation())) {
                    games.add(BlizzardGames.HEARTHSTONE);
                } else if (s.equals(BlizzardGames.STARCRAFT.getAbbreviation())) {
                    games.add(BlizzardGames.STARCRAFT);
                }
            }

            if (tempString[8].equals(BlizzardGames.DIABLO.getAbbreviation())) {
                favoriteGame = BlizzardGames.DIABLO;
            } else if (tempString[8].equals(BlizzardGames.WOW.getAbbreviation())) {
                favoriteGame = BlizzardGames.WOW;
            } else if (tempString[8].equals(BlizzardGames.HOTS.getAbbreviation())) {
                favoriteGame = BlizzardGames.HOTS;
            } else if (tempString[8].equals(BlizzardGames.OVERWATCH.getAbbreviation())) {
                favoriteGame = BlizzardGames.OVERWATCH;
            } else if (tempString[8].equals(BlizzardGames.HEARTHSTONE.getAbbreviation())) {
                favoriteGame = BlizzardGames.HEARTHSTONE;
            } else if (tempString[8].equals(BlizzardGames.STARCRAFT.getAbbreviation())) {
                favoriteGame = BlizzardGames.STARCRAFT;
            }

            description = tempString[9];
        }
    }

    //-----------------------------------------------
    //To String

    @Override
    public String toString() {
        return "Friend{" +
                "favorite=" + favorite +
                ", comment='" + comment + '\'' +
                '}';
    }

    //-----------------------------------------------
    //HashCode & Equal


}
