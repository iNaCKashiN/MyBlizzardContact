package com.unice.myblizzardcontact.activities.group;

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
import com.unice.myblizzardcontact.models.Group;


/**
 * Created by KashiN (ALLENA Johann), DRAVET Jean-BAPTISTE,
 * on 14/05/2016,
 * for My Blizzard Contact.
 * <p/>
 * --
 * <p/>
 * This class represent an activity for show all group.
 */
public class GroupsActivity extends AppCompatActivity {
    //-----------------------------------------------
    //Fields

    // Represent this application
    private MyBlizzardContact application;

    //-----------------------------------------------
    //Fields Android

    // Represent the toolbar of this activity.
    private Toolbar toolbar;

    // Represent the list of groups
    private ListView groupsList;

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
        setContentView(R.layout.activity_groups);

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
            if (requestCode == ToolsForActivity.GROUP_ACTIVITY_REQUEST) {
                // TODO: 29/05/2016 Get the result for a future activity the result on the group activity.
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
        getMenuInflater().inflate(R.menu.menu_groups, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            // TODO: 22/05/2016 Launch for a future maj of this application the new group.
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
        this.groupsList = (ListView) findViewById(R.id.groups_list_id);
        this.sortName = (TextView) findViewById(R.id.name_short_id);
        this.blockSortOption = (FrameLayout) findViewById(R.id.block_sort_option_id);
    }

    /**
     * Setup all event.
     */
    private void setupEvent() {
    }

    /**
     * Populate the list view.
     */
    private void populateListView() {
        ArrayAdapter<Group> adapter = new MyListAdapter();
        groupsList.setAdapter(adapter);
    }

    /**
     * Created by KashiN (ALLENA Johann),
     * on 22/05/2016,
     * for My Blizzard Contact.
     * <p/>
     * --
     * <p/>
     * This class represent a adapter for the list view on the groups activity.
     */
    private class MyListAdapter extends ArrayAdapter<Group> {
        //-----------------------------------------------
        //Fields

        // Represent the content layout
        private FrameLayout contentGroup;

        // Represent the hearth if the group is favorite
        private ImageView favoriteImage;

        // Represent the name of the group.
        private TextView nameGroup;

        // Represent the number of people in the the group.
        private TextView nbGroupPeople;

        //-----------------------------------------------
        //Constructor

        public MyListAdapter() {
            super(GroupsActivity.this, R.layout.item_groups, application.getDefaultGroups());
        }

        //-----------------------------------------------
        //Methods

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_groups, parent, false);
            }

            // Get the good group.
            final Group currentGroup = application.getDefaultGroups().get(position);

            // Fill the view
            favoriteImage = (ImageView) itemView.findViewById(R.id.item_favorite_id);
            contentGroup = (FrameLayout) itemView.findViewById(R.id.content_group_id);
            nameGroup = (TextView) itemView.findViewById(R.id.item_name_id);
            nbGroupPeople = (TextView) itemView.findViewById(R.id.item_number_id);

            if (currentGroup.getFavorite()) {
                favoriteImage.setImageResource(R.drawable.heart_full_icons);
            } else {
                favoriteImage.setImageResource(R.drawable.hearth_empty_icons);
            }

            nameGroup.setText(currentGroup.getName());

            String lol = currentGroup.getMembers().size() + " ";
            nbGroupPeople.setText(lol);

            // Setup event click
            contentGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent contactProfileActivity = new Intent(GroupsActivity.this, GroupActivity.class);
                    contactProfileActivity.putExtra("id", currentGroup.getId());
                    startActivityForResult(contactProfileActivity, ToolsForActivity.GROUP_ACTIVITY_REQUEST);
                }
            });

            return itemView;
        }
    }

}
