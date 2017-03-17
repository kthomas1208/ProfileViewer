package com.dreammist.profileviewer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.dreammist.profileviewer.db.Photo;
import com.dreammist.profileviewer.db.Post;
import com.dreammist.profileviewer.db.User;
import com.facebook.Profile;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmList;

import static com.dreammist.profileviewer.R.id.container;

public class ProfileActivity extends AppCompatActivity {
    final String LOG_TAG = ProfileActivity.class.getName();

    private static final String SELECTED_ITEM = "selected_item";
    private int selectedItem;
    Realm realm;

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
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeDB();

        Profile profile = Profile.getCurrentProfile();

        /** We're assuming the database is loaded at this point **/
        // Set the name in the action bar
        final User user = realm.where(User.class).findFirst();
        String fullName = String.format("%s %s", user.getFirstName(), user.getLastName());
        if(profile == null)
            Toast.makeText(this, "Please login, first", Toast.LENGTH_SHORT).show();
        else
            fullName = profile.getFirstName() + " " + profile.getLastName();

        getSupportActionBar().setTitle(fullName);
        //TODO: What if profile is null AND the DB is empty?

        //Set the coverphoto
        ImageView coverPhoto = (ImageView)findViewById(R.id.cover_photo_view);
        Picasso.with(this).load(user.getCoverPhotoURL()).into(coverPhoto);


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

    private void initializeDB() {

        realm = Realm.getDefaultInstance();
        setRealmData();
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

    private void setRealmData() {

        // Set up posts
        RealmList<Post> posts = new RealmList<>();
        Post post1 = new Post();
        post1.setDate("March 1");
        post1.setText("This is post number 1");
        Post post2 = new Post();
        post2.setDate("March 2");
        post2.setText("This is post number 2");
        Post post3 = new Post();
        post3.setDate("March 3");
        post3.setText("This is post number 3");
        Post post4 = new Post();
        post4.setDate("March 4");
        post4.setText("This is post number 4");
        Post post5 = new Post();
        post5.setDate("March 5");
        post5.setText("This is post number 5");
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);
        posts.add(post5);

        // Set up photos
        RealmList<Photo> photos = new RealmList<>();
        Photo photo1 = new Photo();
        photo1.setUrl("http://i.imgur.com/PSDXjz6.jpg");
        Photo photo2 = new Photo();
        photo2.setUrl("http://i.imgur.com/KyAHf8v.png");
        Photo photo3 = new Photo();
        photo3.setUrl("http://i.imgur.com/CKbhPaq.jpg");
        Photo photo4 = new Photo();
        photo4.setUrl("http://i.imgur.com/4SsMsfW.png");
        Photo photo5 = new Photo();
        photo5.setUrl("http://i.imgur.com/tWL4YoO.jpg");
        Photo photo6 = new Photo();
        photo6.setUrl("http://i.imgur.com/l7NLF4p.jpg");
        Photo photo7 = new Photo();
        photo7.setUrl("http://i.imgur.com/JLWVcKx.jpg");
        Photo photo8 = new Photo();
        photo8.setUrl("http://i.imgur.com/kgmwSYv.jpg");
        Photo photo9 = new Photo();
        photo9.setUrl("http://i.imgur.com/pmYjiXk.jpg");
        photos.add(photo1);
        photos.add(photo2);
        photos.add(photo3);
        photos.add(photo4);
        photos.add(photo5);
        photos.add(photo6);
        photos.add(photo7);
        photos.add(photo8);
        photos.add(photo9);

        // Set up User
        User user = new User();
        user.setFirstName("Kevin");
        user.setLastName("Thomas");
        user.setCoverPhotoURL("http://i.imgur.com/kNk8uTd.jpg");
        user.setPosts(posts);
        user.setPhotos(photos);

        // Persist your data easily
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();


        //Prefs.with(this).setPreLoad(true);

    }
}
