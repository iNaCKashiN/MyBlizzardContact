package com.unice.myblizzardcontact.models;

import com.unice.myblizzardcontact.models.users.Friend;

import java.util.ArrayList;

/**
 * Created by KashiN (ALLENA Johann), DRAVET Jean-BAPTISTE,
 * on 14/05/2016,
 * for My Blizzard Contact.
 * <p/>
 * --
 * <p/>
 * This class represent a group of friend.
 */
public class Group {
    //-----------------------------------------------
    //Fields

    // Represent the index of the group
    private static int stateIndex = 0;
    private int id;

    // Represent the name of the group
    private String name;

    // Represent the members of the group.
    private ArrayList<Friend> members = new ArrayList<>();

    // Represent if the group is favorite.
    private Boolean favorite;

    //-----------------------------------------------
    //Constructor

    /**
     * Create a new {@code Group} instance.
     *
     * @param name The name of the group.
     */
    public Group(String name) {
        this.id = stateIndex++;
        this.name = name;
        this.favorite = false;
    }

    //-----------------------------------------------
    //Getters & Setters

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Friend> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Friend> members) {
        this.members = members;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    //-----------------------------------------------
    //Methods

    /**
     * Add a new member to the group.
     *
     * @param newMember The new member of the group.
     * @return True if the new member was added correctly else false.
     */
    public boolean addMember(Friend newMember) {
        return members.add(newMember);
    }

    /**
     * Remove a member to the group.
     *
     * @param member The member of the group to remove.
     * @return True if the member was removed correctly else false.
     */
    public boolean deleteMember(Friend member) {
        return members.contains(member) && members.remove(member);
    }

    /**
     * Remove all member to the group.
     */
    public void deleteAllMember() {
        members.clear();
    }

    /**
     * Test if a member is in the group.
     *
     * @param member The member to test.
     * @return True if the member was contained in the group else false.
     */
    public boolean groupContainMember(Friend member) {
        if (members.contains(member)) {
            return true;
        } else {
            return false;
        }
    }

    //-----------------------------------------------
    //To String

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", members=" + members +
                '}';
    }

    //-----------------------------------------------
    //HashCode & Equal

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;

        Group group = (Group) o;

        if (!getName().equals(group.getName())) return false;
        return getMembers().equals(group.getMembers());

    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getMembers().hashCode();
        return result;
    }
}
