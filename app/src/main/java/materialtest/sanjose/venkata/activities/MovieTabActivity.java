package materialtest.sanjose.venkata.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import static materialtest.sanjose.venkata.constants.ApplicationConstants.RTConstants.*;
import materialtest.sanjose.venkata.fragments.FragmentBoxOffice;
import materialtest.sanjose.venkata.fragments.FragmentSearch;
import materialtest.sanjose.venkata.fragments.FragmentUpcoming;
import materialtest.sanjose.venkata.materialtest.R;


public class MovieTabActivity extends ActionBarActivity implements MaterialTabListener, View.OnClickListener{

    private MaterialTabHost tabHost;
    private ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_tab_library);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appBar);

        //telling to use my own action bar
        setSupportActionBar(toolbar);
        // go back page if click on arrow
        getSupportActionBar().setHomeButtonEnabled(true);
        // display back button (arrow)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);
            }
        });

        // insert all tabs from pagerAdapter data
        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            //uncomment below if want to use icons as tab layout
                            //.setIcon(adapter.getIcon(i))
                            //the below takes from the get page title beneath
                            .setIcon(adapter.getIcon(i))
                                    // the this below refers to materialtablistener
                            .setTabListener(this)
            );
        }

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.ic_action_new);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(imageView)
                //setting our own background color to float action
                .setBackgroundDrawable(R.drawable.selector_main_float)
                //.setBackgroundDrawable(R.color.favBlue)
                .build();

                        //sub menu
        //sort by name
        ImageView iconSortName = new ImageView(this);
        iconSortName.setImageResource(R.drawable.ic_action_alphabets);
        //sort by date
        ImageView iconSortDate = new ImageView(this);
        iconSortDate.setImageResource(R.drawable.ic_action_calendar);
        //sort by ratings
        ImageView iconSortRatings = new ImageView(this);
        iconSortRatings.setImageResource(R.drawable.ic_action_trending_orange);
        //sub menu
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        //add color to sub menu button background
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_sub_float));
        //construct the menu using the itembuilder
        SubActionButton buttonSortName = itemBuilder.setContentView(iconSortName).build();
        SubActionButton buttonSortDate = itemBuilder.setContentView(iconSortDate).build();
        SubActionButton buttonSortRatings = itemBuilder.setContentView(iconSortRatings).build();

        //add actions in the submenu by attaching to the main action button
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonSortName)
                .addSubActionView(buttonSortDate)
                .addSubActionView(buttonSortRatings)
                .attachTo(actionButton)
                .build();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_using_tab_library, menu);
        getMenuInflater().inflate(R.menu.menu_using_tab_library, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Hey you just hit " + item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.navigate) {
            startActivity(new Intent(this, SubActivity.class));
        }

        if(id == R.id.action_about) {
            Log.i("hey wow man,", "you want to know about of this?");
        }

        if(id == R.id.materialTab) {
            startActivity(new Intent(this, MainActivity.class));
        }
        // on the options menu we now have
        if(id == R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        viewPager.setCurrentItem(materialTab.getPosition());

    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    @Override
    public void onClick(View v) {

    }

    private class ViewPageAdapter extends FragmentStatePagerAdapter{

        //uncomment below when want to use icons for tablayout
        int icons[] = {R.drawable.ic_action_personal, R.drawable.ic_action_calendar,
                R.drawable.ic_action_important, R.drawable.ic_action_trending_orange,
                R.drawable.ic_action_home, R.drawable.ic_action_articles,
                R.drawable.ic_action_search, R.drawable.ic_action_trending_orange,
                R.drawable.ic_action_upcoming, R.drawable.ic_action_trending};

        //uncomment below for android svg items
        /*int icons[] = {R.drawable.vector_android, R.drawable.vector_android,
                R.drawable.vector_android};*/

        public ViewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        //return the fragment at that position
        @Override
        public Fragment getItem(int position) {
            //to decide which fragment to instantiate based on the position choosen
            Fragment fragment = null;

            switch (position){
                case MOVIES_SEARCH_RESULTS:
                    fragment = FragmentSearch.newInstance("", "");
                    break;
                case MOVIES_HITS:
                    fragment = FragmentBoxOffice.newInstance("", "");
                    break;
                case MOVIES_UPCOMING:
                    fragment = FragmentUpcoming.newInstance("", "");
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        // now write own implementation for setting text tab headers or icon tab headers

        // uncomment below for having text as tab heading
        /*@Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tabs)[position];
        }*/

        // uncomment below for having icons as tab heading
        private Drawable getIcon(int position) {
            return getResources().getDrawable(icons[position]);
        }
    }
}