package com.example.ruben.androidhue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;

import java.util.ArrayList;

/**
 * Created by Ruben on 28/10/2015.
 */
public class LightAdapter extends BaseAdapter {

    private Context _context;
    private LayoutInflater _inflater;
    private ArrayList _lightsArrayList;

    public LightAdapter(Context context, LayoutInflater layoutInflater, ArrayList<LightModel> lightsArrayList)
    {
        _context = context;
        _inflater = layoutInflater;
        _lightsArrayList = lightsArrayList;
    }

    @Override
    public int getCount() {
        int size = _lightsArrayList.size();
        return size;
    }

    @Override
    public Object getItem(int i) {
        return _lightsArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if(view == null) {

            view = _inflater.inflate(R.layout.listview_row, null);

            viewHolder = new ViewHolder();
            viewHolder.aSwitch = (Switch) view.findViewById(R.id.switch1);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        LightModel light = (LightModel) _lightsArrayList.get(i);

        viewHolder.aSwitch.setText(light.name);
        viewHolder.aSwitch.setChecked(Boolean.parseBoolean(light.stateOn));

        Switch lightSwitch = (Switch) view.findViewById(R.id.switch1);
        lightSwitch.setOnCheckedChangeListener(new LightListener(light));
        lightSwitch.setOnLongClickListener(new LightListener(light, _context));

        return view;
    }

    private static class ViewHolder {
        public Switch aSwitch;
    }
}
