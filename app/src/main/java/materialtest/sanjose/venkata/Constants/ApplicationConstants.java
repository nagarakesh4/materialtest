package materialtest.sanjose.venkata.Constants;

/**
 * Created by buddhira on 5/1/2015.
 */
public class ApplicationConstants {

    public interface RTConstants {
        public static final int MOVIES_SEARCH_RESULTS = 0;
        public static final int MOVIES_HITS = 1;
        public static final int MOVIES_UPCOMING = 2;

        public static final String API_KEY_RT = "vzm4ej9tdv6bwyy5pjwu8t3y";
        public static final String BASE_URL_RT = "http://api.rottentomatoes.com/api/public/v1.0";

        //BoxOffice url
        public static final String BOX_OFFICE_RT = "/lists/movies/box_office.json";

        //

        //Combo with API
        public static final String COMBO_BO_RT = BASE_URL_RT + BOX_OFFICE_RT + "?apikey=" + API_KEY_RT;
    }

}
