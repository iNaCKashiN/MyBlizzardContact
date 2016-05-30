package com.unice.myblizzardcontact.activities.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.unice.myblizzardcontact.MyBlizzardContact;
import com.unice.myblizzardcontact.R;
import com.unice.myblizzardcontact.activities.tools.ToolsForActivity;
import com.unice.myblizzardcontact.models.Group;
import com.unice.myblizzardcontact.models.users.Friend;

/**
 * Created by KashiN (ALLENA Johann), DRAVET Jean-BAPTISTE,
 * on 14/05/2016,
 * for My Blizzard Contact.
 * <p/>
 * --
 * <p/>
 * This class represent an activity for send sms to a group.
 */
public class SmsGroupActivity extends AppCompatActivity {
    //-----------------------------------------------
    //Fields

    // Represent this application
    private MyBlizzardContact application;

    // Represent the current group.
    private Group currentGroup;

    // Represent the sms manager
    private SmsManager smsManager;

    //-----------------------------------------------
    //Fields Android

    // Represent the toolbar of this activity.
    private Toolbar toolbar;

    // Represent the button for send the sms
    private ImageView sendSmsIcon;

    // Represent the name of the group.
    private TextView nameGroup;

    // Represent the number of member for hte group
    private TextView nbMemberGroup;

    // Represent the Text message.
    private EditText messageText;

    //-----------------------------------------------
    //Create

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Init application
        this.application = MyBlizzardContact.getApp();

        // Setup the theme
        SetupTheme();

        // Get Group
        Bundle extras = getIntent().getExtras();
        for (Group group : application.getDefaultGroups()) {
            if (extras.getInt("id") == group.getId()) {
                currentGroup = group;
                break;
            }
        }

        ToolsForActivity.verifySmsPermissions(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_group);

        // Setup the sms manager
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Init toolbar
        setupToolBar();

        // Init field
        setupField();

        // Add event
        setupEvent();
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
    //Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sms_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // TODO: 27/05/2016 Launch for a future maj of this application a setting for the sms send.
        }
        return super.onOptionsItemSelected(item);
    }

    //-----------------------------------------------
    //Method

    /**
     * Setup the field of the current activity.
     */
    public void setupField() {
        this.sendSmsIcon = (ImageView) findViewById(R.id.send_sms_icon_id);
        this.nameGroup = (TextView) findViewById(R.id.name_group_id);
        this.nameGroup.setText(currentGroup.getName());
        this.nbMemberGroup = (TextView) findViewById(R.id.number_member_id);
        String stringTemp = currentGroup.getMembers().size() + " Members";
        this.nbMemberGroup.setText(stringTemp);
        this.messageText = (EditText) findViewById(R.id.message_text_id);
    }

    /**
     * Setup events of the current activity.
     */
    private void setupEvent() {
        // Event when user click on new photo.
        sendSmsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(messageText.getText().toString() == null || messageText.getText().toString().equals(""))){
                    for (Friend friend : currentGroup.getMembers()) {
                        smsManager.sendTextMessage(friend.getPhoneNumber().getNumber().replace(" ", ""), null, messageText.getText().toString(), null, null);
                        setResult(RESULT_OK);
                        finish();
                    }
                }
            }
        });
    }

}
