package com.unice.myblizzardcontact.activities.contacts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.unice.myblizzardcontact.MyBlizzardContact;
import com.unice.myblizzardcontact.R;
import com.unice.myblizzardcontact.activities.photo.PhotoActivity;
import com.unice.myblizzardcontact.activities.tools.ToolsForActivity;
import com.unice.myblizzardcontact.database.MyBlizzardContactDbHelper;
import com.unice.myblizzardcontact.models.blizzard.BlizzardGames;
import com.unice.myblizzardcontact.models.users.Friend;
import com.unice.myblizzardcontact.models.users.User;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by KashiN (ALLENA Johann), DRAVET Jean-BAPTISTE,
 * on 14/05/2016,
 * for My Blizzard Contact.
 * <p/>
 * --
 * <p/>
 * This class represent an activity for add contact.
 */
public class AddContactActivity extends AppCompatActivity {
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

    // Represent the photo profile of the user.
    private CircleImageView photoProfile;

    // Represent if the friend is favorite or no.
    private ImageView favoriteIcon;
    private boolean favoriteIconStatus;

    // Represent the icons for take a new photo.
    private ImageView takeNewPhoto;

    // Represent the phone number of the user.
    private EditText editPhone;

    // Represent the radio group for sex
    private RadioGroup radioSexGroup;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;

    // Represent the name of the user.
    private EditText editName;

    // Represent the first name of the user.
    private EditText editFirstName;

    // Represent the email of the user.
    private EditText editEmail;

    // Represent the pseudo of the user.
    private EditText editPseudo;

    // Represent the BTag of the user.
    private EditText editBTag;

    // Represent all radio button for the favorite game.
    private RadioButton radioHearthstone;
    private RadioButton radioHots;
    private RadioButton radioWow;
    private RadioButton radioDiablo;
    private RadioButton radioStarcraft;
    private RadioButton radioOverwatch;
    // Represent the current favorite game.
    private RadioButton favoriteGame;

    // Represent all switch button for the game.
    private Switch switchHearthstone;
    private Switch switchHots;
    private Switch switchWow;
    private Switch switchDiablo;
    private Switch switchStarcraft;
    private Switch switchOverwatch;

    // Represent the description of the user.
    private EditText editDescription;

    // Represent the comment of the user.
    private EditText editComment;

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

        // Get friend
        Bundle extras = getIntent().getExtras();
        currentFriend = new Friend();
        String tempString = extras.getString("friend");
        if (tempString != null || !tempString.equals("")) {
            currentFriend.setupWith(tempString);
        }


        // Setup the theme
        SetupTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // Setup the tools setting.
        application.getSetting().newIdSetting();

        // Init toolbar
        setupToolBar();

        // init field
        setupField();

