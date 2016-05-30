package com.unice.myblizzardcontact.activities.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.unice.myblizzardcontact.MyBlizzardContact;
import com.unice.myblizzardcontact.R;
import com.unice.myblizzardcontact.activities.tools.ToolsForActivity;
import com.unice.myblizzardcontact.models.users.PhoneNumber;

import java.util.ArrayList;

/**
 * Created by KashiN (ALLENA Johann), DRAVET Jean-BAPTISTE,
 * on 14/05/2016,
 * for My Blizzard Contact.
 * <p/>
 * --
 * <p/>
 * This class represent an activity for share the profile of the user to a phone number.
 */
public class ShareActivity extends AppCompatActivity {
    //-----------------------------------------------
    //Fields

    // Represent this application
    private MyBlizzardContact application;

    // Represent the sms manager
    private SmsManager smsManager;

    //-----------------------------------------------
    //Fields Android

    // Represent the toolbar of this activity.
    private Toolbar toolbar;

    // Represent the button for share the profile to all sms
    private ImageView shareProfileIcon;

    // Represent the button for add a new phone number.
    private ImageView addPhoneIcon;

    // Represent the button for add a new phone number.
    private LinearLayout listPhone;

    // Represent the name of the group.
    private EditText firstPhoneNumber;

    // Represent all phones number to share.
    ArrayList<EditText> phonesNumber = new ArrayList<>();

    //-----------------------------------------------
    //Create

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Init application
        this.application = MyBlizzardContact.getApp();

        // Setup the theme
        SetupTheme();

        ToolsForActivity.verifySmsPermissions(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        smsManager = SmsManager.getDefault();

        // Init toolbar
        setupToolBar();

        // Init field
        setupField();

        // Add event
        setupEvent();
    }


    /**
     * Setup the theme of the user profile.
     */
    private void SetupTheme() {
        switch (this.application.getUser().getFavoriteGame()) {
            case WOW:
                setTheme(R.style.ThemeApp_Wow);
                break;
            case DIABLO:
                setTheme(R.style.ThemeApp_Diablo);
                break;
            case STARCRAFT:
                setTheme(R.style.ThemeApp_StarCraft);
                break;
            case HOTS:
                setTheme(R.style.ThemeApp_Hots);
                break;
            case OVERWATCH:
                setTheme(R.style.ThemeApp_Overwatch);
                break;
            case HEARTHSTONE:
                setTheme(R.style.ThemeApp_Hearthstone);
                break;
        }
    }

    //-----------------------------------------------
    //Toolbar

    /**
     * Setup the toolbar of the current activity.
     */
    public void setupToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //-----------------------------------------------
    //Method

    /**
     * Setup the field of the current activity.
     */
    public void setupField() {
        this.shareProfileIcon = (ImageView) findViewById(R.id.share_sms_icon_id);
        this.addPhoneIcon = (ImageView) findViewById(R.id.add_number_icon_id);
        this.firstPhoneNumber = (EditText) findViewById(R.id.first_phone_number_id);
        this.listPhone = (LinearLayout) findViewById(R.id.list_phone_id);
        this.phonesNumber.add(firstPhoneNumber);

    }

    /**
     * Setup events of the current activity.
     */
    private void setupEvent() {
        // Event when user click on the share icons.
        addPhoneIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = new EditText(getApplicationContext());
                phonesNumber.add(editText);
                editText.setInputType(InputType.TYPE_CLASS_PHONE);
                listPhone.addView(editText);
            }
        });

        // Event when user click on the add icons.
        shareProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (EditText editText : phonesNumber) {
                    if (PhoneNumber.validatePhoneNumber(editText.getText().toString())) {
                        smsManager.sendTextMessage(editText.getText().toString(), null, application.getUser().userToString(), null, null);
                        setResult(RESULT_OK);
                        finish();
                    }
                }
            }
        });
    }

}
