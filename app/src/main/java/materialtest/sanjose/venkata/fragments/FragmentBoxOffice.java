package materialtest.sanjose.venkata.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
import static materialtest.sanjose.venkata.util.MovieResponseKeys.EndpointBoxOffice.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBoxOffice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBoxOffice extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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

    //create a adapterboxoffice object to have the movies list
    private AdapterBoxOffice adapterBoxOffice;

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

    // preapare the request URL
    public static String getRequestUrl(int limit) {
        return COMBO_BO_RT + "&limit=" + limit;
    }

    public FragmentBoxOffice() {
        // Required empty public constructor
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

        JsonObjectRequest boxOfficeRequest = new JsonObjectRequest(Request.Method.GET, getRequestUrl(10),
                (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Logger.showToast(getActivity(), response.toString());
                moviesList = parseJSONResponse(response);
                //set the data in adapter box office
                adapterBoxOffice.setMoviesList(moviesList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Logger.showToast(getActivity(), error.toString());
            }
        });

        //add this request to the volley request queue
        requestQueue.add(boxOfficeRequest);
    }

    private ArrayList<Movie> parseJSONResponse(JSONObject response) {
        ArrayList<Movie> moviesList = new ArrayList<>();
        if(response != null && response.length() > 0) {

            // the response is a movies object in json array format
            try {
                StringBuilder dataMovies = new StringBuilder();
                if (response.has(KEY_MOVIES)) {
                    JSONArray arrayMovies = response.getJSONArray(KEY_MOVIES);
                    for (int i = 0; i < arrayMovies.length(); i++) {

                        //inside this movie json array, we get json objects of each movie
                        JSONObject currentMovie = arrayMovies.getJSONObject(i);
                        long id = currentMovie.getLong(KEY_ID);
                        String title = currentMovie.getString(KEY_TITLE);
                        JSONObject objectReleaseDates = currentMovie.getJSONObject(KEY_RELEASE_DATES);

                        String releaseDate = null;
                        //convert to date object
                        if (objectReleaseDates.has(KEY_THEATER)) {
                            releaseDate = objectReleaseDates.getString(KEY_THEATER);
                        } else {
                            releaseDate = NOT_AVAILABLE;
                            // need to do the same in parse json exception
                        }

                        JSONObject objectRatings = currentMovie.getJSONObject(KEY_RATINGS);
                        int audienceScore = -1;
                        if (objectRatings.has(KEY_AUDIENCE_SCORE)) {
                            audienceScore = objectRatings.getInt(KEY_AUDIENCE_SCORE);
                            //should be only 0 to 100
                            //set text view (hide initially) if rating available then show stars
                            // if rating na then set the text view to be shown
                        }

                        String synopsis = currentMovie.getString(KEY_SYNOPSIS);

                        JSONObject posterObject = currentMovie.getJSONObject(KEY_POSTERS);
                        String urlThumbnail = null;
                        if (posterObject.has(KEY_THUMBNAIL)) {
                            urlThumbnail = posterObject.getString(KEY_THUMBNAIL);
                        }

                        Movie movie = new Movie();
                        movie.setMovieId(id);
                        movie.setMovieName(title);
                        Date date = dateFormat.parse(releaseDate);//this can fail if no date (NA)
                        movie.setReleaseDateTheater(date);
                        movie.setAudienceScore(audienceScore);
                        movie.setSynopsis(synopsis);
                        movie.setUrlThumbnail(urlThumbnail);

                        moviesList.add(movie);
                        //dataMovies.append("[" + id + ", " + title + ", " + releaseDate + ", " + audienceScore + "]");
                    }
                    Logger.showLogInfo(moviesList.toString());

                    //Logger.showToast(getActivity(), moviesList.toString());
                } else {
                    Logger.showToast(getActivity(), "no movies, try later");
                }
            } catch (JSONException e) {

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{

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

        //initialize the adapter class and set the adapter in the recycler view
        adapterBoxOffice = new AdapterBoxOffice(getActivity());
        listMovieHits.setAdapter(adapterBoxOffice);

        //call the json request
        sendJsonRequest();
        return view;
    }


}
