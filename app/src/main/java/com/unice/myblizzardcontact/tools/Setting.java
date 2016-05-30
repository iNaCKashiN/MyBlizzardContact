package com.unice.myblizzardcontact.tools;

/**
 * Created by KashiN (ALLENA Johann), DRAVET Jean-BAPTISTE,
 * on 21/05/2016,
 * for My Blizzard Contact.
 * <p/>
 * --
 * <p/>
 * This class is use for manage all setting.
 */
public class Setting {
    //-----------------------------------------------
    //Fields

    // Represent the current path for temp photo for profile.
    private String tempPathPhotoFile;

    // Represent the index of setting when the temp path photo was update.
    private int idTempPathPhotoFile = -1;

    // Represent the index of the setting.
    private int idSetting = 0;

    //-----------------------------------------------
    //Constructor

    /**
     * Create a new {@code Setting} instance.
     */
    public Setting() {
        tempPathPhotoFile = null;
    }

    //-----------------------------------------------
    //Getters and setters

    public String getTempPathPhotoFile() {
        if (idTempPathPhotoFile == idSetting) {
            return tempPathPhotoFile;
        } else {
            return null;
        }
    }

    public void setTempPathPhotoFile(String tempPathPhotoFile) {
        this.tempPathPhotoFile = tempPathPhotoFile;
        this.idTempPathPhotoFile = idSetting;
    }

    //-----------------------------------------------
    //Methods

    /**
     * Get the current id for path temp photo.
     *
     * @return the current id for path photo temp.
     */
    public void newIdSetting() {
        idSetting++;
    }
}
