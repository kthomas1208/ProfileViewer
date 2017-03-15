package com.dreammist.profileviewer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    final String LOG_TAG = ProfileActivity.class.getName();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_posts:
                    Toast.makeText(ProfileActivity.this, "Posts", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_photos:
                    Toast.makeText(ProfileActivity.this, "Photos", Toast.LENGTH_SHORT).show();
                    return true;

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ProfilePictureView profilePictureView = (ProfilePictureView) findViewById(R.id.profile_pic);
        Profile profile = Profile.getCurrentProfile();

        if(profile == null) {
            Toast.makeText(this, "Please login, first", Toast.LENGTH_SHORT).show();

        }
        else {
            profilePictureView.setProfileId(profile.getId());
            String fullName = profile.getFirstName() + " " + profile.getLastName();
            getSupportActionBar().setTitle(fullName);
            Log.v(LOG_TAG, fullName + " " + profile.getId());

        }

        ArrayList<String> values = new ArrayList<String>();
        values.add("Hello, Status 1");
        values.add("Hey there, Status 2");
        values.add(("Hi! Status 3"));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(values);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
    }
}
