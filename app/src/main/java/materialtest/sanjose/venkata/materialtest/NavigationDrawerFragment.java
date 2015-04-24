package materialtest.sanjose.venkata.materialtest;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends android.support.v4.app.Fragment {

    public static final String PREF_FILE_NAME = "testpref";
    //create a key for mUserLearnedDrawer
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;


    //when the drawer is opened, we will store the muserlearneddrawer in sharedpreference
    //when the screen is rotated and the drawer is drawn then we don't want the state to persis instead we want to check for this
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    private View containerView;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // false => ensure that if nothing available means the user has never opened the drawer
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        // if coming from screen rotation then a value is stored in shared preference then we should hide the drawer
        if(savedInstanceState!=null){
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }


    public void setUp(int FragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        //read the fragment id that is being sent as this is being used further while trying to auto open the drawer on
        //first usage , see line #89
        containerView = getActivity().findViewById(FragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            //called when completely opened
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //if this drawer is displayed ever before or not, if so then dont open auto
                // if the user has not opened the drawer any time then..
                if (!mUserLearnedDrawer) {
                    // convey that user has just opened the drawer and save to sp
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                // when the drawer is opened it opens on the activity behind it
                //invalidate optionsmenu will draw the action menu again=> once the drawer is opened, this ondraweropened
                //is triggered and now the navigation drawer is partially obstructing the action bar so it redraws
                //the menu!!
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
            // for 3 type of drawer (alpha less for tool bar)
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //super.onDrawerSlide(drawerView, slideOffset);
                //the offset increases from 0 to 1 as the drawer is opened based on slideOffset
                //Log.d("venkata", "offset" + slideOffset);
                if(slideOffset<0.6) {
                    toolbar.setAlpha(1 - slideOffset);
                }
            }
        };
        // the very first time
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    //if already drawer is saved in shared preference then we will not show the drawer else we will display the drawer
    // as this method is not dependent on any object, we can make it static!!
    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        //save to sharedpreference
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        //editor.commit();// commiting will hold other resource apply() is async and we cannot know the result
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        //save to sharedpreference
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }
}
