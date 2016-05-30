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
 * This class represent a user phone number.
 */
public class PhoneNumber {
    //-----------------------------------------------
    //Fields

    // Represent the pattern of a phone number.
    private final static Pattern pattern = Pattern.compile("^((\\+|00)33\\s?|0)[1-5](\\s?\\d{2}){4}$");

    // Represent the pattern of a mobile phone number.
    private final static Pattern patternMobile = Pattern.compile("^((\\+|00)[33\\s]?|0)[679](\\s?\\d{2}){4}$");

    // Represent the number of the phone number.
    private String number;

    // Represent if the number is a phone mobile or not with a boolean.
    private Boolean mobilePhone;

    //-----------------------------------------------
    //Constructor

    /**
     * Create a new {@code PhoneNumber} instance.
     *
     * @param number The phone number in a string.
     */
    public PhoneNumber(String number) {
        Matcher matcher = pattern.matcher(number);
        Matcher matcherMobile = patternMobile.matcher(number);
        if (matcher.find()) {
            this.number = number;
            this.mobilePhone = false;
        } else if (matcherMobile.find()) {
            this.number = number;
            this.mobilePhone = true;
        } else {
            this.number = "04 00 00 00 00";
            this.mobilePhone = false;
        }
    }

    //-----------------------------------------------
    //Getters & Setters

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        Matcher matcher = pattern.matcher(number);
        Matcher matcherMobile = patternMobile.matcher(number);
        if (matcher.find()) {
            this.number = number;
            this.mobilePhone = false;
        } else if (matcherMobile.find()) {
            this.number = number;
            this.mobilePhone = true;
        }
    }

    public Boolean isMobilePhone() {
        return mobilePhone;
    }

    //-----------------------------------------------
    //Methods

    /**
     * This static method return a boolean in function of the input phone number.
     *
     * @param number The phone number that will be check.
     * @return True if the phone number is correct else false.
     */
    public static boolean validatePhoneNumber(String number) {
        Matcher matcher = pattern.matcher(number);
        Matcher matcherMobile = patternMobile.matcher(number);
        return matcher.find() | matcherMobile.find();
    }

    //-----------------------------------------------
    //To String

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "number='" + number + '\'' +
                ", mobilePhone=" + mobilePhone +
                '}';
    }


}
