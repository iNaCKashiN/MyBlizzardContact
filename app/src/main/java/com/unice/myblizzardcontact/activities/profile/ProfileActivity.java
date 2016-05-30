package com.unice.myblizzardcontact.activities.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.unice.myblizzardcontact.MyBlizzardContact;
import com.unice.myblizzardcontact.R;
import com.unice.myblizzardcontact.activities.contacts.ContactsActivity;
import com.unice.myblizzardcontact.activities.group.GroupsActivity;
import com.unice.myblizzardcontact.activities.tools.ToolsForActivity;
import com.unice.myblizzardcontact.database.MyBlizzardContactDbHelper;
import com.unice.myblizzardcontact.models.users.User;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by KashiN (ALLENA Johann), DRAVET Jean-BAPTISTE,
 * on 14/05/2016,
 * for My Blizzard Contact.
 * <p/>
 * --
 * <p/>
 * This class represent an activity for show the profile of user.
 * this is the first activity.
 */
public class ProfileActivity extends AppCompatActivity {
    //-----------------------------------------------
    //Fields

    // Represent this application
    private MyBlizzardContact application;

    //-----------------------------------------------
    //Fields Android

    // Represent the toolbar of this activity.
    private Toolbar toolbar;

    // Represent the block group
    private FrameLayout blockGroup;

    // Represent the block contacts
    private FrameLayout contactGroup;

    // Represent the share icon
    private ImageView shareIcon;

    // Represent the photo profile of the user.
    private CircleImageView photoProfile;

    // Represent the big pseudo of the user.
    private TextView bigPseudo;

    // Represent the number group of the user.
    private TextView nbGroup;

    // Represent the number contact of the user.
    private TextView nbContact;

    // Represent the phone number of the user.
    private TextView fieldPhone;

    // Represent the name of the user.
    private TextView fieldName;

    // Represent the first name of the user.
    private TextView fieldFirstName;

    // Represent the email of the user.
    private TextView fieldEmail;

    // Represent the sex of the user.
    private TextView fieldSex;

    // Represent the pseudo of the user.
    private TextView fieldPseudo;

    // Represent the BTag of the user.
    private TextView fieldBTag;

    // Represent the games of the user.
    private TextView fieldGames;

    // Represent the favorite game of the user.
    private TextView fieldFavoriteGame;

    // Represent the description of the user.
    private TextView fieldDescription;

    //-----------------------------------------------
    //Create

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Init application
        this.application = MyBlizzardContact.getApp();

        // Init the data Base
        application.initDataBase(new MyBlizzardContactDbHelper(getApplicationContext()));

        // Test if the user exist in data base (First connection)
        if (application.firstConnectionUser()) {
            //First connection.
            User userDefault = new User();
            application.getMyBlizzardContactDbHelper().updateUser(userDefault);
            application.setUser(userDefault);
        } else {
            // Load dataBase
            application.loadData();
        }

        // Setup the theme
        SetupTheme();

        ToolsForActivity.verifySmsPermissions(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Init toolbar
        setupToolBar();

        // init field
        setupField();

        // Init Event
        setupEvent();
    }

