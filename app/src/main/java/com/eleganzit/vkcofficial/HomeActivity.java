package com.eleganzit.vkcofficial;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eleganzit.vkcofficial.fragment.AllVendorListFragment;
import com.eleganzit.vkcofficial.fragment.CompletedPoFragment;
import com.eleganzit.vkcofficial.fragment.NotificationandMessage;
import com.eleganzit.vkcofficial.fragment.PendingPOFragment;
import com.eleganzit.vkcofficial.fragment.ReportFragment;
import com.eleganzit.vkcofficial.fragment.ViewDefectsFragment;
import com.eleganzit.vkcofficial.util.UserLoggedInSession;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

  public   static TextView plan,entry,textTitle,tv_defects;
    LinearLayout tablayout;
    String notification="";
    UserLoggedInSession userLoggedInSession;
    private Fragment fragment = null;
    private FragmentManager fragmentManager;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        textTitle=findViewById(R.id.textTitle);
        userLoggedInSession = new UserLoggedInSession(HomeActivity.this);
        notification=getIntent().getStringExtra("notification");
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        toolbar.setOutlineProvider(ViewOutlineProvider.BACKGROUND);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        TextView text = (TextView) header.findViewById(R.id.official_name);
        text.setText("Welcome, "+userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_NAME));
        displayView(0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //textTitle.setText("PENDING PO");
        if (notification!=null && !(notification.isEmpty()))
        {
            Log.d("fsfs","0"+notification);
            if (notification.equalsIgnoreCase("yes"))
            {
                NotificationandMessage myPhotosFragment = new NotificationandMessage();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, myPhotosFragment, "TAG")
                        .commit();
            }
        }
        else
        {
            Log.d("fsfs","else");

            AllVendorListFragment myPhotosFragment = new AllVendorListFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, myPhotosFragment, "TAG")
                    .commit();
        }

    }
    private void displayView(int position) {
        fragment = null;
        String fragmentTags = "";
        switch (position) {
            case 0:
                fragment = new AllVendorListFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment, fragmentTags).commit();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);


        if (id == R.id.nav_po) {


            textTitle.setText("VENDORS");
            AllVendorListFragment myPhotosFragment = new AllVendorListFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, myPhotosFragment, "TAG")
                    .commit();



        }
        else
        if (id == R.id.na_complete) {


            textTitle.setText("COMPLETED PO");
            CompletedPoFragment myPhotosFragment = new CompletedPoFragment();
            getSupportFragmentManager().beginTransaction()

                    .addToBackStack("AllVendorListFragment")
                    .replace(R.id.container, myPhotosFragment, "TAG")

                    .commit();

        } else   if (id == R.id.viewdefects) {


            textTitle.setText("VIEW DEFECTS");
            ViewDefectsFragment myPhotosFragment = new ViewDefectsFragment();
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack("AllVendorListFragment")

                    .replace(R.id.container, myPhotosFragment, "TAG")

                    .commit();

        }  else if (id == R.id.report) {


            textTitle.setText("REPORT");
            ReportFragment myPhotosFragment = new ReportFragment();
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack("AllVendorListFragment")

                    .replace(R.id.container, myPhotosFragment, "TAG")

                    .commit();

        }
        else if (id == R.id.nav_noti) {


            textTitle.setText("NOTIFICATIONS & MESSAGES");
            NotificationandMessage myPhotosFragment = new NotificationandMessage();
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack("AllVendorListFragment")

                    .replace(R.id.container, myPhotosFragment, "TAG")

                    .commit();

        } else if (id == R.id.nav_logout) {


            userLoggedInSession.logoutUser();


        }


        return false;
    }
}
