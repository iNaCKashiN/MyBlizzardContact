package com.unice.myblizzardcontact.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.unice.myblizzardcontact.models.blizzard.BTag;
import com.unice.myblizzardcontact.models.blizzard.BlizzardGames;
import com.unice.myblizzardcontact.models.users.Email;
import com.unice.myblizzardcontact.models.users.Friend;
import com.unice.myblizzardcontact.models.users.PhoneNumber;
import com.unice.myblizzardcontact.models.users.User;

import java.util.ArrayList;

/**
 * Created by KashiN (ALLENA Johann), DRAVET Jean-BAPTISTE,
 * on 14/05/2016,
 * for My Blizzard Contact.
 * <p/>
 * --
 * <p/>
 * This class represent the data base of this application.
 */
public class MyBlizzardContactDbHelper extends SQLiteOpenHelper {
    //-----------------------------------------------
    //Fields

    //Represent the database version
    public static final int DATABASE_VERSION = 1;

    //Represent the name of the database
    public static final String DATABASE_NAME = "MyBlizzardContact.db";

    //-----------------------------------------------
    //Constructor

    /**
     * Create a new database helper
     *
     * @param context context of the application
     */
    public MyBlizzardContactDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Create the table User and Friend
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create the table User
        db.execSQL(UserContract.UserEntry.SQL_CREATE_ENTRIES);
        //Create the table Friend
        db.execSQL(FriendContract.FriendEntry.SQL_CREATE_ENTRIES);
    }

    //-----------------------------------------------
    //Methods

    /**
     * Upgrade the version of the database
     *
     * @param db         database
     * @param oldVersion number
     * @param newVersion number
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserContract.UserEntry.SQL_DELETE_ENTRIES);
        db.execSQL(FriendContract.FriendEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * Downgrade the version of the database
     *
     * @param db         database
     * @param oldVersion number
     * @param newVersion number
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    /**
     * Function to know if a User exist in database.
     *
     * @return true if the user exist;
     */
    public boolean userExist() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {UserContract.UserEntry._ID};
        //The WHERE clause
        String selection = "Type=?";
        //Arguments for the WHERE clause
        String[] selectionArgs = {"master"};
        Cursor res = db.query(UserContract.UserEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        if (!res.moveToFirst()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Function to get all information on the user
     * if there is no user in database, create a defaultUser and save it
     * else , get the User master
     *
     * @return User
     */
    public User getUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        // information that you want
        String[] projection = {
                UserContract.UserEntry._ID,
                UserContract.UserEntry.COLUMN_NAME_NAME,
                UserContract.UserEntry.COLUMN_NAME_FIRSTNAME,
                UserContract.UserEntry.COLUMN_NAME_PSEUDO,
                UserContract.UserEntry.COLUMN_NAME_PHONENUMBER,
                UserContract.UserEntry.COLUMN_NAME_EMAIL,
                UserContract.UserEntry.COLUMN_NAME_MALE,
                UserContract.UserEntry.COLUMN_NAME_BTAG,
                UserContract.UserEntry.COLUMN_NAME_DESCRIPTION,
                UserContract.UserEntry.COLUMN_NAME_FAVORITEGAME,
                UserContract.UserEntry.COLUMN_NAME_GAMES
        };
        //The WHERE clause
        String selection = "Type=?";
        //Arguments for the WHERE clause
        String[] selectionArgs = {"master"};
        Cursor res = db.query(UserContract.UserEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        // if there is no information in the database, create a default user
        if (!res.moveToFirst()) {
            return null;
        }
        // else, creation of the User
        User user = new User(res.getInt(res.getColumnIndex(UserContract.UserEntry._ID)));
        //----------------------------------------------------------------------------
        //Set all information from data
        user.setName(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_NAME)));
        user.setFirstName(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_FIRSTNAME)));
        user.setPseudo(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_PSEUDO)));
        user.setPhoneNumber(new PhoneNumber(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_PHONENUMBER))));
        user.setEmail(new Email(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_EMAIL))));
        if (res.getInt(res.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_MALE)) == 1) {
            user.setMale(true);
        } else {
            user.setMale(false);
        }
        user.setbTag(new BTag(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_BTAG))));
        user.setDescription(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_DESCRIPTION)));
        String[] temp = res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_GAMES)).split(",");
        for (String s : temp) {
            if (s.equals(BlizzardGames.DIABLO.getAbbreviation())) {
                user.getGames().add(BlizzardGames.DIABLO);
            } else if (s.equals(BlizzardGames.HEARTHSTONE.getAbbreviation())) {
                user.getGames().add(BlizzardGames.HEARTHSTONE);
            } else if (s.equals(BlizzardGames.HOTS.getAbbreviation())) {
                user.getGames().add(BlizzardGames.HOTS);
            } else if (s.equals(BlizzardGames.OVERWATCH.getAbbreviation())) {
                user.getGames().add(BlizzardGames.OVERWATCH);
            } else if (s.equals(BlizzardGames.WOW.getAbbreviation())) {
                user.getGames().add(BlizzardGames.WOW);
            } else {
                user.getGames().add(BlizzardGames.STARCRAFT);
            }
        }
        String s = res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_FAVORITEGAME));
        if (s.equals(BlizzardGames.DIABLO.getAbbreviation())) {
            user.setFavoriteGame(BlizzardGames.DIABLO);
        } else if (s.equals(BlizzardGames.HEARTHSTONE.getAbbreviation())) {
            user.setFavoriteGame(BlizzardGames.HEARTHSTONE);
        } else if (s.equals(BlizzardGames.HOTS.getAbbreviation())) {
            user.setFavoriteGame(BlizzardGames.HOTS);
        } else if (s.equals(BlizzardGames.OVERWATCH.getAbbreviation())) {
            user.setFavoriteGame(BlizzardGames.OVERWATCH);
        } else if (s.equals(BlizzardGames.WOW.getAbbreviation())) {
            user.setFavoriteGame(BlizzardGames.WOW);
        } else {
            user.setFavoriteGame(BlizzardGames.STARCRAFT);
        }
        //----------------------------------------------------------------------------
        return user;
    }

    /**
     * Get all friends of User
     *
     * @return Array of Friend
     */
    public ArrayList<Friend> getFriends() {
        SQLiteDatabase db = this.getReadableDatabase();
        //Projection for the User table
        String[] projectionUser = {
                UserContract.UserEntry._ID,
                UserContract.UserEntry.COLUMN_NAME_NAME,
                UserContract.UserEntry.COLUMN_NAME_FIRSTNAME,
                UserContract.UserEntry.COLUMN_NAME_PSEUDO,
                UserContract.UserEntry.COLUMN_NAME_PHONENUMBER,
                UserContract.UserEntry.COLUMN_NAME_EMAIL,
                UserContract.UserEntry.COLUMN_NAME_MALE,
                UserContract.UserEntry.COLUMN_NAME_BTAG,
                UserContract.UserEntry.COLUMN_NAME_DESCRIPTION,
                UserContract.UserEntry.COLUMN_NAME_FAVORITEGAME,
                UserContract.UserEntry.COLUMN_NAME_GAMES
        };
        //Projection for the Friend table
        String[] projectionFriend = {
                FriendContract.FriendEntry.COLUMN_NAME_FAVORITE,
                FriendContract.FriendEntry.COLUMN_NAME_COMMENT
        };
        //The WHERE clause for User Table
        String selectionUser = "Type = ?";
        String[] selectionAgrsUser = {"none"};
        //The WHERE clause for Friend Table
        String selectionFriend = "User_ID = ?";
        Cursor res = db.query(UserContract.UserEntry.TABLE_NAME, projectionUser, selectionUser, selectionAgrsUser, null, null, null);
        //if there is no information in the database, create a default friend and add it to the list of friends
        if (!res.moveToFirst()) {
            ArrayList<Friend> defaultfriends = new ArrayList<>();
            return defaultfriends;
        }
        ArrayList<Friend> friends = new ArrayList<>();
        //----------------------------------------------------------------------------
        //get the information for each friend from the table User
        if (res.moveToFirst()) {
            do {
                Friend friend = new Friend(res.getInt(res.getColumnIndex(UserContract.UserEntry._ID)));
                friend.setName(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_NAME)));
                friend.setFirstName(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_FIRSTNAME)));
                friend.setPseudo(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_PSEUDO)));
                friend.setPhoneNumber(new PhoneNumber(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_PHONENUMBER))));
                friend.setEmail(new Email(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_EMAIL))));
                if (res.getInt(res.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_MALE)) == 1) {
                    friend.setMale(true);
                } else {
                    friend.setMale(false);
                }
                friend.setbTag(new BTag(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_BTAG))));
                friend.setDescription(res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_DESCRIPTION)));
                String[] temp = res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_GAMES)).split(",");
                for (String s : temp) {
                    if (s.equals(BlizzardGames.DIABLO.getAbbreviation())) {
                        friend.getGames().add(BlizzardGames.DIABLO);
                    } else if (s.equals(BlizzardGames.HEARTHSTONE.getAbbreviation())) {
                        friend.getGames().add(BlizzardGames.HEARTHSTONE);
                    } else if (s.equals(BlizzardGames.HOTS.getAbbreviation())) {
                        friend.getGames().add(BlizzardGames.HOTS);
                    } else if (s.equals(BlizzardGames.OVERWATCH.getAbbreviation())) {
                        friend.getGames().add(BlizzardGames.OVERWATCH);
                    } else if (s.equals(BlizzardGames.WOW.getAbbreviation())) {
                        friend.getGames().add(BlizzardGames.WOW);
                    } else if (s.equals(BlizzardGames.STARCRAFT.getAbbreviation())) {
                        friend.getGames().add(BlizzardGames.STARCRAFT);
                    }
                }
                //Set the favorite game of the user
                String s = res.getString(res.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_FAVORITEGAME));
                if (s.equals(BlizzardGames.DIABLO.getAbbreviation())) {
                    friend.setFavoriteGame(BlizzardGames.DIABLO);
                } else if (s.equals(BlizzardGames.HEARTHSTONE.getAbbreviation())) {
                    friend.setFavoriteGame(BlizzardGames.HEARTHSTONE);
                } else if (s.equals(BlizzardGames.HOTS.getAbbreviation())) {
                    friend.setFavoriteGame(BlizzardGames.HOTS);
                } else if (s.equals(BlizzardGames.OVERWATCH.getAbbreviation())) {
                    friend.setFavoriteGame(BlizzardGames.OVERWATCH);
                } else if (s.equals(BlizzardGames.WOW.getAbbreviation())) {
                    friend.setFavoriteGame(BlizzardGames.WOW);
                } else {
                    friend.setFavoriteGame(BlizzardGames.STARCRAFT);
                }
                // add the friend to the list
                friends.add(friend);
            } while (res.moveToNext());
        }
        //----------------------------------------------------------------------------
        //get the information from each friend from the table Friend
        for (Friend friend : friends) {
            String[] selectionArgs = {Integer.toString(friend.getId())};
            Cursor resFriend = db.query(FriendContract.FriendEntry.TABLE_NAME, projectionFriend, selectionFriend, selectionArgs, null, null, null);
            resFriend.moveToFirst();
            if (resFriend.getInt(resFriend.getColumnIndex(FriendContract.FriendEntry.COLUMN_NAME_FAVORITE)) == 1) {
                friend.setFavorite(true);
            } else {
                friend.setFavorite(false);
            }
            friend.setComment(resFriend.getString(resFriend.getColumnIndex(FriendContract.FriendEntry.COLUMN_NAME_COMMENT)));
        }
        return friends;
    }


    /**
     * Function to create friend to table User
     *
     * @return true if friend was update or insert, false either.
     */
    public boolean insertFriend(Friend friend) {
        SQLiteDatabase db = this.getReadableDatabase();
        // information that you want to insert
        if (friend.getId() == -1) {
            //Add friend in User Table
            ContentValues contentValuesUserTable = new ContentValues();
            contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_TYPE, "none");
            contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_NAME, friend.getName());
            contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_FIRSTNAME, friend.getFirstName());
            contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_PSEUDO, friend.getPseudo());
            contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_PHONENUMBER, friend.getPhoneNumber().getNumber());
            contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_EMAIL, friend.getEmail().getEmail());
            if (friend.getMale()) {
                contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_MALE, 1);
            } else {
                contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_MALE, 0);
            }
            contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_BTAG, friend.getbTag().getBTag());
            contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_DESCRIPTION, friend.getDescription());
            contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_FAVORITEGAME, friend.getFavoriteGame().getAbbreviation());
            String temp = "";
            for (BlizzardGames blizzardGames : friend.getGames()) {
                temp += blizzardGames.getAbbreviation() + ",";
            }
            contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_GAMES, temp);
            //Add friend in the User Table
            long rowID = db.insert(UserContract.UserEntry.TABLE_NAME, null, contentValuesUserTable);
            friend.setId((int) rowID);
            //----------------------------------------------------------------------
            //add friend to the Friend Table
            ContentValues contentValuesFriendTable = new ContentValues();
            contentValuesFriendTable.put(FriendContract.FriendEntry.COLUMN_NAME_USER_ID, rowID);
            if (friend.isFavorite()) {
                contentValuesFriendTable.put(FriendContract.FriendEntry.COLUMN_NAME_FAVORITE, Integer.toString(1));
            } else {
                contentValuesFriendTable.put(FriendContract.FriendEntry.COLUMN_NAME_FAVORITE, Integer.toString(0));
            }
            contentValuesFriendTable.put(FriendContract.FriendEntry.COLUMN_NAME_COMMENT, friend.getComment());
            db.insert(FriendContract.FriendEntry.TABLE_NAME, null, contentValuesFriendTable);
            return true;
        } else {
            //Update friend in User Table
            ContentValues contentValuesFriend = new ContentValues();
            contentValuesFriend.put(UserContract.UserEntry.COLUMN_NAME_NAME, friend.getName());
            contentValuesFriend.put(UserContract.UserEntry.COLUMN_NAME_FIRSTNAME, friend.getFirstName());
            contentValuesFriend.put(UserContract.UserEntry.COLUMN_NAME_PSEUDO, friend.getPseudo());
            contentValuesFriend.put(UserContract.UserEntry.COLUMN_NAME_PHONENUMBER, friend.getPhoneNumber().getNumber());
            contentValuesFriend.put(UserContract.UserEntry.COLUMN_NAME_EMAIL, friend.getEmail().getEmail());
            if (friend.getMale()) {
                contentValuesFriend.put(UserContract.UserEntry.COLUMN_NAME_MALE, 1);
            } else {
                contentValuesFriend.put(UserContract.UserEntry.COLUMN_NAME_MALE, 0);
            }
            contentValuesFriend.put(UserContract.UserEntry.COLUMN_NAME_BTAG, friend.getbTag().getBTag());
            contentValuesFriend.put(UserContract.UserEntry.COLUMN_NAME_DESCRIPTION, friend.getDescription());
            contentValuesFriend.put(UserContract.UserEntry.COLUMN_NAME_FAVORITEGAME, friend.getFavoriteGame().getAbbreviation());
            String temp = "";
            for (BlizzardGames blizzardGames : friend.getGames()) {
                temp += blizzardGames.getAbbreviation() + ",";
            }
            contentValuesFriend.put(UserContract.UserEntry.COLUMN_NAME_GAMES, temp);
            //The WHERE clause
            String selectionFriend = "_ID = ?";
            //Arguments for the WHERE clause
            String[] selectionArgsFriend = {Integer.toString(friend.getId())};
            db.update(UserContract.UserEntry.TABLE_NAME, contentValuesFriend, selectionFriend, selectionArgsFriend);
            //----------------------------------------------------------------------
            //Update friend in Friend Table

            ContentValues contentValues = new ContentValues();
            contentValues.put(FriendContract.FriendEntry.COLUMN_NAME_USER_ID, friend.getId());
            if (friend.isFavorite()) {
                contentValues.put(FriendContract.FriendEntry.COLUMN_NAME_FAVORITE, Integer.toString(1));
            } else {
                contentValues.put(FriendContract.FriendEntry.COLUMN_NAME_FAVORITE, Integer.toString(0));
            }
            contentValues.put(FriendContract.FriendEntry.COLUMN_NAME_COMMENT, friend.getComment());
            // Condition to the WHERE clause
            String selection = "User_ID = ?";
            //Arguments for the WHERE clause
            String[] selectionArgs = {Integer.toString(friend.getId())};
            db.update(FriendContract.FriendEntry.TABLE_NAME, contentValues, selection, selectionArgs);
            return true;
        }
    }

    /**
     * Funtion to delete a friend in both table Friend and User
     *
     * @param friend id of the friend in the database who gonna be delete
     * @return true if deleted
     */
    public boolean deleteFriend(Friend friend) {
        SQLiteDatabase db = this.getReadableDatabase();
        //the Where condition with the friendID in the table Friend
        String selectionFriend = FriendContract.FriendEntry.COLUMN_NAME_USER_ID + " LIKE ?";
        //the Where condition with the friendID in the table User
        String selectionUser = UserContract.UserEntry._ID + " LIKE ?";
        String[] selectionArgs = {Integer.toString(friend.getId())};
        db.delete(FriendContract.FriendEntry.TABLE_NAME, selectionFriend, selectionArgs);
        db.delete(UserContract.UserEntry.TABLE_NAME, selectionUser, selectionArgs);
        return true;
    }

    /**
     * Function to update information about User
     *
     * @return true if the User is update
     */
    public boolean updateUser(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (user.getId() == -1) {
            ContentValues contentValuesUserTable = new ContentValues();
            contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_TYPE, "master");
            contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_NAME, user.getName());
            contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_FIRSTNAME, user.getFirstName());
            contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_PSEUDO, user.getPseudo());
            contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_PHONENUMBER, user.getPhoneNumber().getNumber());
            contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_EMAIL, user.getEmail().getEmail());
            if (user.getMale()) {
                contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_MALE, 1);
            } else {
                contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_MALE, 0);
            }
            contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_BTAG, user.getbTag().getBTag());
            contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_DESCRIPTION, user.getDescription());
            contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_FAVORITEGAME, user.getFavoriteGame().getAbbreviation());
            String temp = "";
            for (BlizzardGames blizzardGames : user.getGames()) {
                temp += blizzardGames.getAbbreviation() + ",";
            }
            contentValuesUserTable.put(UserContract.UserEntry.COLUMN_NAME_GAMES, temp);
            //Add friend in the Friend Table
            long rowID = db.insert(UserContract.UserEntry.TABLE_NAME, null, contentValuesUserTable);
            user.setId((int) rowID);
            return true;
        } else {
            // information that you want to update
            ContentValues contentValues = new ContentValues();
            contentValues.put(UserContract.UserEntry.COLUMN_NAME_NAME, user.getName());
            contentValues.put(UserContract.UserEntry.COLUMN_NAME_FIRSTNAME, user.getFirstName());
            contentValues.put(UserContract.UserEntry.COLUMN_NAME_PSEUDO, user.getPseudo());
            contentValues.put(UserContract.UserEntry.COLUMN_NAME_PHONENUMBER, user.getPhoneNumber().getNumber());
            contentValues.put(UserContract.UserEntry.COLUMN_NAME_EMAIL, user.getEmail().getEmail());
            if (user.getMale()) {
                contentValues.put(UserContract.UserEntry.COLUMN_NAME_MALE, 1);
            } else {
                contentValues.put(UserContract.UserEntry.COLUMN_NAME_MALE, 0);
            }
            contentValues.put(UserContract.UserEntry.COLUMN_NAME_BTAG, user.getbTag().getBTag());
            contentValues.put(UserContract.UserEntry.COLUMN_NAME_DESCRIPTION, user.getDescription());
            contentValues.put(UserContract.UserEntry.COLUMN_NAME_FAVORITEGAME, user.getFavoriteGame().getAbbreviation());
            String temp = "";
            for (BlizzardGames blizzardGames : user.getGames()) {
                temp += blizzardGames.getAbbreviation() + ",";
            }
            contentValues.put(UserContract.UserEntry.COLUMN_NAME_GAMES, temp);
            //The WHERE clause
            String selection = "_ID = ?";
            //Arguments for the WHERE clause
            String[] selectionArgs = {Integer.toString(user.getId())};
            db.update(UserContract.UserEntry.TABLE_NAME, contentValues, selection, selectionArgs);
            return true;
        }
    }


}
