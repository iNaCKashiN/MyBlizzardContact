package com.unice.myblizzardcontact.models.users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by KashiN (ALLENA Johann), DRAVET Jean-BAPTISTE,
 * on 14/05/2016,
 * for My Blizzard Contact.
 * <p/>
 * --
 * <p/>
 * This class represent a email.
 */
public class Email {
    //-----------------------------------------------
    //Fields

    // Represent the pattern of a email.
    private final static Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    // Represent the string of the email.
    private String email;

    // Represent the local name of the email.
    private String localName;

    // Represent the domain name of the email.
    private String domainName;

    //-----------------------------------------------
    //Constructor

    /**
     * Create a new {@code Email} instance.
     *
     * @param email The email in a string.
     */
    public Email(String email) {
        Matcher matcher = pattern.matcher(email);
        if (matcher.find()) {
            String[] tempEmail = email.split("@");
            this.email = email;
            this.localName = tempEmail[0];
            this.domainName = tempEmail[1];
        } else {
            String[] tempEmail = "default@gmail.com".split("@");
            this.email = email;
            this.localName = tempEmail[0];
            this.domainName = tempEmail[1];
        }
    }

    //-----------------------------------------------
    //Getters & Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        if (matcher.find()) {
            String[] tempEmail = email.split("@");
            this.email = email;
            this.localName = tempEmail[0];
            this.domainName = tempEmail[1];
        }
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String getLocalName() {
        return localName;
    }

    public String getDomainName() {
        return domainName;
    }

    //-----------------------------------------------
    //Methods

    /**
     * This static method return a boolean in function of the input email.
     *
     * @param email The email that will be check.
     * @return True if the email is correct else false.
     */
    public static boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    //-----------------------------------------------
    //To String

    @Override
    public String toString() {
        return "Email{" +
                ", email='" + email + '\'' +
                ", localName='" + localName + '\'' +
                ", domainName='" + domainName + '\'' +
                '}';
    }

    //-----------------------------------------------
    //HashCode & Equal

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email1 = (Email) o;

        return getEmail().equals(email1.getEmail());

    }

    @Override
    public int hashCode() {
        return getEmail().hashCode();
    }
}
