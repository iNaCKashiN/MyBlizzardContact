package com.unice.myblizzardcontact.activities.contacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.unice.myblizzardcontact.MyBlizzardContact;
import com.unice.myblizzardcontact.R;
import com.unice.myblizzardcontact.activities.group.GroupsActivity;
import com.unice.myblizzardcontact.activities.tools.ToolsForActivity;
import com.unice.myblizzardcontact.models.users.Friend;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by KashiN (ALLENA Johann), DRAVET Jean-BAPTISTE,
 * on 14/05/2016,
 * for My Blizzard Contact.
 * <p/>
 * --
 * <p/>
 * This class represent an activity for Show the profile of the contact.
 */
public class ContactProfileActivity extends AppCompatActivity {
    //-----------------------------------------------
    //Fields

    // Represent this application
    private MyBlizzardContact application;

    // Represent the friend of this activity;
    private Friend currentFriend = null;

    //-----------------------------------------------
    //Fields Android

    // Represent the toolbar of this activity.
    private Toolbar toolbar;

    // Represent the photo profile of the friend.
    private CircleImageView photoProfile;

    // Represent the button delete.
    private ImageView deleteIcon;

    // Represent the button share.
    private ImageView shareIcon;

    // Represent if the friend is favorite
    private ImageView favoriteIcon;

    // Represent the big pseudo of the friend.
    private TextView bigPseudo;

    // Represent the phone number of the friend.
    private TextView fieldPhone;

    // Represent the name of the friend.
    private TextView fieldName;

    // Represent the first name of the friend.
    private TextView fieldFirstName;

    // Represent the email of the friend.
    private TextView fieldEmail;

    // Represent the sex of the friend.
    private TextView fieldSex;

    // Represent the pseudo of the friend.
    private TextView fieldPseudo;

    // Represent the BTag of the friend.
    private TextView fieldBTag;

    // Represent the games of the friend.
    private TextView fieldGames;

    // Represent the favorite game of the friend.
    private TextView fieldFavoriteGame;

    // Represent the description of the friend.
    private TextView fieldDescription;

    // Represent the comment of the friend.
    private TextView fieldComment;

    //-----------------------------------------------
    //Create

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Init application
        this.application = MyBlizzardContact.getApp();

        // Get friend
        Bundle extras = getIntent().getExtras();
        for (Friend friend : application.getFriends()) {
            if (extras.getInt("id") == friend.getId()) {
                currentFriend = friend;
                break;
            }
        }

        if (currentFriend == null) {
            finish();
        }
        // Setup the theme
        SetupTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_profile);


        // Init toolbar
        setupToolBar();

        // init field
        setupField();

        // Init Event
        setupEvent();
    }

    /**
     * Setup the theme of the friend profile.
     */
    private void SetupTheme() {
        switch (currentFriend.getFavoriteGame()) {
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
            if (requestCode == ToolsForActivity.USER_CONTACT_PROFILE_SETTING_ACTIVITY_REQUEST) {
                finish();
                Intent intent = new Intent(this, ContactProfileActivity.class);
                intent.putExtra("id", currentFriend.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
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
    //Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent contactProfileSettingActivity = new Intent(ContactProfileActivity.this, ContactSettingActivity.class);
            contactProfileSettingActivity.putExtra("id", currentFriend.getId());
            startActivityForResult(contactProfileSettingActivity, ToolsForActivity.USER_CONTACT_PROFILE_SETTING_ACTIVITY_REQUEST);
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


        this.favoriteIcon = (ImageView) findViewById(R.id.favorite_icon_id);
        if (currentFriend.isFavorite()) {
            favoriteIcon.setImageResource(R.drawable.heart_full_icons);
        } else {
            favoriteIcon.setImageResource(R.drawable.hearth_empty_icons);
        }

        this.bigPseudo = (TextView) findViewById(R.id.big_pseudo);
        if (this.bigPseudo != null) {
            this.bigPseudo.setText(currentFriend.getPseudo());
        }

        this.fieldPhone = (TextView) findViewById(R.id.field_phone_id);
        if (this.fieldPhone != null) {
            this.fieldPhone.setText(currentFriend.getPhoneNumber().getNumber());
        }

        this.fieldName = (TextView) findViewById(R.id.field_name_id);
        if (this.fieldName != null) {
            this.fieldName.setText(currentFriend.getName());
        }

        this.fieldFirstName = (TextView) findViewById(R.id.field_first_name_id);
        if (this.fieldFirstName != null) {
            this.fieldFirstName.setText(currentFriend.getFirstName());
        }

        this.fieldEmail = (TextView) findViewById(R.id.field_email_id);
        if (this.fieldEmail != null) {
            fieldEmail.setText(currentFriend.getEmail().getEmail());
        }

        this.fieldPseudo = (TextView) findViewById(R.id.field_pseudo_id);
        if (this.fieldPseudo != null) {
            this.fieldPseudo.setText(currentFriend.getPseudo());
        }

        this.fieldSex = (TextView) findViewById(R.id.field_sex_id);
        if (this.fieldSex != null) {
            if (currentFriend.getMale()) {
                this.fieldSex.setText(R.string.activity_profile_sex_male);
            } else {
                this.fieldSex.setText(R.string.activity_profile_sex_male);
            }
        }

        this.fieldBTag = (TextView) findViewById(R.id.field_btag_id);
        if (this.fieldBTag != null) {
            this.fieldBTag.setText(currentFriend.getbTag().getBTag());
        }

        this.fieldGames = (TextView) findViewById(R.id.field_games_id);
        if (this.fieldGames != null) {
            String temp = "";
            for (int i = 0; i < currentFriend.getGames().size(); i++) {
                if (i != currentFriend.getGames().size() - 1) {
                    temp += currentFriend.getGames().get(i) + ", ";
                } else {
                    temp += currentFriend.getGames().get(i) + ".";
                }
            }
            this.fieldGames.setText(temp);
        }

        this.fieldFavoriteGame = (TextView) findViewById(R.id.field_favorite_game_id);
        if (this.fieldFavoriteGame != null) {
            this.fieldFavoriteGame.setText(currentFriend.getFavoriteGame().getName());
        }

        this.fieldDescription = (TextView) findViewById(R.id.field_description_id);
        if (this.fieldDescription != null) {
            this.fieldDescription.setText(currentFriend.getDescription());
        }

        this.fieldComment = (TextView) findViewById(R.id.field_comment_id);
        if (this.fieldComment != null) {
            this.fieldComment.setText(currentFriend.getComment());
        }
    }


    /**
     * Setup the profile photo.
     */
    private void setupPhoto() {
        this.photoProfile = (CircleImageView) findViewById(R.id.photo_profile_id);

        File fileTemp = new File(currentFriend.getPhotoPath());
        System.out.println(currentFriend.getPhotoPath());

        if (fileTemp.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(currentFriend.getPhotoPath());
            this.photoProfile.setImageBitmap(bitmap);
        } else {
            this.photoProfile.setImageResource(R.mipmap.photo_profile);
        }
    }

    /**
     * Setup all profile event.
     */
    private void setupEvent() {
        this.deleteIcon = (ImageView) findViewById(R.id.delete_icon_id);
        this.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                application.deleteFriend(currentFriend);
                finish();
            }
        });

        this.shareIcon = (ImageView) findViewById(R.id.share_icons_id);
        this.shareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 22/05/2016 Share friend for a future maj of this application.
            }
        });
    }
}
