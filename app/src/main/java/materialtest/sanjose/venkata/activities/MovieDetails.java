package materialtest.sanjose.venkata.activities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import materialtest.sanjose.venkata.adapters.AdapterBoxOffice;
import materialtest.sanjose.venkata.logging.Logger;
import materialtest.sanjose.venkata.materialtest.R;
import materialtest.sanjose.venkata.model.Movie;

public class MovieDetails extends AppCompatActivity implements ObservableScrollViewCallbacks {
    private View mImageView;
    private View mToolbarView;
    private ObservableScrollView mScrollView;
    private int mParallaxImageHeight;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextView = (TextView) findViewById(R.id.titleMovie);
        mImageView = findViewById(R.id.selectedMovieImage);
        mToolbarView = findViewById(R.id.toolbar);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.highRating)));

        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.drawer_menu_width);

        Movie movieDetails =
                getIntent().getParcelableExtra("movieDetails");

        //setTitle(movieDetails.getMovieName());
        setTitle("");

        String[] posterUrl = movieDetails.getUrlThumbnail().split("/");
        URI uri = null;
        try {
            uri = new URI(movieDetails.getUrlThumbnail());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String path = uri.getPath();
        Log.i("posterUrl", path);
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

        mTextView.setText(movieDetails.getMovieName());
        Picasso.with(this).load(imageNewURL).
                placeholder(R.drawable.ic_movie_poster)//.resize(200, 200)
                .into((android.widget.ImageView) mImageView);
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
