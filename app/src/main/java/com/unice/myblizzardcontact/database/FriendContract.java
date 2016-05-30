package com.unice.myblizzardcontact.database;

import android.provider.BaseColumns;

/**
 * Created by KashiN (ALLENA Johann), DRAVET Jean-BAPTISTE,
 * on 14/05/2016,
 * for My Blizzard Contact.
 * <p/>
 * --
 * <p/>
 * This class represent a friend contact for the table Friend in the database.
 */
public class FriendContract {

    //-----------------------------------------------
    //Constructor
    private FriendContract() {
    }

    /**
     * inner class that defines the tables contents
     */
    public static abstract class FriendEntry implements BaseColumns {

        //-----------------------------------------------
        //Column and table name

        public static final String TABLE_NAME = "Friend";
        public static final String COLUMN_NAME_USER_ID = "User_id";
        public static final String COLUMN_NAME_FAVORITE = "Favorite";
        public static final String COLUMN_NAME_COMMENT = "Comment";

        //-----------------------------------------------
        //Request create and delete table

        private static final String TEXT_TYPE = " TEXT";
        private static final String NUMERIC_TYPE = " NUMERIC";
        private static final String COMMA_SEP = ",";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + FriendEntry.TABLE_NAME + " (" +
                        FriendEntry.COLUMN_NAME_USER_ID + " INTEGER PRIMARY KEY, " +
                        FriendEntry.COLUMN_NAME_FAVORITE + NUMERIC_TYPE + COMMA_SEP +
                        FriendEntry.COLUMN_NAME_COMMENT + TEXT_TYPE + " )";
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + FriendEntry.TABLE_NAME;
    }
}
