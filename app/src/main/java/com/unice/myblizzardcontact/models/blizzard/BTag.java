package com.unice.myblizzardcontact.models.blizzard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by KashiN (ALLENA Johann), DRAVET Jean-BAPTISTE,
 * on 14/05/2016,
 * for My Blizzard Contact.
 * <p/>
 * --
 * <p/>
 * This class represent a blizzard BTag.
 */
public class BTag {
    //-----------------------------------------------
    //Fields

    // Represent the pattern of a BTag.
    private final static Pattern pattern = Pattern.compile("^[A-Za-z]{2,14}#[0-9]{4,6}$");

    // Represent the string of the BTag.
    private String bTag;

    // Represent the pseudo of the BTag.
    private String pseudo;

    // Represent the number of the BTag.
    private String number;

    //-----------------------------------------------
    //Constructor

    /**
     * Create a new {@code BTag} instance.
     *
     * @param bTag The BTag in a string.
     */
    public BTag(String bTag) {
        Matcher matcher = pattern.matcher(bTag);
        if (matcher.find()) {
            String[] tempBTag = bTag.split("#");
            this.bTag = bTag;
            this.pseudo = tempBTag[0];
            this.number = tempBTag[1];
        } else {
            String[] tempBTag = "default#5698".split("#");
            this.bTag = bTag;
            this.pseudo = tempBTag[0];
            this.number = tempBTag[1];
        }
    }

    //-----------------------------------------------
    //Getters & Setters

    public String getBTag() {
        return bTag;
    }

    public void setBTag(String bTag) {
        Matcher matcher = pattern.matcher(bTag);
        if (matcher.find()) {
            String[] tempBTag = bTag.split("#");
            this.bTag = bTag;
            this.pseudo = tempBTag[0];
            this.number = tempBTag[1];
        }
    }

    public static Pattern getPattern() {
        return pattern;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getNumber() {
        return number;
    }

    //-----------------------------------------------
    //Methods

    /**
     * This static method return a boolean in function of the input BTag.
     *
     * @param bTag The BTag that will be check.
     * @return True if the BTag is correct else false.
     */
    public static boolean validateBTag(String bTag) {
        Matcher matcher = pattern.matcher(bTag);
        return matcher.find();
    }

    //-----------------------------------------------
    //To String

    @Override
    public String toString() {
        return "BTag{" +
                "bTag='" + bTag + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", number='" + number + '\'' +
                '}';
    }

    //-----------------------------------------------
    //HashCode & Equal

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BTag bTag1 = (BTag) o;

        return getBTag().equals(bTag1.getBTag());

    }

    @Override
    public int hashCode() {
        return getBTag().hashCode();
    }
}
