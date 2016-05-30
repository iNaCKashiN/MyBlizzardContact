package com.unice.myblizzardcontact.activities.photo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.unice.myblizzardcontact.MyBlizzardContact;
import com.unice.myblizzardcontact.R;
import com.unice.myblizzardcontact.activities.tools.ToolsForActivity;

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
 * This class represent an activity for take a photo.
 */
public class PhotoActivity extends AppCompatActivity {
    //-----------------------------------------------
    //Fields

    // The request image capture for the camera.
    static final int REQUEST_TAKE_PHOTO = 1;

    // The request image get from gallery.
    static final int REQUEST_GET_PHOTO = 2;

    // Represent this application
    private MyBlizzardContact application;

    // Represent the temp file of the photo.
    private String tempPathPhotoFile;

    //-----------------------------------------------
    //Fields Android

    // Represent the toolbar of this activity.
    private Toolbar toolbar;

    // Represent the profile photo take.
    private CircleImageView photoProfile;

    // Represent the textView for take photo;
    private TextView takePhoto;

    // Represent the textView for take photo;
    private TextView fromGallery;

    //-----------------------------------------------
    //Create

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Init application
        this.application = MyBlizzardContact.getApp();

        // Setup the theme
        SetupTheme();

        ToolsForActivity.verifyStoragePermissions(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

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
        this.toolbar = (Toolbar) findViewById(R.id.tool_bar_id);
        setSupportActionBar(toolbar);
    }

    //-----------------------------------------------
    //Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            application.getSetting().setTempPathPhotoFile(tempPathPhotoFile);

            setResult(Activity.RESULT_OK);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //-----------------------------------------------
    //Method

    /**
     * Launch the photo intent for take a photo.
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFileTemp = null;
            try {
                photoFileTemp = ToolsForActivity.createImageFile("user_photo-temp.jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
                tempPathPhotoFile = photoFileTemp.getAbsolutePath();
            } catch (IOException ignored) {
                System.out.println("Error : when temp file is created.");
            }
            if (photoFileTemp != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFileTemp));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    /**
     * Launch the gallery intent to get a photo from gallery.
     */
    private void dispatchGalleryPictureIntent() {
        Intent getPictureIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(getPictureIntent, REQUEST_GET_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(tempPathPhotoFile);
            photoProfile.setImageBitmap(bitmap);
        } else if (requestCode == REQUEST_GET_PHOTO && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            tempPathPhotoFile = picturePath;
            Bitmap bitmap = BitmapFactory.decodeFile(tempPathPhotoFile);
            if (bitmap != null) {
                photoProfile.setImageBitmap(bitmap);
            }
        }
    }

    //-----------------------------------------------
    //Method

    /**
     * Setup the field of the current activity.
     */
    public void setupField() {
        setupPhoto();
        this.takePhoto = (TextView) findViewById(R.id.take_photo_id);
        this.fromGallery = (TextView) findViewById(R.id.gallery_photo_id);
    }

    /**
     * Init the profile photo.
     */
    private void setupPhoto() {
        this.photoProfile = (CircleImageView) findViewById(R.id.photo_profile_id);
        this.photoProfile.setImageResource(R.mipmap.photo_profile);
    }

    /**
     * Setup events of the current activity.
     */
    private void setupEvent() {
        // Event when user click on new photo.
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        // Event when user click on gallery photo.
        fromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchGalleryPictureIntent();
            }
        });
    }


}
