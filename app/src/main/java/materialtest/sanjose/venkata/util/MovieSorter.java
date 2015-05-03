package materialtest.sanjose.venkata.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import materialtest.sanjose.venkata.model.Movie;

/**
 * Created by buddhira on 5/3/2015.
 */
public class MovieSorter {
    public void sortByName(ArrayList<Movie> movies){
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return lhs.getMovieName().compareTo(rhs.getMovieName());
            }
        });
    }
    public void sortByDate(ArrayList<Movie> movies){

        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                if(lhs.getReleaseDateTheater()!=null && rhs.getReleaseDateTheater()!=null)
                {
                    return rhs.getReleaseDateTheater().compareTo(lhs.getReleaseDateTheater());
                }
                else {
                    return 0;
                }

            }
        });
    }
    public void sortByRating(ArrayList<Movie> movies){
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                if(lhs.getAudienceScore()<rhs.getAudienceScore())
                {
                    return 1;
                }
                else if(lhs.getAudienceScore()>rhs.getAudienceScore())
                {
                    return -1;
                }
                else
                {
                    return 0;
                }
            }
        });
    }
}
