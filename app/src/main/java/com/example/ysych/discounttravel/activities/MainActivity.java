package com.example.ysych.discounttravel.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;

import com.example.ysych.discounttravel.R;
import com.example.ysych.discounttravel.data.HelperFactory;
import com.example.ysych.discounttravel.fragments.CountryFragment;
import com.example.ysych.discounttravel.model.Country;
import com.example.ysych.discounttravel.model.Tour;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final long DRAWER_CLOSE_DELAY_MS = 250;
    private static final String NAV_ITEM_ID = "navItemId";

    private final Handler mDrawerActionHandler = new Handler();
    private DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private int mNavItemId;
    private List<Country> countries;
    private ExpandableListView expandableListView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);

        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>() {{
            add(new HashMap<String, String>() {{
                put(getString(R.string.root_name), getString(R.string.drawer_menu_title));
            }});
        }};
        List<List<Map<String, String>>> listOfChildGroups = new ArrayList<>();

        List<Map<String, String>> childGroupForFirstGroupRow = new ArrayList<>();
        List<Tour> systemPages = new ArrayList<>();
        try{
            systemPages = HelperFactory.getHelper().getTourDAO().queryForEq(Tour.CAT_ID, 2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Tour oneSystemPage : systemPages){
            HashMap<String, String> child = new HashMap<>();
            child.put(getResources().getString(R.string.child_name), oneSystemPage.getTitle().substring(0, 15));
            childGroupForFirstGroupRow.add(child);
        }
        listOfChildGroups.add(childGroupForFirstGroupRow);

        LinearLayout headerLayout = (LinearLayout) navigationView.getHeaderView(0);
        expandableListView = (ExpandableListView) headerLayout.findViewById(R.id.drawer_header_expandable_list_view);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                ViewGroup.LayoutParams layoutParams = expandableListView.getLayoutParams();
                layoutParams.height = expandableListView.getAdapter().getCount() * expandableListView.getHeight() + expandableListView.getAdapter().getCount();
                expandableListView.setLayoutParams(layoutParams);
                expandableListView.refreshDrawableState();
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                ViewGroup.LayoutParams layoutParams = expandableListView.getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                expandableListView.setLayoutParams(layoutParams);
                expandableListView.refreshDrawableState();
            }
        });

        expandableListView.setAdapter(new SimpleExpandableListAdapter(
                this,
                groupData,
                R.layout.expandablelistview_parent,
                new String[]{getString(R.string.root_name)},
                new int[]{R.id.expandable_list_view_parent},
                listOfChildGroups,
                R.layout.expandablelistview_child,
                new String[]{getString(R.string.child_name), getString(R.string.child_name)},
                new int[]{R.id.expandable_list_view_child, android.R.id.text2}
        ));

        try {
            countries = HelperFactory.getHelper().getCountryDAO().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(Country country : countries){
            navigationView.getMenu().add(0, country.getId(), 0, country.getTitle().toUpperCase());
        }

        // load saved navigation state if present
        if (null == savedInstanceState) {
            mNavItemId = R.id.all_tours;
        } else {
            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        }

        navigationView.setNavigationItemSelectedListener(this);

        // set up the hamburger icon to open and close the drawer
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open,
                R.string.close);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mDrawerToggle.syncState();

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(getSupportActionBar() != null){
                    if(getSupportFragmentManager().getBackStackEntryCount() > 0){
                        mDrawerToggle.setDrawerIndicatorEnabled(false);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }
                    else {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        mDrawerToggle.setDrawerIndicatorEnabled(true);
                    }
                }
            }
        });

        navigate(mNavItemId);
    }

    private void navigate(int itemId) {
        // perform the actual navigation logic, updating the main content fragment etc
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new CountryFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(CountryFragment.COUNTRY_CODE, itemId);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {
        // update highlighted item in the navigation menu
        mNavItemId = menuItem.getItemId();
        mCollapsingToolbarLayout.setTitle(menuItem.getTitle());

        // allow some time after closing the drawer before performing real navigation
        // so the user can see what is happening
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerActionHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(menuItem.getItemId());
            }
        }, DRAWER_CLOSE_DELAY_MS);
        return true;
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.support.v7.appcompat.R.id.home) {
            return mDrawerToggle.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, mNavItemId);
    }
}