        // Add event
        setupEvent();
    }

    /**
     * Setup the theme of the user profile Setting.
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
            if (requestCode == ToolsForActivity.PHOTO_ACTIVITY_REQUEST) {
                setupPhoto();
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
        getMenuInflater().inflate(R.menu.menu_contact_profile_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {

            // Save DataBase
            this.application.getMyBlizzardContactDbHelper().insertFriend(currentFriend);

            // Save photo
            if (application.getSetting().getTempPathPhotoFile() != null) {
                File photoFile, fileTemp;
                try {
                    photoFile = ToolsForActivity.createImageFile("friend_" + currentFriend.getId() + "_photo.jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
                    System.out.println(photoFile.getAbsolutePath());
                    fileTemp = new File(application.getSetting().getTempPathPhotoFile());
                    if (fileTemp.exists()) {
                        boolean isFileRename = fileTemp.renameTo(photoFile.getAbsoluteFile());
                        if (!isFileRename) {
                            System.out.println("Error : when temp file is renamed.");
                        }
                    } else {
                        System.out.println("Error : when temp file is get.");
                    }
                } catch (IOException ignored) {
                    System.out.println("Error : when true file is created.");
                }
            }

            saveField();

            // Save DataBase
            this.application.addFriend(currentFriend);

            setResult(Activity.RESULT_OK);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //-----------------------------------------------
    //Method

    /**
     * Setup the field of the user.
     */
    private void setupField() {
        setupPhoto();
        this.takeNewPhoto = (ImageView) findViewById(R.id.take_new_photo_id);
        this.favoriteIcon = (ImageView) findViewById(R.id.favorite_heart_icon_id);

        if (currentFriend.isFavorite()) {
            this.favoriteIcon.setImageResource(R.drawable.heart_full_icons);
            favoriteIconStatus = true;
        } else {
            this.favoriteIcon.setImageResource(R.drawable.hearth_empty_icons);
            favoriteIconStatus = false;
        }

        this.editPhone = (EditText) findViewById(R.id.edit_phone_id);
        this.editPhone.setText(currentFriend.getPhoneNumber().getNumber());

        this.editName = (EditText) findViewById(R.id.edit_name_id);
        this.editName.setText(currentFriend.getName());

        this.editFirstName = (EditText) findViewById(R.id.edit_first_name_id);
        this.editFirstName.setText(currentFriend.getFirstName());

        this.editEmail = (EditText) findViewById(R.id.edit_email_id);
        this.editEmail.setText(currentFriend.getEmail().getEmail());

        this.radioSexGroup = (RadioGroup) findViewById(R.id.radio_group_sex_id);
        this.radioButtonMale = (RadioButton) findViewById(R.id.radio_male_id);
        this.radioButtonFemale = (RadioButton) findViewById(R.id.radio_female_id);

        if (currentFriend.getMale()) {
            this.radioButtonMale.setChecked(true);
        } else {
            this.radioButtonFemale.setChecked(true);
        }

        this.editPseudo = (EditText) findViewById(R.id.edit_pseudo_id);
        this.editPseudo.setText(currentFriend.getPseudo());

        this.editBTag = (EditText) findViewById(R.id.edit_btag_id);
        this.editBTag.setText(currentFriend.getbTag().getBTag());

        this.editDescription = (EditText) findViewById(R.id.edit_description_id);
        this.editDescription.setText(currentFriend.getDescription());

        this.editComment = (EditText) findViewById(R.id.edit_comment_id);
        this.editComment.setText(currentFriend.getComment());

        // Setup radio button.
        setupRadio();

        // Setup switch button.
        setupSwitch();
    }

    /**
     * Setup the switch button
     */
    private void setupSwitch() {
        this.switchHearthstone = (Switch) findViewById(R.id.switch_hearthStone_id);
        this.switchHots = (Switch) findViewById(R.id.switch_hots_id);
        this.switchWow = (Switch) findViewById(R.id.switch_wow_id);
        this.switchDiablo = (Switch) findViewById(R.id.switch_diablo_id);
        this.switchStarcraft = (Switch) findViewById(R.id.switch_starcraft_id);
        this.switchOverwatch = (Switch) findViewById(R.id.switch_overwatch_id);
        for (BlizzardGames blizzardGames : currentFriend.getGames()) {
            switch (blizzardGames) {
                case DIABLO:
                    this.switchDiablo.setChecked(true);
                    break;
                case HEARTHSTONE:
                    this.switchHearthstone.setChecked(true);
                    break;
                case HOTS:
                    this.switchHots.setChecked(true);
                    break;
                case OVERWATCH:
                    this.switchOverwatch.setChecked(true);
                    break;
                case WOW:
                    this.switchWow.setChecked(true);
                    break;
                case STARCRAFT:
                    this.switchStarcraft.setChecked(true);
                    break;
            }
        }
    }

    /**
     * Setup the Radio button
     */
    private void setupRadio() {
        // Init the radio button
        this.radioHearthstone = (RadioButton) findViewById(R.id.radio_hearthstone_id);
        this.radioHearthstone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFavoriteGame(radioHearthstone);
            }
        });

        this.radioHots = (RadioButton) findViewById(R.id.radio_hots_id);
        this.radioHots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFavoriteGame(radioHots);
            }
        });

        this.radioWow = (RadioButton) findViewById(R.id.radio_wow_id);
        this.radioWow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFavoriteGame(radioWow);
            }
        });

        this.radioDiablo = (RadioButton) findViewById(R.id.radio_diablo_id);
        this.radioDiablo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFavoriteGame(radioDiablo);
            }
        });

        this.radioStarcraft = (RadioButton) findViewById(R.id.radio_starcraft_id);
        this.radioStarcraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFavoriteGame(radioStarcraft);
            }
        });

        this.radioOverwatch = (RadioButton) findViewById(R.id.radio_overwatch_id);
        this.radioOverwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFavoriteGame(radioOverwatch);
            }
        });

        switch (currentFriend.getFavoriteGame()) {
            case WOW:
                updateFavoriteGame(radioWow);
                radioWow.setChecked(true);
                break;
            case HOTS:
                updateFavoriteGame(radioHots);
                radioHots.setChecked(true);
                break;
            case HEARTHSTONE:
                updateFavoriteGame(radioHearthstone);
                radioHearthstone.setChecked(true);
                break;
            case OVERWATCH:
                updateFavoriteGame(radioOverwatch);
                radioOverwatch.setChecked(true);
                break;
            case DIABLO:
                updateFavoriteGame(radioDiablo);
                radioDiablo.setChecked(true);
                break;
            case STARCRAFT:
                updateFavoriteGame(radioStarcraft);
                radioStarcraft.setChecked(true);
                break;
        }
    }

    /**
     * Set a new favorite game.
     *
     * @param radioButton The new favorite game.
     */
    private void updateFavoriteGame(RadioButton radioButton) {
        if (favoriteGame != null && !favoriteGame.equals(radioButton)) {
            favoriteGame.setChecked(false);
        }
        favoriteGame = radioButton;

    }

    /**
     * Setup events of the current activity.
     */
    private void setupEvent() {
        // Event when user click on new photo.
        takeNewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoActivity = new Intent(AddContactActivity.this, PhotoActivity.class);
                startActivityForResult(photoActivity, ToolsForActivity.PHOTO_ACTIVITY_REQUEST);
            }
        });

        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!favoriteIconStatus) {
                    favoriteIconStatus = !favoriteIconStatus;
                    favoriteIcon.setImageResource(R.drawable.heart_full_icons);
                } else {
                    favoriteIconStatus = !favoriteIconStatus;
                    favoriteIcon.setImageResource(R.drawable.hearth_empty_icons);
                }
            }
        });
    }

    /**
     * Init the profile photo.
     */
    private void setupPhoto() {
        this.photoProfile = (CircleImageView) findViewById(R.id.photo_profile_id);

        if (application.getSetting().getTempPathPhotoFile() != null) {
            File fileTemp = new File(application.getSetting().getTempPathPhotoFile());
            if (fileTemp.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(fileTemp.getPath());
                this.photoProfile.setImageBitmap(bitmap);
            } else {
                this.photoProfile.setImageResource(R.mipmap.photo_profile);
            }
        } else {
            this.photoProfile.setImageResource(R.mipmap.photo_profile);
        }
    }

    /**
     * Save the change of all field.
     */
    private void saveField() {
        currentFriend.setFavorite(favoriteIconStatus);

        currentFriend.getPhoneNumber().setNumber(String.valueOf(this.editPhone.getText()));

        currentFriend.setName(String.valueOf(this.editName.getText()));

        currentFriend.setFirstName(String.valueOf(this.editFirstName.getText()));

        if (radioButtonMale.isChecked()) {
            currentFriend.setMale(true);
        } else {
            currentFriend.setMale(false);
        }

        currentFriend.getEmail().setEmail(String.valueOf(this.editEmail.getText()));

        currentFriend.setPseudo(String.valueOf(this.editPseudo.getText()));

        currentFriend.getbTag().setBTag(String.valueOf(this.editBTag.getText()));

        currentFriend.setDescription(String.valueOf(this.editDescription.getText()));

        currentFriend.setComment(String.valueOf(this.editComment.getText()));

        // Save radio button.
        saveRadio();

        // Save switch button.
        saveSwitch();

    }

    /**
     * Save the favorite game.
     */
    private void saveRadio() {
        if (favoriteGame.equals(radioDiablo)) {
            currentFriend.setFavoriteGame(BlizzardGames.DIABLO);
        } else if (favoriteGame.equals(radioWow)) {
            currentFriend.setFavoriteGame(BlizzardGames.WOW);
        } else if (favoriteGame.equals(radioHearthstone)) {
            currentFriend.setFavoriteGame(BlizzardGames.HEARTHSTONE);
        } else if (favoriteGame.equals(radioHots)) {
            currentFriend.setFavoriteGame(BlizzardGames.HOTS);
        } else if (favoriteGame.equals(radioOverwatch)) {
            currentFriend.setFavoriteGame(BlizzardGames.OVERWATCH);
        } else if (favoriteGame.equals(radioStarcraft)) {
            currentFriend.setFavoriteGame(BlizzardGames.STARCRAFT);
        }
    }

    /**
     * Save the game of the user.
     */
    private void saveSwitch() {
        currentFriend.getGames().clear();
        if (this.switchHearthstone.isChecked()) {
            currentFriend.getGames().add(BlizzardGames.HEARTHSTONE);
        }
        if (this.switchHots.isChecked()) {
            currentFriend.getGames().add(BlizzardGames.HOTS);
        }
        if (this.switchWow.isChecked()) {
            currentFriend.getGames().add(BlizzardGames.WOW);
        }
        if (this.switchDiablo.isChecked()) {
            currentFriend.getGames().add(BlizzardGames.DIABLO);
        }
        if (this.switchStarcraft.isChecked()) {
            currentFriend.getGames().add(BlizzardGames.STARCRAFT);
        }
        if (this.switchOverwatch.isChecked()) {
            currentFriend.getGames().add(BlizzardGames.OVERWATCH);
        }
    }
}
