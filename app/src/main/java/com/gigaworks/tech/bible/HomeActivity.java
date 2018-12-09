package com.gigaworks.tech.bible;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.gigaworks.tech.bible.fragments.BibleFragment;
import com.gigaworks.tech.bible.fragments.CoachFragment;
import com.gigaworks.tech.bible.fragments.DailyFragmnet;
import com.gigaworks.tech.bible.fragments.ToolsFragment;
import com.gigaworks.tech.bible.fragments.YeshuFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<String> {

    private ImageView navHeader;
    private final String SOUND_URL = "http://biblebook.pilgrimsmanna.com/biblebook/android/getSound.php";
    private AppPreferences preferences;
    private NavigationView navigationView;
    private int checkId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        preferences = AppPreferences.getInstance(this);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState != null) {
            checkId = savedInstanceState.getInt("checked");
        }

        if (checkId != -1) {
            MenuItem item = navigationView.getMenu().findItem(checkId);
            onNavigationItemSelected(item);
        }

        getSupportLoaderManager().initLoader(0, null, this).forceLoad();

        View header = navigationView.getHeaderView(0);
        navHeader = header.findViewById(R.id.tvHeaderIcon);

        navHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                Fragment fragment = new MainFragment();
//                fragmentTransaction.replace(R.id.fl_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//                drawer.closeDrawer(GravityCompat.START);
//                int size = navigationView.getMenu().size();
//                for (int i = 0; i < size; i++) {
//                    navigationView.getMenu().getItem(i).setChecked(false);
//                }
                checkId = -1;
            }
        });


        if(checkId == -1) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment = new BibleFragment();
            fragmentTransaction.replace(R.id.fl_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finishAffinity();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.bible) {
            // Handle the camera action
            Fragment fragment = new BibleFragment();
            fragmentTransaction.replace(R.id.fl_container, fragment);
        } else if (id == R.id.nav_daily) {
            Fragment fragment = new DailyFragmnet();
            fragmentTransaction.replace(R.id.fl_container, fragment);
        } else if (id == R.id.nav_yeshu) {
            Fragment fragment = new YeshuFragment();
            fragmentTransaction.replace(R.id.fl_container, fragment);
        } else if (id == R.id.nav_tools) {
            Fragment fragment = new ToolsFragment();
            fragmentTransaction.replace(R.id.fl_container, fragment);
        } else if (id == R.id.nav_coaching) {
            Fragment fragment = new CoachFragment();
            fragmentTransaction.replace(R.id.fl_container, fragment);
        } else if(id == R.id.nav_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "PilgrimsManna");
            String msg = "Hey, checkout this Bible app. Go to this link " +
                    "to download this app now.\n\n";
            intent.putExtra(Intent.EXTRA_TEXT, msg);
            startActivity(Intent.createChooser(intent, "Choose one"));
        } else if(id == R.id.nav_contact) {
            Intent intent = new Intent(this,ContactUs.class);
            startActivity(intent);
        } else if(id == R.id.nav_about) {
            Intent intent = new Intent(this,AboutActivity.class);
            startActivity(intent);
        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new NetworkTask(HomeActivity.this, SOUND_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {

        preferences.setStringPreference(AppPreferences.APP_SOUND_RESPONSE, s);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (navigationView.getCheckedItem() != null) {
            outState.putInt("checked", navigationView.getCheckedItem().getItemId());
        } else {
            outState.putInt("checked", checkId);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        checkId = savedInstanceState.getInt("checked");
    }
}
