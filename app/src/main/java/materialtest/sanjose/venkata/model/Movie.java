package materialtest.sanjose.venkata.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import materialtest.sanjose.venkata.logging.Logger;

/**
 * Created by buddhira on 5/1/2015.
 */
public class Movie implements Parcelable{

    private long movieId;
    private String movieName;
    private Date releaseDateTheater;
    private int audienceScore;
    private int criticsScore;
    private String synopsis;
    private String urlThumbnail;
    private String urlSelf;
    private String urlCast;
    private String urlReviews;
    private String urlSimilar;


    public Movie(long movieId,
                 String movieName,
                 Date releaseDateTheater,
                 int audienceScore,
                 int criticsScore,
                 String synopsis,
                 String urlThumbnail,
                 String urlSelf,
                 String urlCast,
                 String urlReviews,
                 String urlSimilar) {

        this.movieId = movieId;
        this.movieName = movieName;
        this.releaseDateTheater = releaseDateTheater;
        this.audienceScore = audienceScore;
        this.criticsScore = criticsScore;
        this.synopsis = synopsis;
        this.urlThumbnail = urlThumbnail;
        this.urlSelf = urlSelf;
        this.urlCast = urlCast;
        this.urlReviews = urlReviews;
        this.urlSimilar = urlSimilar;
    }

    public Movie() {

    }

    public Movie(Parcel input) {
        //have to restore data back that was written to dest
        //again the order has to be followed as stored in writeToParcel
        movieId = input.readLong();
        movieName = input.readString();
        // as the date is store as long format we are retrieving and converting to date
        releaseDateTheater = new Date(input.readLong());
        audienceScore = input.readInt();
        criticsScore = input.readInt();
        synopsis = input.readString();
        urlThumbnail = input.readString();
        urlSelf = input.readString();
        urlCast = input.readString();
        urlReviews = input.readString();
        urlSimilar = input.readString();
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Date getReleaseDateTheater() {
        return releaseDateTheater;
    }

    public void setReleaseDateTheater(Date releaseDateTheater) {
        this.releaseDateTheater = releaseDateTheater;
    }

    public int getAudienceScore() {
        return audienceScore;
    }

    public void setAudienceScore(int audienceScore) {
        this.audienceScore = audienceScore;
    }

    public int getCriticsScore() {
        return criticsScore;
    }

    public void setCriticsScore(int criticsScore) {
        this.criticsScore = criticsScore;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }

    public String getUrlSelf() {
        return urlSelf;
    }

    public void setUrlSelf(String urlSelf) {
        this.urlSelf = urlSelf;
    }

    public String getUrlCast() {
        return urlCast;
    }

    public void setUrlCast(String urlCast) {
        this.urlCast = urlCast;
    }

    public String getUrlReviews() {
        return urlReviews;
    }

    public void setUrlReviews(String urlReviews) {
        this.urlReviews = urlReviews;
    }

    public String getUrlSimilar() {
        return urlSimilar;
    }

    public void setUrlSimilar(String urlSimilar) {
        this.urlSimilar = urlSimilar;
    }

    @Override
    public String toString() {
        return "\nID: " + movieId +
                "\nTitle " + movieName +
                "\nDate " + releaseDateTheater +
                "\nSynopsis " + synopsis +
                "\nScore " + audienceScore +
                "\nurlThumbnail " + urlThumbnail +
                "\nurlSelf " + urlSelf +
                "\nurlCast " + urlCast +
                "\nurlReviews " + urlReviews +
                "\nurlSimilar " + urlSimilar +
                "\n";
    }

    //parcel - describe contents
    @Override
    public int describeContents() {
        Logger.showLogInfo("describingcontents");
        return 0;
    }

    //parcel - to write to parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Logger.showLogInfo("storetoparcel");
        dest.writeLong(movieId);
        dest.writeString(movieName);
        dest.writeLong(releaseDateTheater.getTime()); //converting date to long
        dest.writeInt(audienceScore);
        dest.writeInt(criticsScore);
        dest.writeString(synopsis);
        dest.writeString(urlThumbnail);
        dest.writeString(urlSelf);
        dest.writeString(urlCast);
        dest.writeString(urlReviews);
        dest.writeString(urlSimilar);
        
        /*******VERY VERY IMPORTANT ************/
        //for a boolean dest.writeInt(1) for true or dest.writeInt(0) for false
        //read the dest in the same way as inserted
    }
    
    //to restore from parcelable
    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            Logger.showLogInfo("createfromparcel");
            return new Movie(in);
        }


        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
