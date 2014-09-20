package com.example.charlesmarino.tasky;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by charlesmarino on 9/10/14.
 */
public class TaskAdapter extends ArrayAdapter<Task>{
    public DBTools dbTools;

    public TaskAdapter(Context context, ArrayList<Task> values, DBTools dbTools) {
        super(context, R.layout.row_layout, values);
        this.dbTools = dbTools;
    }

    private static class ViewHolder {
        TextView name;
        TextView description;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //get Data for position
        Task task = getItem(position);

        //check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        //if (convertView == null ) {
            viewHolder = new ViewHolder();
            LayoutInflater theInflater = LayoutInflater.from(getContext());
            convertView = theInflater.inflate(R.layout.row_layout, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.nameTextView);
            viewHolder.description = (TextView) convertView.findViewById(R.id.descriptionTextView);
        //} else {
        //    viewHolder = (ViewHolder) convertView.getTag();
        // }

        //populate data
        viewHolder.name.setText(task.name);
        viewHolder.description.setText(task.description);
        //return completed view
        return convertView;
    }

}
