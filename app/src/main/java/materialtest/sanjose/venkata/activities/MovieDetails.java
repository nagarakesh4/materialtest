package materialtest.sanjose.venkata.activities;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;

import materialtest.sanjose.venkata.logging.Logger;
import materialtest.sanjose.venkata.materialtest.R;
import materialtest.sanjose.venkata.model.Movie;

public class MovieDetails extends AppCompatActivity implements ObservableScrollViewCallbacks {
    private View mImageView;
    private View mToolbarView;
    private ObservableScrollView mScrollView;
    private int mParallaxImageHeight;

    private Movie movieDetails;

   // private TextView mMovieNameTextView;

    //Selected Movie details
    private RatingBar mRatingBar;
    private TextView mMovieNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //mMovieNameTextView = (TextView) findViewById(R.id.titleMovie);
        mImageView = findViewById(R.id.selectedMovieImage);
        mToolbarView = findViewById(R.id.toolbar);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.highRating)));

        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.drawer_menu_width);

        movieDetails =
                getIntent().getParcelableExtra("movieDetails");

        //recognize all the fields on the screen
        mRatingBar = (RatingBar) findViewById(R.id.selectedMovieRating);
        mMovieNameTextView = (TextView) findViewById(R.id.selectedMovieName);



        //setTitle(movieDetails.getMovieName());
        setTitle("");

        // choose the poster image from a different url
        //http://content6.flixster.com/movie/11/18/17/11181778_ori.jpg
        URI uri = null;
        try {
            uri = new URI(movieDetails.getUrlThumbnail());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String path = uri.getPath();
        String imagePath = "";
        int countIndex = 0;
        int index = path.indexOf("/");
        while (index >= 0) {
            index = path.indexOf("/", index + 1);
            countIndex++;
            if(countIndex == 3){
                imagePath = path.substring(index, path.length());
            }
        }
        String imageNewURL = "http://content6.flixster.com" + imagePath;
        Log.i("is this what??", imageNewURL);

        //mMovieNameTextView.setText(movieDetails.getMovieName());
        Picasso.with(this).load(imageNewURL).
                placeholder(R.drawable.ic_movie_poster).resize(1024, 1024)
                .into((android.widget.ImageView) mImageView);


        //set the details of the selected movie name
        setMovieDetails(movieDetails);
    }

    private void setMovieDetails(Movie movieDetails) {

        int high_score = getResources().getColor(R.color.highRating);
        int low_score = getResources().getColor(R.color.lowRating);
        int average_score = getResources().getColor(R.color.averageRating);
        int color = 0;

        int audienceRating = movieDetails.getAudienceScore();
        String movieName = movieDetails.getMovieName();

        //set the movie name
        mMovieNameTextView.setText(movieName);

        //set the rating
        if (audienceRating != -1) {
            mRatingBar.setRating(audienceRating / 20.0F);
            mRatingBar.setAlpha(1.0F);

            if (audienceRating / 20.0F > 3.6) {
                color = high_score;
            } else if (audienceRating / 20.0F > 2.75) {
                color = average_score;
            } else {
                color = low_score;
            }
            //change color of stars in ratings bar
            LayerDrawable stars = (LayerDrawable) mRatingBar.getProgressDrawable();

            stars.getDrawable(2).setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

        } else {
            mRatingBar.setRating(0.0F);
            mRatingBar.setAlpha(0.5F);
        }


    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.primary);
        float alpha = 1 - (float) Math.max(0, mParallaxImageHeight - scrollY) / mParallaxImageHeight;
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        Logger.showLogInfo(String.valueOf(alpha));
        if(alpha>0.8){
            setTitle(movieDetails.getMovieName());
            //setTitle("");
            //mMovieNameTextView.setText(movieDetails.getMovieName());
        }else{
            setTitle("");
            //setTitle(movieDetails.getMovieName());
            //Logger.showLogInfo(String.valueOf(movieDetails.getMovieName()));
            //mMovieNameTextView.setText("");
        }
        ViewHelper.setTranslationY(mImageView, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        /*ActionBar ab = getSupportActionBar();
        if (scrollState == ScrollState.UP) {
            if (ab.isShowing()) {
                ab.hide();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (!ab.isShowing()) {
                ab.show();
            }
        }*/
    }


}
