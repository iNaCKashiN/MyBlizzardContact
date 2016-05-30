package com.unice.myblizzardcontact.models.users;

import com.unice.myblizzardcontact.models.blizzard.BTag;
import com.unice.myblizzardcontact.models.blizzard.BlizzardGames;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by KashiN (ALLENA Johann), DRAVET Jean-BAPTISTE,
 * on 14/05/2016,
 * for My Blizzard Contact.
 * <p/>
 * --
 * <p/>
 * This class represent a user of this application.
 */
public class User {
    //-----------------------------------------------
    //Fields

    // Represent the id of the user.
    protected int id;

    // Represent the path to the file path of the photo user.
    protected String photoPath;

    // Represent the name of the user.
    protected String name;

    // Represent the firstName of the user.
    protected String firstName;

    // Represent the pseudo of the user.
    protected String pseudo;

    // Represent the phone Number of the user.
    protected PhoneNumber phoneNumber;

    // Represent the email of the user.
    protected Email email;

    // Represent the sex of the user.
    protected Boolean male;

    // Represent the bTag of the user.
    protected BTag bTag;

    // Represent the description of the user.
    protected String description;

    // Represent the favorite game of the user.
    protected BlizzardGames favoriteGame;

    // Represent all game of the user.
    protected ArrayList<BlizzardGames> games = new ArrayList<>();

    //-----------------------------------------------
    //Constructor

    /**
     * Create a new {@code User} instance.
     * Default value.
     */
    public User() {
        this.id = -1;
        this.photoPath = "/storage/emulated/0/Android/data/com.unice.myblizzardcontact/files/Pictures/user_photo.jpg";
        this.name = "Default";
        this.firstName = "Default";
        this.pseudo = "Default";
        this.phoneNumber = new PhoneNumber("06 00 00 00 00");
        this.email = new Email("Default@gmail.com");
        this.male = true;
        this.bTag = new BTag("Default#0000");
        this.description = "Default Description";
        this.favoriteGame = BlizzardGames.HOTS;
    }

    /**
     * Create a new {@code User} instance.
     * From DataBase.
     */
    public User(int id) {
        this.id = id;
        this.photoPath = "/storage/emulated/0/Android/data/com.unice.myblizzardcontact/files/Pictures/user_photo.jpg";
    }

    //-----------------------------------------------
    //Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Boolean getMale() {
        return male;
    }

    public void setMale(Boolean male) {
        this.male = male;
    }

    public BTag getbTag() {
        return bTag;
    }

    public void setbTag(BTag bTag) {
        this.bTag = bTag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BlizzardGames getFavoriteGame() {
        return favoriteGame;
    }

    public void setFavoriteGame(BlizzardGames favoriteGame) {
        this.favoriteGame = favoriteGame;
    }

    public ArrayList<BlizzardGames> getGames() {
        return games;
    }

    public void setGames(ArrayList<BlizzardGames> games) {
        this.games = games;
    }

    //-----------------------------------------------
    //Methods

    /**
     * Generate a string with the value of the user.
     * String format === "phone;name;firstname;Email;Sex;pseudo;Btag;Game1,Game2,game3;favoriteGame;Description"
     *
     * @return the string of the user representation.
     */
    public String userToString() {
        String result = "{@MyBlizzardContact}";
        result += phoneNumber.getNumber().replace(" ", "") + ";" +
                name + ";" +
                firstName + ";" +
                email.getEmail() + ";";

        if (male) {
            result += "1;";
        } else {
            result += "0;";
        }

        result += pseudo + ";" +
                bTag.getBTag() + ";";

        for (BlizzardGames game : games) {
            result += game.getAbbreviation() + ",";
        }
        result = result.replace(result.substring(result.length() - 1), "");
        result += ";" + favoriteGame.getAbbreviation() + ";" + description;

        return result;
    }

    //-----------------------------------------------
    //To String

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email=" + email +
                ", male=" + male +
                ", bTag=" + bTag +
                ", description='" + description + '\'' +
                ", favoriteGame=" + favoriteGame +
                ", games=" + games +
                '}';
    }

    //-----------------------------------------------
    //HashCode & Equal


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getId() != user.getId()) return false;
        if (!getName().equals(user.getName())) return false;
        if (!getFirstName().equals(user.getFirstName())) return false;
        return getPseudo().equals(user.getPseudo());

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getPseudo().hashCode();
        return result;
    }
}
