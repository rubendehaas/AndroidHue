package com.example.ruben.androidhue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class LightDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_detail);

        Intent intent = getIntent();
        final LightModel lightModel = (LightModel) intent.getSerializableExtra("lightModel");
        Log.i("INTENT_SUCCESS", lightModel.name);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setProgress(Integer.parseInt(lightModel.stateBrightness));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            private Integer brightness;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                brightness = i;
                Log.i("StartTrack", Integer.toString(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i("StartTrack", "");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i("StopTrack", "");
                lightModel.stateBrightness = brightness.toString();

                new LightPostTask(lightModel).execute();
            }
        });

        if(lightModel.stateSaturation != null && lightModel.stateHue != null) {

            SeekBar seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
            TextView tv2 = (TextView) findViewById(R.id.textView2);
            seekBar2.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.VISIBLE);
            seekBar2.setProgress(Integer.parseInt(lightModel.stateSaturation));
            seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                private Integer saturation;

                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    saturation = i;
                    Log.i("StartTrack", Integer.toString(i));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    Log.i("StartTrack", "");
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    Log.i("StopTrack", "");
                    lightModel.stateSaturation = saturation.toString();

                    new LightPostTask(lightModel).execute();
                }
            });

            SeekBar seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
            TextView tv3 = (TextView) findViewById(R.id.textView3);
            seekBar3.setVisibility(View.VISIBLE);
            tv3.setVisibility(View.VISIBLE);
            seekBar3.setProgress(Integer.parseInt(lightModel.stateHue));
            seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                private Integer hue;

                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    hue = i;
                    Log.i("StartTrack", Integer.toString(i));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    Log.i("StartTrack", "");
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    Log.i("StopTrack", "");
                    lightModel.stateHue = hue.toString();

                    new LightPostTask(lightModel).execute();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_light_detail, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
