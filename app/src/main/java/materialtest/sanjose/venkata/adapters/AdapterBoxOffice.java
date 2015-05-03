package materialtest.sanjose.venkata.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import materialtest.sanjose.venkata.constants.ApplicationConstants;
import materialtest.sanjose.venkata.materialtest.R;
import materialtest.sanjose.venkata.model.Movie;
import materialtest.sanjose.venkata.network.VolleySingleton;

/**
 * Created by buddhira on 5/2/2015.
 */
public class AdapterBoxOffice extends RecyclerView.Adapter<AdapterBoxOffice.ViewHolderBoxOffice> {

    //layout inflater
    private Context context;
    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private ArrayList<Movie> moviesList = new ArrayList<>();
    private DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

    public AdapterBoxOffice(Context context) {

        this.context = context;
        layoutInflater = layoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    // this method would be initiated by the fragmentboxoffice class
    public void setMoviesList(ArrayList<Movie> moviesList) {
        this.moviesList = moviesList;
        //on change of data
        notifyItemRangeChanged(0, moviesList.size());
    }

    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.movie_bo_list, parent, false);
        //Create an object for viewholder
        ViewHolderBoxOffice viewHolder = new ViewHolderBoxOffice(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderBoxOffice holder, int position) {
        //after the layout is created for the given position inside recycler view
        // take data from ds fill the data inside the view

        //we need to store data in this adapter
        //the position retrieves the movie at that position
        Movie currentMovie = moviesList.get(position);

        //set each attribute in the holder view/ recycler
        holder.movieTitle.setText(currentMovie.getMovieName());

        Date movieReleaseDate = currentMovie.getReleaseDateTheater();
        if(movieReleaseDate != null) {
            String formattedReleaseDate = dateFormat.format(movieReleaseDate);
            holder.movieReleaseDate.setText(formattedReleaseDate);
        }else{
            holder.movieReleaseDate.setText(ApplicationConstants.RTConstants.NOT_AVAILABLE);
        }

        int audienceScore = currentMovie.getAudienceScore();

        if(audienceScore !=-1 ){
            holder.movieAudienceScore.setRating(audienceScore / 20.0F );
            holder.movieAudienceScore.setAlpha(1.0F);
        }else {
            holder.movieAudienceScore.setRating(0.0F);
            holder.movieAudienceScore.setAlpha(0.5F);
        }
        //divide by 20.0F for values between 0 and 5 - have remodify this approach
        holder.movieAudienceScore.setRating(currentMovie.getAudienceScore() / 20.0F);

        String urlThumbNail = currentMovie.getUrlThumbnail();
        loadThumbnails(urlThumbNail, holder);
    }

    private void loadThumbnails(String urlThumbNail, final ViewHolderBoxOffice holder) {
        if (urlThumbNail != ApplicationConstants.RTConstants.NOT_AVAILABLE) {
            //load the image using imageLoader from volleysingleton
            imageLoader.get(urlThumbNail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.movieThumbnail.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    static class ViewHolderBoxOffice extends RecyclerView.ViewHolder{
        //create object of image view, text view and rating bar used in xml

        ImageView movieThumbnail;
        TextView movieTitle;
        RatingBar movieAudienceScore;
        TextView movieReleaseDate;

        //the item view is instantiated by the layout inflater from
        // on onCreateViewHolder() and initialize all the objects in that layout here
        public ViewHolderBoxOffice(View itemView) {
            super(itemView);
            movieThumbnail = (ImageView) itemView.findViewById(R.id.movieThumbnail);
            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            movieAudienceScore = (RatingBar) itemView.findViewById(R.id.movieAudienceScore);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);
        }
    }
}