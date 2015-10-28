package com.example.ruben.androidhue;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;

public class MainActivity extends Activity implements LightTask.newLightsAvailable{

    private static final String TAG = "MainActivity";
    private ListView lightListView = null;
    private ArrayList<LightModel> lights = new ArrayList<>();
    private LightAdapter lightAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        lightListView = (ListView) findViewById(R.id.listView);
        lightAdapter = new LightAdapter(getApplicationContext(), getLayoutInflater(), lights);
        lightListView.setAdapter(lightAdapter);

        new LightTask(this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinished(LightModel output) {

        lights.add(output);
        lightAdapter.notifyDataSetChanged();
    }
}
