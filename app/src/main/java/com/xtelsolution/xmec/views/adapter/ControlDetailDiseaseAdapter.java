package com.xtelsolution.xmec.views.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.model.ControlDetailDisease;

import java.util.List;

/**
 * Created by ChungDT on 1/23/2017.
 */

public class ControlDetailDiseaseAdapter extends ArrayAdapter<ControlDetailDisease> {
    private List<ControlDetailDisease> list;
    private Activity context;

    public ControlDetailDiseaseAdapter(Activity context, List<ControlDetailDisease> objects) {
        super(context, R.layout.item_control_detail_disease, objects);
        this.list = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WeatherHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = (context).getLayoutInflater();
            row = inflater.inflate(R.layout.item_control_detail_disease, parent, false);

            holder = new WeatherHolder();
            holder.imgIcon = (ImageView) row.findViewById(R.id.ic_control);
            holder.txtTitle = (TextView) row.findViewById(R.id.name_control);

            row.setTag(holder);
        } else {
            holder = (WeatherHolder) row.getTag();
        }

        ControlDetailDisease disease = list.get(position);
        holder.txtTitle.setText(disease.getName());
        holder.imgIcon.setImageResource(disease.getImage());

        return row;
    }

    static class WeatherHolder {
        ImageView imgIcon;
        TextView txtTitle;
    }
}
