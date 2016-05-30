package com.unice.myblizzardcontact.activities.tools;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;

import java.io.File;
import java.io.IOException;

/**
 * Created by KashiN (ALLENA Johann), DRAVET Jean-BAPTISTE,
 * on 14/05/2016,
 * for My Blizzard Contact.
 * <p/>
 * --
 * <p/>
 * This class represent a tools box for activities.
 */
public class ToolsForActivity {
    //-----------------------------------------------
    //Fields

    // Represent Storage Permissions variables.
    public static final int PHOTO_ACTIVITY_REQUEST = 9001;
    public static final int USER_PROFILE_SETTING_ACTIVITY_REQUEST = 9002;
    public static final int USER_CONTACT_PROFILE_SETTING_ACTIVITY_REQUEST = 9005;
    public static final int USER_CONTACT_PROFILE_ACTIVITY_REQUEST = 9006;
    public static final int CONTACTS_ACTIVITY_REQUEST = 9003;
    public static final int GROUPS_ACTIVITY_REQUEST = 9004;
    public static final int GROUP_ACTIVITY_REQUEST = 9007;
    public static final int SMS_GROUP_ACTIVITY_REQUEST = 9008;
    public static final int ADD_CONTACT_ACTIVITY_REQUEST = 9008;
    public static final int SHARE_ACTIVITY_REQUEST = 9009;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int REQUEST_EXTERNAL_SMS = 2;
    private static String[] PERMISSIONS_SMS = {
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS
    };

    //-----------------------------------------------
    //Constructor

    /**
     * Impossible create a new instance of {@code ToolsForActivity} instance.
     */
    private ToolsForActivity() {
    }

    //-----------------------------------------------
    //Getters & Setters

    //-----------------------------------------------
    //Methods

    /**
     * Verify the permission of the user, for the storage.
     *
     * @param activity The activity to verify.
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    /**
     * Verify the permission of the user, for the sms.
     *
     * @param activity The activity to verify.
     */
    public static void verifySmsPermissions(Activity activity) {
        // Check if we have send sms permission
        int sendSmsPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS);

        if (sendSmsPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_SMS,
                    REQUEST_EXTERNAL_SMS
            );
        }
    }

    /**
     * Create a new photo file.
     *
     * @return The new photo file.
     */
    public static File createImageFile(String imageFileName, File storageDir) throws IOException {
        // Create an image file name
        return new File(storageDir, imageFileName);
    }
}
