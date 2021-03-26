package com.example.hunted;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hunted.repeatingtask.RepeatingTask;
import com.example.hunted.repeatingtask.RepeatingTaskName;
import com.example.hunted.repeatingtask.RepeatingTaskService;
import com.google.android.material.navigation.NavigationView;

import java.util.Observable;
import java.util.Observer;

public class ThievesActivity extends AppCompatActivity implements Observer {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thieves);
        doBindService();

        //Set toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(getResources().getColor(R.color.thieves));

        //Set initial fragment
        setFragment(new ThievesFragmentLocations());
        setTitle("Locaties");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_thieves);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(navigationView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_locations:
                fragmentClass = ThievesFragmentLocations.class;
                break;
            case R.id.nav_scanner:
                fragmentClass = ThievesFragmentScanner.class;
                break;
            default:
                fragmentClass = ThievesFragmentLocations.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setFragment(fragment);

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        drawerLayout.closeDrawers();
    }

    private void setFragment(Fragment fragment){
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mainContentThieves, fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //region Service code

    @Override
    public void update(Observable observable, Object o) {
        //runOnUiThread(() -> Toast.makeText(ThievesActivity.this, "Observable update: " + o.toString(), Toast.LENGTH_SHORT).show());
    }

    // Clean service binding
    private boolean mShouldUnbind;
    private RepeatingTaskService mBoundService;

    private final ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mBoundService = ((RepeatingTaskService.LocalBinder)service).getService();

            //Add repeatingTask.
            RepeatingTask repeatingTask = new RepeatingTask(RepeatingTaskName.CHECK_ARRESTED, 3000);
            repeatingTask.addObserver(ThievesActivity.this);
            mBoundService.addRepeatingTask(repeatingTask);
        }

        public void onServiceDisconnected(ComponentName className) {
            mBoundService = null;
        }
    };

    void doBindService() {
        if (bindService(new Intent(ThievesActivity.this, RepeatingTaskService.class), mConnection, Context.BIND_AUTO_CREATE)) {
            mShouldUnbind = true;
        }
    }

    void doUnbindService() {
        if (mShouldUnbind) {
            unbindService(mConnection);
            mShouldUnbind = false;
        }
    }

    //endregion

}