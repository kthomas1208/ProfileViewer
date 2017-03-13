package com.dreammist.profileviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

public class ProfileActivity extends AppCompatActivity {
    final String LOG_TAG = ProfileActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    }
}