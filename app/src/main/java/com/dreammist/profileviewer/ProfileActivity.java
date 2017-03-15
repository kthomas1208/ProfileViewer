package com.dreammist.profileviewer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.Profile;

import static com.dreammist.profileviewer.R.id.container;

public class ProfileActivity extends AppCompatActivity {
    final String LOG_TAG = ProfileActivity.class.getName();

    private static final String SELECTED_ITEM = "selected_item";
    private int selectedItem;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            selectFragment(item);
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Profile profile = Profile.getCurrentProfile();

        if(profile == null) {
            Toast.makeText(this, "Please login, first", Toast.LENGTH_SHORT).show();
        }
        else {
            String fullName = profile.getFirstName() + " " + profile.getLastName();
            getSupportActionBar().setTitle(fullName);
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Set the fragment when starting up the activity
        MenuItem item;
        if (savedInstanceState != null) {
            selectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
            item = navigation.getMenu().findItem(selectedItem);
        } else {
            item = navigation.getMenu().getItem(0);
        }
        selectFragment(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, selectedItem);
        super.onSaveInstanceState(outState);
    }

    private void selectFragment(MenuItem item) {
        Fragment frag = null;
        switch (item.getItemId()) {
            case R.id.navigation_posts:
                frag = PostsFragment.newInstance("", "");
                break;
            case R.id.navigation_photos:
                frag = PhotosFragment.newInstance("", "");
                break;
        }

        // update selected item
        selectedItem = item.getItemId();

        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(container, frag, frag.getTag());
            ft.commit();
        }
    }
}
