package com.dreammist.profileviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private Profile profile;

    private TextView displayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayText = (TextView) findViewById(R.id.textView);
        profile = Profile.getCurrentProfile();
        if(profile != null) displayText.setText("Hey there, " + profile.getFirstName() + "!");

        final LoginButton loginButton = (LoginButton) findViewById(R.id.loginButton);
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {

                    private ProfileTracker mProfileTracker;

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        //displayText = (TextView) findViewById(R.id.textView);
                        if(Profile.getCurrentProfile() == null) {
                            mProfileTracker = new ProfileTracker() {
                                @Override
                                protected void onCurrentProfileChanged(final Profile oldProfile, final Profile currentProfile) {
                                    // currentProfile is the new profile
                                    Toast.makeText(MainActivity.this, "Hey there, " + currentProfile.getFirstName() + "!", Toast.LENGTH_SHORT).show();
                                    displayText.setText("Hey there, " + currentProfile.getFirstName() + "!");
                                    mProfileTracker.stopTracking();
                                }
                            };
                        }
                        else {
                            Profile profile = Profile.getCurrentProfile();
                            Toast.makeText(MainActivity.this, "Welcome, " + profile.getFirstName() + "!", Toast.LENGTH_SHORT).show();
                            displayText.setText("Welcome, " + profile.getFirstName() + "!");
                        }

                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(callbackManager.onActivityResult(requestCode, resultCode, data)) return;

    }

    public void launchProfileActivity(View view) {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}
