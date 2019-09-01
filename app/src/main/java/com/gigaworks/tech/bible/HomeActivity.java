package com.gigaworks.tech.bible;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.gigaworks.tech.bible.fragments.BibleFragment;
import com.gigaworks.tech.bible.viewmodel.HomeViewModel;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AppPreferences preferences;
    private NavigationView navigationView;
    private int checkId = -1;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //setting toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialisations
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navigationView = findViewById(R.id.nav_view);
        preferences = AppPreferences.getInstance(this);
        HomeViewModel model = ViewModelProviders.of(this).get(HomeViewModel.class);
        String bookResponse = preferences.getStringPreference(AppPreferences.APP_BOOK_RESPONSE);
        String soundResponse = preferences.getStringPreference(AppPreferences.APP_SOUND_RESPONSE);



        //adding drawer listener
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //setting navigation bar
        navigationView.setNavigationItemSelectedListener(this);

        //Load book data from internet
        model.getBookData(bookResponse)
                .observe(this, data ->
                        preferences.setStringPreference(AppPreferences.APP_BOOK_RESPONSE, data));

        //Load sound data from internet
        model.getSoundData(soundResponse)
                .observe(this, data ->
                        preferences.setStringPreference(AppPreferences.APP_SOUND_RESPONSE, data));


        if (checkId == -1) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment = new BibleFragment();
            fragmentTransaction.replace(R.id.fl_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finishAffinity();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.bible) {
            Fragment fragment = new BibleFragment();
            fragmentTransaction.replace(R.id.fl_container, fragment);
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "PilgrimsManna");
            String msg = "Hey, checkout this really cool app called PilgrimsManna. Go to this link " +
                    "to download this app now.\n\n" + Constants.getPlayStoreLink();
            intent.putExtra(Intent.EXTRA_TEXT, msg);
            startActivity(Intent.createChooser(intent, "Choose one"));
        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(this, ContactUs.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
