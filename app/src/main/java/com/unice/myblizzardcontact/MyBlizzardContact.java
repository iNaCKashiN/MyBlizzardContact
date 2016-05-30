package com.unice.myblizzardcontact;

import com.unice.myblizzardcontact.database.MyBlizzardContactDbHelper;
import com.unice.myblizzardcontact.models.Group;
import com.unice.myblizzardcontact.models.blizzard.BlizzardGames;
import com.unice.myblizzardcontact.models.users.Friend;
import com.unice.myblizzardcontact.models.users.User;
import com.unice.myblizzardcontact.tools.Setting;

import java.util.ArrayList;

/**
 * Created by KashiN (ALLENA Johann), DRAVET Jean-BAPTISTE,
 * on 14/05/2016,
 * for My Blizzard Contact.
 * <p/>
 * --
 * <p/>
 * Represent the "My Blizzard Application".
 */
public class MyBlizzardContact {
    //-----------------------------------------------
    //Fields

    // Represent the dataBase
    MyBlizzardContactDbHelper myBlizzardContactDbHelper = null;

    // Represent the user of the application
    private User user;

    // Represent the setting of this application.
    private Setting setting;

    // Represent the friend of the user.
    private ArrayList<Friend> friends = new ArrayList<>();

    // Represent all default group.
    private ArrayList<Group> defaultGroups = new ArrayList<>();

    // Represent all custom group.
    private ArrayList<Group> customGroups = new ArrayList<>();

    // Create an object of I18n (Singleton Pattern)
    private static MyBlizzardContact app = new MyBlizzardContact();

    // Represent the default group
    private Group groupWow;
    private Group groupHearthstone;
    private Group groupHots;
    private Group groupOverwatch;
    private Group groupDiablo;
    private Group groupStarcraft;

    //-----------------------------------------------
    //Constructor

    /**
     * Create a new {@code MyBlizzardContact} instance.
     */
    private MyBlizzardContact() {
        // Init setting.
        this.setting = new Setting();

        // Setup default group
        setupDefaultGroup();
    }

