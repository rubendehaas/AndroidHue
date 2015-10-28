package com.example.ruben.androidhue;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import java.io.Serializable;

/**
 * Created by Ruben on 28/10/2015.
 */
public class LightListener implements CompoundButton.OnCheckedChangeListener, AdapterView.OnLongClickListener {

    private LightModel _lightModel;
    private Context _mainActivity;

    public LightListener(LightModel lightModel){
        this._lightModel = lightModel;
    }

    public LightListener(LightModel lightModel, Context mainActivity){
        this._lightModel = lightModel;
        this._mainActivity = mainActivity;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        _lightModel.stateOn = Boolean.toString(b);
        new LightPostTask(_lightModel).execute();
    }

    @Override
    public boolean onLongClick(View view) {

        Intent intent = new Intent(_mainActivity, LightDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("lightModel", (Serializable)_lightModel);
        _mainActivity.startActivity(intent);

        return true;
    }

}
