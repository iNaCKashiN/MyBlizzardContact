package com.unice.myblizzardcontact.activities.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
 * This class represent an activity for Show a group.
 */
public class GroupActivity extends AppCompatActivity {
    //-----------------------------------------------
    //Fields

    // Represent this application
    private MyBlizzardContact application;

    // Represent the group of this activity.
    private Group currentGroup;

    //-----------------------------------------------
    //Fields Android

    // Represent the toolbar of this activity.
    private Toolbar toolbar;

    // Represent the name of the group.
    private TextView nameGroup;

    // Represent if the group is favorite.
    private ImageView favoriteIcon;

    // Represent if the user want send a sms to the group.
    private ImageView sendSmsIcon;

    // Represent the list of the member.
    private ListView listMember;

    //-----------------------------------------------
    //Create

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Setup application
        this.application = MyBlizzardContact.getApp();

        // Get Group
        Bundle extras = getIntent().getExtras();
        for (Group group : application.getDefaultGroups()) {
            if (extras.getInt("id") == group.getId()) {
                currentGroup = group;
                break;
            }
        }

        // Setup the theme
        SetupTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

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
            if (requestCode == ToolsForActivity.SMS_GROUP_ACTIVITY_REQUEST) {
                Toast.makeText(getApplicationContext(), "Success : SMS send.", Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.menu_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // TODO: 22/05/2016 Launch the group setting for a future maj of this application.
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
        this.nameGroup = (TextView) findViewById(R.id.name_group_id);
        this.nameGroup.setText(currentGroup.getName());
        this.favoriteIcon = (ImageView) findViewById(R.id.favorite_icon_id);
        if (currentGroup.getFavorite()) {
            this.favoriteIcon.setImageResource(R.drawable.heart_full_icons);
        } else {
            this.favoriteIcon.setImageResource(R.drawable.hearth_empty_icons);
        }

        this.sendSmsIcon = (ImageView) findViewById(R.id.send_message_icon_id);
        this.listMember = (ListView) findViewById(R.id.list_member_id);
    }

    /**
     * Setup all event.
     */
    private void setupEvent() {
        sendSmsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent smsGroupActivity = new Intent(GroupActivity.this, SmsGroupActivity.class);
                smsGroupActivity.putExtra("id", currentGroup.getId());
                startActivityForResult(smsGroupActivity, ToolsForActivity.SMS_GROUP_ACTIVITY_REQUEST);
            }
        });
    }

    /**
     * Populate the list view.
     */
    private void populateListView() {
        ArrayAdapter<Friend> adapter = new MyListAdapter();
        listMember.setAdapter(adapter);
    }

    /**
     * Created by KashiN (ALLENA Johann),
     * on 22/05/2016,
     * for My Blizzard Contact.
     * <p/>
     * --
     * <p/>
     * This class represent a adapter for the list view on the group activity.
     */
    private class MyListAdapter extends ArrayAdapter<Friend> {
        //-----------------------------------------------
        //Fields

        // Represent the hearth if the member is favorite
        private ImageView favoriteIcon;

        // Represent the name of the member.
        private TextView nameMember;

        //-----------------------------------------------
        //Constructor

        public MyListAdapter() {
            super(GroupActivity.this, R.layout.item_group, currentGroup.getMembers());
        }

        //-----------------------------------------------
        //Methods

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_group, parent, false);
            }

            // Get the good member.
            Friend currentFriend = currentGroup.getMembers().get(position);

            // Fill the view
            this.favoriteIcon = (ImageView) itemView.findViewById(R.id.item_favorite_id);
            this.nameMember = (TextView) itemView.findViewById(R.id.item_name_id);

            if (currentFriend.isFavorite()) {
                this.favoriteIcon.setImageResource(R.drawable.heart_full_icons);
            } else {
                this.favoriteIcon.setImageResource(R.drawable.hearth_empty_icons);
            }

            this.nameMember.setText(currentFriend.getName());
            return itemView;
        }
    }
}


