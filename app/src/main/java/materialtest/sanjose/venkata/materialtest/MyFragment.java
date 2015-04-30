package materialtest.sanjose.venkata.materialtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by buddhira on 4/29/2015.
 */
public class MyFragment extends Fragment {
    private TextView textView;

    public static MyFragment getInstance(int position) {
        MyFragment myFragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_my, container, false);
        textView = (TextView) layout.findViewById(R.id.position);

        Bundle bundle = getArguments();
        if (bundle != null) {
            textView.setText("Venkata has selected tab# " + bundle.getInt("position"));
        }


        //initialize the request queue object
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://php.net/", new Response.Listener<String>() {
            //Once the data is downloaded through Volley, this response method will have the
            // response (success in angjs)
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), "Success RESPONSE " + response, Toast.LENGTH_SHORT).show();
            }
        }, // if suppose that vogella request gave an error, then this, like in angjs
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error RESPONSE " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
        //Log.i("priority set for volley", "" + stringRequest.getPriority()); - returns NORMAL
        //Log.i("priority set for volley", "" + requestQueue.getCache()); - returns com.android.volley
        return layout;
    }
}
