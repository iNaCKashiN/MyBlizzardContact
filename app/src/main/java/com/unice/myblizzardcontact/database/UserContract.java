package com.unice.myblizzardcontact.database;

import android.provider.BaseColumns;

/**
 * Created by KashiN (ALLENA Johann), DRAVET Jean-BAPTISTE,
 * on 14/05/2016,
 * for My Blizzard Contact.
 * <p/>
 * --
 * <p/>
 * This class represent a User Contact for the table "User" in the database.
 */
public class UserContract {

    //-----------------------------------------------
    //Constructor
    private UserContract() {
    }

    /**
     * inner class that defines the tables contents
     **/
    public static abstract class UserEntry implements BaseColumns {

        //-----------------------------------------------
        //Column and table name

        public static final String TABLE_NAME = "User";
        public static final String COLUMN_NAME_TYPE = "Type";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_FIRSTNAME = "FirstName";
        public static final String COLUMN_NAME_PSEUDO = "Pseudo";
        public static final String COLUMN_NAME_PHONENUMBER = "phoneNumber";
        public static final String COLUMN_NAME_EMAIL = "Email";
        public static final String COLUMN_NAME_MALE = "Male";
        public static final String COLUMN_NAME_BTAG = "bTag";
        public static final String COLUMN_NAME_DESCRIPTION = "Description";
        public static final String COLUMN_NAME_FAVORITEGAME = "FavoriteGame";
        public static final String COLUMN_NAME_GAMES = "Games";

        //-----------------------------------------------
        //Request create and delete table

        private static final String TEXT_TYPE = " TEXT";
        private static final String NUMERIC_TYPE = " NUMERIC";
        private static final String COMMA_SEP = ",";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                        UserEntry._ID + " INTEGER PRIMARY KEY, " +
                        UserEntry.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                        UserEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                        UserEntry.COLUMN_NAME_FIRSTNAME + TEXT_TYPE + COMMA_SEP +
                        UserEntry.COLUMN_NAME_PSEUDO + TEXT_TYPE + COMMA_SEP +
                        UserEntry.COLUMN_NAME_PHONENUMBER + TEXT_TYPE + COMMA_SEP +
                        UserEntry.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
                        UserEntry.COLUMN_NAME_MALE + NUMERIC_TYPE + COMMA_SEP +
                        UserEntry.COLUMN_NAME_BTAG + TEXT_TYPE + COMMA_SEP +
                        UserEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                        UserEntry.COLUMN_NAME_FAVORITEGAME + TEXT_TYPE + COMMA_SEP +
                        UserEntry.COLUMN_NAME_GAMES + TEXT_TYPE + " )";
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;
    }
}
