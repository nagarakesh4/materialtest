package materialtest.sanjose.venkata.activities;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import materialtest.sanjose.venkata.materialtest.R;


public class SubActivity extends ActionBarActivity {
    /*
    * This is the second page after main activity class
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appBar);

        //telling to use my own action bar
        setSupportActionBar(toolbar);
        // go back page if click on arrow
        getSupportActionBar().setHomeButtonEnabled(true);
        // display back button (arrow)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_sub, menu);
        getMenuInflater().inflate(R.menu.menu_sub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Hey you just hit " + item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.navigate) {
            startActivity(new Intent(this, UsingTabLibrary.class));
        }

        if(id == R.id.action_about) {
            Log.i("hey wow man,", "you want to know about of this?");
        }

        if(id == R.id.materialTab) {
            startActivity(new Intent(this, UsingTabLibrary.class));
        }
        // on the options menu we now have
        if(id == R.id.home) {
            Log.i("this is where=", "correct");
            NavUtils.navigateUpFromSameTask(getParent());
        }

        return super.onOptionsItemSelected(item);
    }
}
