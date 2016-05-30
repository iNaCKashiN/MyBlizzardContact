package com.unice.myblizzardcontact.activities.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.unice.myblizzardcontact.MyBlizzardContact;
import com.unice.myblizzardcontact.R;
import com.unice.myblizzardcontact.activities.tools.ToolsForActivity;
import com.unice.myblizzardcontact.models.users.Friend;

/**
 * Created by KashiN (ALLENA Johann), DRAVET Jean-BAPTISTE,
 * on 14/05/2016,
 * for My Blizzard Contact.
 * <p/>
 * --
 * <p/>
 * This class represent an activity for show all contact.
 */
public class ContactsActivity extends AppCompatActivity {
    //-----------------------------------------------
    //Fields

    // Represent this application
    private MyBlizzardContact application;

    //-----------------------------------------------
    //Fields Android

    // Represent the toolbar of this activity.
    private Toolbar toolbar;

    // Represent the list of contact
    private ListView contactsList;

    // Represent the sort name.
    private TextView sortName;

    // Represent the sort option
    private FrameLayout blockSortOption;

    //-----------------------------------------------
    //Create

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Setup application
        this.application = MyBlizzardContact.getApp();

        // Setup the theme
        SetupTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        // Setup toolbar
        setupToolBar();

        // Setup field
        setupField();

        // Populate list view
        populateListView();

        // Setup Event
        setupEvent();
    }

    /**
     * Setup the theme of this activity.
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
            if (requestCode == ToolsForActivity.USER_CONTACT_PROFILE_ACTIVITY_REQUEST) {

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Setup field
        setupField();

        // Populate list view
        populateListView();

        // Setup Event
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
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            Intent addContactsActivity = new Intent(ContactsActivity.this, AddContactActivity.class);
            addContactsActivity.putExtra("friend", "");
            startActivityForResult(addContactsActivity, ToolsForActivity.ADD_CONTACT_ACTIVITY_REQUEST);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //-----------------------------------------------
    //Methods

    /**
     * Setup the field of the current activity.
     */
    public void setupField() {
        this.contactsList = (ListView) findViewById(R.id.contact_list_id);
        this.sortName = (TextView) findViewById(R.id.name_short_id);
        this.blockSortOption = (FrameLayout) findViewById(R.id.block_sort_option_id);
    }

    /**
     * Setup all profile event.
     */
    private void setupEvent() {
    }

    /**
     * Populate the list view.
     */
    private void populateListView() {
        ArrayAdapter<Friend> adapter = new MyListAdapter();
        contactsList.setAdapter(adapter);
    }

    /**
     * Created by KashiN (ALLENA Johann),
     * on 22/05/2016,
     * for My Blizzard Contact.
     * <p/>
     * --
     * <p/>
     * This class represent a adapter for the list view on the contacts activity.
     */
    private class MyListAdapter extends ArrayAdapter<Friend> {
        //-----------------------------------------------
        //Fields

        // Represent the hearth if the friend is favorite
        private ImageView favoriteImage;

        // Represent the name of the friend.
        private TextView nameFriend;

        // Represent the name of the friend.
        private FrameLayout blockContactId;

        //-----------------------------------------------
        //Constructor

        public MyListAdapter() {
            super(ContactsActivity.this, R.layout.item_contacts, application.getFriends());
        }

        //-----------------------------------------------
        //Methods

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_contacts, parent, false);
            }

            // Get the good friend.
            final Friend currentFriend = application.getFriends().get(position);

            // Fill the view
            favoriteImage = (ImageView) itemView.findViewById(R.id.item_favorite_id);
            nameFriend = (TextView) itemView.findViewById(R.id.item_name_id);
            blockContactId = (FrameLayout) itemView.findViewById(R.id.block_contact_id);

            if (currentFriend.isFavorite()) {
                favoriteImage.setImageResource(R.drawable.heart_full_icons);
            } else {
                favoriteImage.setImageResource(R.drawable.hearth_empty_icons);
            }

            nameFriend.setText(currentFriend.getName());

            blockContactId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent contactProfileActivity = new Intent(ContactsActivity.this, ContactProfileActivity.class);
                    contactProfileActivity.putExtra("id", currentFriend.getId());
                    startActivityForResult(contactProfileActivity, ToolsForActivity.USER_CONTACT_PROFILE_ACTIVITY_REQUEST);
                }
            });

            return itemView;
        }
    }
}
