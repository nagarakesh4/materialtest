package materialtest.sanjose.venkata.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import materialtest.sanjose.venkata.adapters.InformationAdapter;
import materialtest.sanjose.venkata.materialtest.R;
import materialtest.sanjose.venkata.model.Information;


/**
 * A simple {@link Fragment} subclass.
 */
/*
    STEPS TO HANDLE THE RECYCLER CLICK
    1 Create a class that EXTENDS RecylcerView.OnItemTouchListener
    2 Create an interface inside that class that supports click and long click and indicates the View that was clicked and the position where it was clicked
    3 Create a GestureDetector to detect ACTION_UP single tap and Long Press events
    4 Return true from the singleTap to indicate your GestureDetector has consumed the event.
    5 Find the childView containing the coordinates specified by the MotionEvent and if the childView is not null and the listener is not null either, fire a long click event
    6 Use the onInterceptTouchEvent of your RecyclerView to check if the childView is not null, the listener is not null and the gesture detector consumed the touch event
    7 if above condition holds true, fire the click event
    8 return false from the onInterceptedTouchEvent to give a chance to the childViews of the RecyclerView to process touch events if any.
    9 Add the onItemTouchListener object for our RecyclerView that uses our class created in step 1
     */
public class MovieDrawerFragment extends android.support.v4.app.Fragment{

    private RecyclerView recyclerView;
    public static final String PREF_FILE_NAME = "testpref";
    //create a key for mUserLearnedDrawer
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private InformationAdapter informationAdapter;

    //when the drawer is opened, we will store the muserlearneddrawer in sharedpreference
    //when the screen is rotated and the drawer is drawn then we don't want the state to persis instead we want to check for this
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    private View containerView;

    public MovieDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // false => ensure that if nothing available means the user has never opened the drawer
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        // if coming from screen rotation then a value is stored in shared preference then we should hide the drawer
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_movie_drawer, container, false);
        //recyclerview implementation
        recyclerView = (RecyclerView) layout.findViewById(R.id.movieDrawerList);

        //initialise the information adapter with the data from getData() - static
        informationAdapter = new InformationAdapter(getActivity(), getData());
        //set the adapter on the recycler view
        recyclerView.setAdapter(informationAdapter);
        //set the layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText(getActivity(), "onClick "+position, Toast.LENGTH_SHORT).show();
                //close the drawer on click of any items in the recycler view of the movie
                //navigation bar
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }

            @Override
            public void onLongClick(View view, int position) {
                //Toast.makeText(getActivity(), "onlongClick "+position, Toast.LENGTH_SHORT).show();
            }
        }));
        return layout;
    }

    /*public static List<Information> getData() {
        // load dummy data as the navigation drawer recommends only static data
        // but not dynamic content
        List<Information> dataInformation = new ArrayList<>();
        int[] icons = {R.drawable.ic_number1, R.drawable.ic_number2, R.drawable.ic_number3,
                R.drawable.ic_number4, R.drawable.ic_next, R.drawable.ic_number1, R.drawable.ic_number2, R.drawable.ic_number3,
                R.drawable.ic_number4, R.drawable.ic_next, R.drawable.ic_number2};
        String[] titles = {"Venkata", "Adobe", "SJSU", "India", "San Jose", "Visakhapatnam", "Google",
                "Yahoo", "eBay", "Apple", "PayPal"};

        *//*for(int i=0;i<titles.length && i<icons.length; i++) {*//*
        for(int i=0; i<100; i++){
            Information currentInformation = new Information();
            //currentInformation.iconId = icons[i];
            currentInformation.iconId = icons[i%icons.length];
            //currentInformation.title = titles[i];
            currentInformation.title = titles[i%icons.length];
            dataInformation.add(currentInformation);
        }
        return dataInformation;
    }*/

    public List<Information> getData() {
        // load dummy data as the navigation drawer recommends only static data
        // but not dynamic content
        List<Information> dataInformation = new ArrayList<>();
        int[] icons = {R.drawable.ic_action_search_orange, R.drawable.ic_action_trending_orange, R.drawable.ic_action_upcoming_orange};
        String[] titles = getResources().getStringArray(R.array.movieDrawerTabs);

        /*for(int i=0;i<titles.length && i<icons.length; i++) {*/
        for(int i=0; i<titles.length; i++){
            Information currentInformation = new Information();
            //currentInformation.iconId = icons[i];
            currentInformation.iconId = icons[i];
            //currentInformation.title = titles[i];
            currentInformation.title = titles[i];
            dataInformation.add(currentInformation);
        }
        return dataInformation;
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
            /*@Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //super.onDrawerSlide(drawerView, slideOffset);
                //the offset increases from 0 to 1 as the drawer is opened based on slideOffset
                //Log.d("venkata", "offset" + slideOffset);
                if(slideOffset<0.6) {
                    toolbar.setAlpha(1 - slideOffset);
                }
            }*/
        };
        // the very first time
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        // for showing the burger icon on swype of the navigation drawerk
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    //step 1
    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private GestureDetector gestureDetector;
        private ClickListener clickListener;
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            Log.i("constructor", "invoked");
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                // these two methods cannot be invoked auto, we have to invoke manually like
                // gestureDectector.onTouchEvent(e)
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    Log.i("single tapup", "touching " + e);
                    // to make gesture detector handle single tap up - step 4
                    return true;
                }

                // step -5 find child view which performed long press - its coordinates
                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    //child must not be null and click listener must not be null
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                    super.onLongPress(e);
                }
            });
        }
        // single touch is taken care by the onintercepttouch event
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());

            if(child!=null && clickListener!=null && gestureDetector.onTouchEvent(e)){
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            //further processing if any should take place inside the child view of recycler view
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            // CALL THE GESTURE DETECTORS ONTOUCHEVENT
        }
    }
    //step 2
    public static interface ClickListener{
        // the view that was clicked and the position where that particular child view was clicked in
        // the recycler view
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
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
