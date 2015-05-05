package materialtest.sanjose.venkata.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static materialtest.sanjose.venkata.constants.ApplicationConstants.RTConstants.*;

import materialtest.sanjose.venkata.adapters.AdapterBoxOffice;
import materialtest.sanjose.venkata.logging.Logger;
import materialtest.sanjose.venkata.materialtest.R;
import materialtest.sanjose.venkata.model.Movie;
import materialtest.sanjose.venkata.network.VolleySingleton;
import materialtest.sanjose.venkata.util.MovieSorter;
import materialtest.sanjose.venkata.util.SortListener;

import static materialtest.sanjose.venkata.util.MovieResponseKeys.EndpointBoxOffice.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBoxOffice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBoxOffice extends Fragment implements SortListener, SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String STATE_MOVIES = "state_movies";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //volley variables
    private RequestQueue requestQueue;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;

    //construct movie model array list
    private ArrayList<Movie> moviesList = new ArrayList<>();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    //recyclerview
    private RecyclerView listMovieHits;
    private TextView textVolleyError;

    //create a adapterboxoffice object to have the movies list
    private AdapterBoxOffice adapterBoxOffice;

    //MovieSorter
    private MovieSorter movieSorter;

    //swipe refresh layout
    private SwipeRefreshLayout mSwipeRefreshLayout;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBoxOffice.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBoxOffice newInstance(String param1, String param2) {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //save instance when the screen is rotated
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // to save all states of movies
        outState.putParcelableArrayList(STATE_MOVIES, moviesList);
    }

    // prepare the request URL
    public static String getRequestUrl(int limit) {
        return COMBO_BO_RT + "&limit=" + limit;
    }

    public FragmentBoxOffice() {
        // Required empty public constructor
        movieSorter = new MovieSorter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //initialize the volley
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

        //StringRequest, JsonObject, JsonArray, Image are the various requests
        // in volley there are many requests, one of such is json object request
        // as response is in json format prepare volley json object request
        //sendJsonRequest();
    }

    private void sendJsonRequest() {

        JsonObjectRequest boxOfficeRequest = new JsonObjectRequest(Request.Method.GET, getRequestUrl(40),
                (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                textVolleyError.setVisibility(View.GONE);
                //Logger.showToast(getActivity(), response.toString());
                moviesList = parseJSONResponse(response);
                //set the data in adapter box office
                adapterBoxOffice.setMoviesList(moviesList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleVolleyError(error);
            }
        });

        //add this request to the volley request queue
        requestQueue.add(boxOfficeRequest);
    }

    private void handleVolleyError(VolleyError error) {
        //Logger.showToast(getActivity(), error.getMessage().toString());
        //by default the text volley error is gone
        textVolleyError.setVisibility(View.VISIBLE);
        Logger.showLogInfo(error+"");
        if(error instanceof NetworkError){
            textVolleyError.setText(R.string.error_connection);
        }else if(error instanceof AuthFailureError){
            textVolleyError.setText(R.string.error_auth);
        }else if(error instanceof ParseError){
            textVolleyError.setText(R.string.error_parse);
        }else if(error instanceof ServerError){
            textVolleyError.setText(R.string.error_server);
        }else if(error instanceof NoConnectionError){
            textVolleyError.setText(R.string.error_network);
        }else{
            textVolleyError.setText(R.string.error_server);
        }
    }

    private ArrayList<Movie> parseJSONResponse(JSONObject response) {
        ArrayList<Movie> moviesList = new ArrayList<>();
        if (response != null && response.length() > 0) {
            // the response is a movies object in json array format
            try {
                StringBuilder dataMovies = new StringBuilder();
                if (response.has(KEY_MOVIES)) {
                    JSONArray arrayMovies = response.getJSONArray(KEY_MOVIES);
                    for (int i = 0; i < arrayMovies.length(); i++) {

                    long id = 0;
                    String title = NOT_AVAILABLE;
                    String releaseDate = NOT_AVAILABLE;
                    int audienceScore = -1;
                    String synopsis = NOT_AVAILABLE;
                    String urlThumbnail = NOT_AVAILABLE;
                    Date date = null;
                    //inside this movie json array, we get json objects of each movie
                    JSONObject currentMovie = arrayMovies.getJSONObject(i);

                    //get the movie id
                    if (currentMovie.has(KEY_ID) && !currentMovie.isNull(KEY_ID)) {
                        id = currentMovie.getLong(KEY_ID);
                    }

                    //get the movie name
                    if ((currentMovie.has(KEY_TITLE)) && !currentMovie.isNull(KEY_TITLE)) {
                        title = currentMovie.getString(KEY_TITLE);
                    }

                    //get the release dates
                    if (currentMovie.has(KEY_RELEASE_DATES) && !currentMovie.isNull(KEY_RELEASE_DATES)) {
                        JSONObject objectReleaseDates = currentMovie.getJSONObject(KEY_RELEASE_DATES);

                        if (objectReleaseDates != null &&
                                objectReleaseDates.has(KEY_THEATER) &&
                                !objectReleaseDates.isNull(KEY_THEATER)) {
                            releaseDate = objectReleaseDates.getString(KEY_THEATER);
                        }
                    }

                    // get the audience score
                    if (currentMovie.has(KEY_RATINGS) && !currentMovie.isNull(KEY_RATINGS)) {
                        JSONObject objectRatings = currentMovie.getJSONObject(KEY_RATINGS);
                        if (objectRatings != null &&
                                objectRatings.has(KEY_AUDIENCE_SCORE) &&
                                !objectRatings.isNull(KEY_AUDIENCE_SCORE)) {
                            audienceScore = objectRatings.getInt(KEY_AUDIENCE_SCORE);
                            //should be only 0 to 100
                            //set text view (hide initially) if rating available then show stars
                            // if rating na then set the text view to be shown
                        }
                    }

                    //get the synopsis
                    if (currentMovie.has(KEY_SYNOPSIS) && !currentMovie.isNull(KEY_SYNOPSIS)) {
                        synopsis = currentMovie.getString(KEY_SYNOPSIS);
                    }

                    //get the thumbnail
                    if (currentMovie.has(KEY_POSTERS) && !currentMovie.isNull(KEY_POSTERS)) {
                        JSONObject posterObject = currentMovie.getJSONObject(KEY_POSTERS);
                        if (posterObject != null &&
                                posterObject.has(KEY_THUMBNAIL) &&
                                !posterObject.isNull(KEY_THUMBNAIL)) {
                            urlThumbnail = posterObject.getString(KEY_THUMBNAIL);
                        }
                    }

                    Movie movie = new Movie();
                    movie.setMovieId(id);
                    movie.setMovieName(title);
                    try{
                        date = dateFormat.parse(releaseDate);//this can fail if no date (NA)
                    }catch (ParseException e) {}
                    movie.setReleaseDateTheater(date);
                    movie.setAudienceScore(audienceScore);
                    movie.setSynopsis(synopsis);
                    movie.setUrlThumbnail(urlThumbnail);

                    // donot show the movie if there is no id for the movie or the title
                    if (id != -1 && !title.equals(NOT_AVAILABLE)) {
                        moviesList.add(movie);
                        //dataMovies.append("[" + id + ", " + title + ", " + releaseDate + ", " + audienceScore + "]");
                    }
                }
              } else {
                Logger.showToast(getActivity(), "no movies, try later");
            }
        } catch (JSONException e) {

        }
            Logger.showToast(getActivity(), +moviesList.size() + " results fetched..");
        } else {

        }
        return moviesList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_box_office, container, false);

        //recyclerview implementation
        listMovieHits = (RecyclerView) view.findViewById(R.id.boxOfficeMoviesList);
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));

        //text volley error
        textVolleyError = (TextView) view.findViewById(R.id.textVolleyError);

        //mSwipeRefreshLayout
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeMovieHits);

        //onrefresh listener for swipe to get notification
        mSwipeRefreshLayout.setOnRefreshListener(this );

        //initialize the adapter class and set the adapter in the recycler view
        adapterBoxOffice = new AdapterBoxOffice(getActivity());
        listMovieHits.setAdapter(adapterBoxOffice);

        //on restoring state from rotation
        if(savedInstanceState != null) {
            textVolleyError.setVisibility(View.GONE);
            moviesList = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
            //set this on the adapter
            adapterBoxOffice.setMoviesList(moviesList);
            //sample way to hide refresh progress
            if(mSwipeRefreshLayout.isRefreshing()){
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }else {
            //call the json request when the activity is called for the first time
            sendJsonRequest();
        }
        return view;
    }


    @Override
    public void onSortByName() {
        movieSorter.sortByName(moviesList);
        Logger.showToast(getActivity(), "Sorted by movie title...");


        adapterBoxOffice.notifyDataSetChanged();
    }

    @Override
    public void onSortByDate() {
        movieSorter.sortByDate(moviesList);
        Logger.showToast(getActivity(), "Sorted by movie releasedate...");
        adapterBoxOffice.notifyDataSetChanged();
    }

    @Override
    public void onSortByRatings() {
        movieSorter.sortByRating(moviesList);
        Logger.showToast(getActivity(), "Sorted by movie ratings...");
        adapterBoxOffice.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        // will show swype for refresh
        Logger.showToast(getActivity(), "Sorting list, Please wait..");
        //usually a async task should be called and the post execute method should off the swipe
        //progress bar
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //onSortByName();
                adapterBoxOffice.setMoviesList(moviesList);
            }
        },4000);
    }
}