    /**
     * Setup the theme of the user profile.
     */
    private void SetupTheme() {
        if (this.application.getUser().getFavoriteGame() == null) {
            setTheme(R.style.ThemeApp_Wow);
        } else {
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
                default:
                    setTheme(R.style.ThemeApp_Wow);
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == ToolsForActivity.USER_PROFILE_SETTING_ACTIVITY_REQUEST) {
                finish();
                Intent intent = new Intent(this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else if (requestCode == ToolsForActivity.SHARE_ACTIVITY_REQUEST) {
                Toast.makeText(getApplicationContext(), "Success : Share SMS send.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Init toolbar
        setupToolBar();

        // init field
        setupField();

        // Init Event
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
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent profileSettingActivity = new Intent(ProfileActivity.this, ProfileSettingActivity.class);
            startActivityForResult(profileSettingActivity, ToolsForActivity.USER_PROFILE_SETTING_ACTIVITY_REQUEST);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //-----------------------------------------------
    //Method

    /**
     * Setup the field of the current activity.
     */
    public void setupField() {
        setupPhoto();

        this.bigPseudo = (TextView) findViewById(R.id.big_pseudo);
        if (this.bigPseudo != null) {
            this.bigPseudo.setText(this.application.getUser().getPseudo());
        }

        this.nbGroup = (TextView) findViewById(R.id.nb_group_id);
        if (this.nbGroup != null) {
            this.nbGroup.setText(Integer.toString(this.application.getDefaultGroups().size()));
        }

        this.nbContact = (TextView) findViewById(R.id.nb_contact_id);
        if (this.nbContact != null) {
            this.nbContact.setText(Integer.toString(this.application.getFriends().size()));
        }

        this.fieldPhone = (TextView) findViewById(R.id.field_phone_id);
        if (this.fieldPhone != null) {
            this.fieldPhone.setText(this.application.getUser().getPhoneNumber().getNumber());
        }

        this.fieldName = (TextView) findViewById(R.id.field_name_id);
        if (this.fieldName != null) {
            this.fieldName.setText(this.application.getUser().getName());
        }

        this.fieldFirstName = (TextView) findViewById(R.id.field_first_name_id);
        if (this.fieldFirstName != null) {
            this.fieldFirstName.setText(this.application.getUser().getFirstName());
        }

        this.fieldEmail = (TextView) findViewById(R.id.field_email_id);
        if (this.fieldEmail != null) {
            fieldEmail.setText(this.application.getUser().getEmail().getEmail());
        }

        this.fieldSex = (TextView) findViewById(R.id.field_sex_id);
        if (this.fieldSex != null) {
            if (this.application.getUser().getMale()) {
                this.fieldSex.setText(R.string.activity_profile_sex_male);
            } else {
                this.fieldSex.setText(R.string.activity_profile_sex_female);
            }

        }

        this.fieldPseudo = (TextView) findViewById(R.id.field_pseudo_id);
        if (this.fieldPseudo != null) {
            this.fieldPseudo.setText(this.application.getUser().getPseudo());
        }

        this.fieldBTag = (TextView) findViewById(R.id.field_btag_id);
        if (this.fieldBTag != null) {
            this.fieldBTag.setText(this.application.getUser().getbTag().getBTag());
        }

        this.fieldGames = (TextView) findViewById(R.id.field_games_id);
        if (this.fieldGames != null) {
            String temp = "";
            for (int i = 0; i < this.application.getUser().getGames().size(); i++) {
                if (i != this.application.getUser().getGames().size() - 1) {
                    temp += this.application.getUser().getGames().get(i) + ", ";
                } else {
                    temp += this.application.getUser().getGames().get(i) + ".";
                }
            }
            this.fieldGames.setText(temp);
        }

        this.fieldFavoriteGame = (TextView) findViewById(R.id.field_favorite_game_id);
        if (this.fieldFavoriteGame != null) {
            this.fieldFavoriteGame.setText(this.application.getUser().getFavoriteGame().getName());
        }

        this.fieldDescription = (TextView) findViewById(R.id.field_description_id);
        if (this.fieldDescription != null) {
            this.fieldDescription.setText(this.application.getUser().getDescription());
        }
    }

    /**
     * Setup the profile photo.
     */
    private void setupPhoto() {
        this.photoProfile = (CircleImageView) findViewById(R.id.photo_profile_id);

        File fileTemp = new File(application.getUser().getPhotoPath());

        if (fileTemp.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(application.getUser().getPhotoPath());
            this.photoProfile.setImageBitmap(bitmap);
        } else {
            this.photoProfile.setImageResource(R.mipmap.photo_profile);
        }
    }

    /**
     * Setup all profile event.
     */
    private void setupEvent() {
        this.blockGroup = (FrameLayout) findViewById(R.id.block_group_id);
        this.blockGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent groupsActivity = new Intent(ProfileActivity.this, GroupsActivity.class);
                startActivityForResult(groupsActivity, ToolsForActivity.GROUPS_ACTIVITY_REQUEST);
            }
        });
        this.contactGroup = (FrameLayout) findViewById(R.id.block_contact_id);
        this.contactGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactsActivity = new Intent(ProfileActivity.this, ContactsActivity.class);
                startActivityForResult(contactsActivity, ToolsForActivity.CONTACTS_ACTIVITY_REQUEST);
            }
        });
        this.shareIcon = (ImageView) findViewById(R.id.share_icon_id);
        this.shareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareActivity = new Intent(ProfileActivity.this, ShareActivity.class);
                startActivityForResult(shareActivity, ToolsForActivity.SHARE_ACTIVITY_REQUEST);
            }
        });
    }


}
