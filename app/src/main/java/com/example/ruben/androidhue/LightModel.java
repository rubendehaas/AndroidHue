package com.example.ruben.androidhue;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Ruben on 27/10/2015.
 */
public class LightModel implements Serializable{

    public Integer id;
    public String name;
    public String stateOn;
    public String stateBrightness;
    public String stateHue;
    public String stateSaturation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStateOn() {
        return stateOn;
    }

    public void setStateOn(String stateOn) {
        this.stateOn = stateOn;
    }

    public String getStateBrightness() {
        return stateBrightness;
    }

    public void setStateBrightness(String stateBrightness) {
        this.stateBrightness = stateBrightness;
    }

    public String getStateHue() {
        return stateHue;
    }

    public void setStateHue(String stateHue) {
        this.stateHue = stateHue;
    }

    public String getStateSaturation() {
        return stateSaturation;
    }

    public void setStateSaturation(String stateSaturation) {
        this.stateSaturation = stateSaturation;
    }
}
