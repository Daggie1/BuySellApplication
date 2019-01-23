package com.example.anonymous.tomatoesapp.UI.activities;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.anonymous.tomatoesapp.R;

import com.example.anonymous.tomatoesapp.UI.fragments.MapsActivity;
import com.example.anonymous.tomatoesapp.UI.fragments.MyTomatoesFragment;
import com.example.anonymous.tomatoesapp.UI.fragments.Pests_Diseases_Fragment;
import com.example.anonymous.tomatoesapp.UI.fragments.RecentFragment;
import com.example.anonymous.tomatoesapp.adapters.ViewPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {



    private ViewPager mViewPager;
    private TabLayout mtabLayout;
   private AppBarLayout mappBarLaout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("OurServices");
        setSupportActionBar(toolbar);

mappBarLaout=(AppBarLayout) findViewById(R.id.appbar);
mtabLayout= (TabLayout) findViewById(R.id.tabs);
        // Set up the ViewPagerAdapter with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);


        ViewPagerAdapter mAdapter=new ViewPagerAdapter(getSupportFragmentManager());

        mAdapter.addFragment(new RecentFragment(),"MarketPlace");
        mAdapter.addFragment(new MyTomatoesFragment(),"My Tomatoes");
        mAdapter.addFragment(new Pests_Diseases_Fragment(),"More");
        mViewPager.setAdapter(mAdapter);
        mtabLayout.setupWithViewPager(mViewPager);






    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handles a click on the menu option to get a place.
     * @param item The menu item to handle.
     * @return Boolean.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_product) {
            startActivity(new Intent(HomeActivity.this, Add_Update_Product_Activity.class));
        }
        if (item.getItemId() == R.id.action_logout) {
FirebaseAuth.getInstance().signOut();
startActivity(new Intent(HomeActivity.this,MainActivity.class));
        }
        return true;
    }
}
