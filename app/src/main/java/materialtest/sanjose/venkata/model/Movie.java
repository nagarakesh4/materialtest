package materialtest.sanjose.venkata.model;

import java.util.Date;

/**
 * Created by buddhira on 5/1/2015.
 */
public class Movie {

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

}
