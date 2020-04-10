package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter {
    ItemAdapter(Context context, ArrayList<String> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        String data = getItem(position).toString();
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView lapIndex = convertView.findViewById(R.id.lap_index);
        TextView lapText = convertView.findViewById(R.id.lap_text);
        // Populate the data into the template view using the data object
        lapIndex.setText(String.format("%01d", position));
        lapText.setText(data);
        // Return the completed view to render on screen
        return convertView;
    }
}
