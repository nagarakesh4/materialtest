package materialtest.sanjose.venkata.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import materialtest.sanjose.venkata.fragments.MainActivityFragment;
import materialtest.sanjose.venkata.fragments.NavigationDrawerFragment;
import materialtest.sanjose.venkata.materialtest.PagerActivity;
import materialtest.sanjose.venkata.materialtest.R;
import materialtest.sanjose.venkata.util.AnimationUtils;
import materialtest.sanjose.venkata.views.SlidingTabLayout;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        toolbar = (Toolbar) findViewById(R.id.appBar);
        //telling to use my own action bar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // implement the navigation drawer fragment in the mainactivity page
        NavigationDrawerFragment navigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        //send tool bar, icon changes and everything from here
        navigationDrawerFragment.setUp(R.id.fragment_navigation_drawer, (android.support.v4.widget.DrawerLayout) findViewById(R.id.drawerLayout), toolbar);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
        mTabs.setDistributeEvenly(true);//all the three tabs take equal space

        mTabs.setBackgroundColor(getResources().getColor(R.color.icons));
        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.accent));
        mTabs.setViewPager(mPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.navigate) {
            Toast.makeText(this, "Hey you just hit " + item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }*/
        if (id == R.id.navigate) {
            Log.i("sending mail", "now sending");
            startActivity(new Intent(this, MovieTabActivity.class));
            /*Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"nagarakesh4@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
            i.putExtra(Intent.EXTRA_TEXT, "body of email");
            try {
                Log.i("sending mail", Intent.EXTRA_EMAIL+"");
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }*/
        }
        if(id == R.id.action_about) {
            Log.i("hey wow man,", "you want to know about of this?");
        }
        if(id == R.id.materialTab) {
            startActivity(new Intent(this, UsingTabLibrary.class));
        }
        if(id == R.id.vectorTest) {
            startActivity(new Intent(this, VectorTestActivity.class));
        }
        if(id == R.id.materialPage){
            startActivity(new Intent(this, PagerActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    // the view pager should be constructed having several fragments using adapter
    class MyPagerAdapter extends FragmentPagerAdapter{
        String[] tabs;

        int icons[] = {R.drawable.ic_action_home, R.drawable.ic_action_articles, R.drawable.ic_action_personal};
        String[] tabText = getResources().getStringArray(R.array.tabs);

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabText = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            MainActivityFragment myFragment = MainActivityFragment.getInstance(position);
            return myFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // use spannable to display text and icon
            // construct an icon from the position
            Drawable drawable = getResources().getDrawable(icons[position]);
            drawable.setBounds(0,0,36,36);
            //image span object from the drawable
            ImageSpan imageSpan = new ImageSpan(drawable);
            //spannable string class
            SpannableString spannableString = new SpannableString(" ");//to make the icons work
            spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );

            return spannableString;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


}