    //-----------------------------------------------
    //Getters & Setters

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
    }

    public ArrayList<Group> getDefaultGroups() {
        return defaultGroups;
    }

    public void setDefaultGroups(ArrayList<Group> defaultGroups) {
        this.defaultGroups = defaultGroups;
    }

    public ArrayList<Group> getCustomGroups() {
        return customGroups;
    }

    public void setCustomGroups(ArrayList<Group> customGroups) {
        this.customGroups = customGroups;
    }

    public static MyBlizzardContact getApp() {
        return app;
    }

    public static void setApp(MyBlizzardContact app) {
        MyBlizzardContact.app = app;
    }

    public Group getGroupWow() {
        return groupWow;
    }

    public void setGroupWow(Group groupWow) {
        this.groupWow = groupWow;
    }

    public Group getGroupHearthstone() {
        return groupHearthstone;
    }

    public void setGroupHearthstone(Group groupHearthstone) {
        this.groupHearthstone = groupHearthstone;
    }

    public Group getGroupHots() {
        return groupHots;
    }

    public void setGroupHots(Group groupHots) {
        this.groupHots = groupHots;
    }

    public Group getGroupOverwatch() {
        return groupOverwatch;
    }

    public void setGroupOverwatch(Group groupOverwatch) {
        this.groupOverwatch = groupOverwatch;
    }

    public Group getGroupDiablo() {
        return groupDiablo;
    }

    public void setGroupDiablo(Group groupDiablo) {
        this.groupDiablo = groupDiablo;
    }

    public Group getGroupStarcraft() {
        return groupStarcraft;
    }

    public void setGroupStarcraft(Group groupStarcraft) {
        this.groupStarcraft = groupStarcraft;
    }

    public MyBlizzardContactDbHelper getMyBlizzardContactDbHelper() {
        return myBlizzardContactDbHelper;
    }

    public void setMyBlizzardContactDbHelper(MyBlizzardContactDbHelper myBlizzardContactDbHelper) {
        this.myBlizzardContactDbHelper = myBlizzardContactDbHelper;
    }

    //-----------------------------------------------
    //Methods


    /**
     * Setup the access dataBase of this application.
     *
     * @param myBlizzardContactDbHelper The access data base.
     */
    public void initDataBase(MyBlizzardContactDbHelper myBlizzardContactDbHelper) {
        if (this.myBlizzardContactDbHelper == null) {
            this.myBlizzardContactDbHelper = myBlizzardContactDbHelper;
        }
    }

    public boolean firstConnectionUser() {
        return !this.myBlizzardContactDbHelper.userExist();
    }


    /**
     * Lauch the load of the data base.
     */
    public void loadDataFromDataBase() {
        if (this.myBlizzardContactDbHelper != null) {
            loadData();
        } else {
            System.out.println("Error the dataBase is not init.");
        }
    }

    /**
     * Load data base.
     */
    public void loadData() {
        // Clear all data.
        friends.clear();

        // Load the user from data base.
        loadUser();

        // Load all friend from data base.
        loadFriends();

        // Init the default group.
        for (Friend friend : friends) {
            setupDefaultGroupForFriend(friend);
        }
    }

    /**
     * Load the user of this application.
     */
    private void loadUser() {
        this.user = myBlizzardContactDbHelper.getUser();
    }

    /**
     * Load friend from data Base.
     */
    private void loadFriends() {
        this.friends = myBlizzardContactDbHelper.getFriends();
    }

    /**
     * Generate default group.
     */
    private void setupDefaultGroup() {
        // Create blizzard group.
        this.groupWow = new Group(BlizzardGames.WOW.getName());
        this.groupHearthstone = new Group(BlizzardGames.HEARTHSTONE.getName());
        this.groupHots = new Group(BlizzardGames.HOTS.getName());
        this.groupOverwatch = new Group(BlizzardGames.OVERWATCH.getName());
        this.groupDiablo = new Group(BlizzardGames.DIABLO.getName());
        this.groupStarcraft = new Group(BlizzardGames.STARCRAFT.getName());

        // Add group
        defaultGroups.add(groupWow);
        defaultGroups.add(groupHearthstone);
        defaultGroups.add(groupHots);
        defaultGroups.add(groupOverwatch);
        defaultGroups.add(groupDiablo);
        defaultGroups.add(groupStarcraft);
    }

    /**
     * Add a new friend to the application.
     *
     * @param friend The new friend.
     */
    public void addFriend(Friend friend) {
        friends.add(friend);
        getMyBlizzardContactDbHelper().insertFriend(friend);
        setupDefaultGroupForFriend(friend);
    }

    /**
     * Add a new friend to the application.
     *
     * @param friend The new friend.
     */
    public void UpdateFriend(Friend friend) {
        getMyBlizzardContactDbHelper().insertFriend(friend);
        setupDefaultGroupForFriend(friend);
    }

    /**
     * delete a friend to the application.
     *
     * @param friend The friend.
     */
    public void deleteFriend(Friend friend) {
        getMyBlizzardContactDbHelper().deleteFriend(friend);
        setupDefaultGroupForFriend(friend);
        for (Group group : defaultGroups) {
            if (group.groupContainMember(friend)) {
                group.deleteMember(friend);
            }
        }
        friends.remove(friend);
    }

    /**
     * Setup default member default group.
     *
     * @param friend The friend to add.
     */
    public void setupDefaultGroupForFriend(Friend friend) {
        for (Group group : defaultGroups) {
            if (group.groupContainMember(friend)) {
                group.deleteMember(friend);
            }
        }
        for (BlizzardGames blizzardGames : friend.getGames()) {
            switch (blizzardGames) {
                case DIABLO:
                    groupDiablo.addMember(friend);
                    break;
                case HEARTHSTONE:
                    groupHearthstone.addMember(friend);
                    break;
                case HOTS:
                    groupHots.addMember(friend);
                    break;
                case OVERWATCH:
                    groupOverwatch.addMember(friend);
                    break;
                case STARCRAFT:
                    groupStarcraft.addMember(friend);
                    break;
                case WOW:
                    groupWow.addMember(friend);
                    break;
            }
        }
    }
}
