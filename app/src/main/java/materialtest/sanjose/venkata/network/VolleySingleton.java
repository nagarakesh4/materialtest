package materialtest.sanjose.venkata.network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import materialtest.sanjose.venkata.instance.ApplicationInstance;

/**
 * Created by buddhira on 4/30/2015.
 */
public class VolleySingleton {

    private static VolleySingleton sInstance = null;

    //for caching images
    private ImageLoader imageLoader;

    //for valley requests
    private RequestQueue mRequestQueue;

    //create a private constructor which returns the request queue instance like
    //RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
    private VolleySingleton() {

        //here we cannot get activity context specific to the class that is
        // calling this requestqueue initiation, instead implement the similar
        // singleton process or retrieving activity
        // - we need a application context

        mRequestQueue = Volley.newRequestQueue(ApplicationInstance.getAppContext());

        imageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            // THE LRU CACHE, size is specified using maxmemory constraints - 1024/8 bytes
            private LruCache<String, Bitmap> cache = new LruCache<>((int) (Runtime.getRuntime().maxMemory()/1024/8));

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    //for other classes to access the image loader
    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    //create a object if not instance exists of this class
    public static VolleySingleton getInstance(){
        if(sInstance == null){
            sInstance = new VolleySingleton();
        }
        return sInstance;
    }

    //return the request queue for others to use it
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
