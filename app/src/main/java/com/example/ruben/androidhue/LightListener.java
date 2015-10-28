package com.example.ruben.androidhue;

import android.widget.CompoundButton;

/**
 * Created by Ruben on 28/10/2015.
 */
public class LightListener implements CompoundButton.OnCheckedChangeListener {

    private LightModel _lightModel;

    public LightListener(LightModel lightModel){
        this._lightModel = lightModel;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        new LightPostTask(_lightModel, b).execute();
    }
}
