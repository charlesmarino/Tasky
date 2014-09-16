package com.example.charlesmarino.tasky;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by charlesmarino on 9/10/14.
 */
public class TaskAdapter extends ArrayAdapter<Task>{
    private ArrayList<Task> values;
    public DBTools dbTools;

    public TaskAdapter(Context context, ArrayList<Task> values, DBTools dbTools) {
        super(context, R.layout.row_layout, values);
        this.values = values;
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
        //Add listener for btn
        Button deleteButton = (Button) convertView.findViewById(R.id.arrowRight);
        deleteButton.setTag(task.ID);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = (String) v.getTag();
                dbTools.deleteTask(tag);
                remove(getItem(position));
                notifyDataSetChanged();

            }
        });
        //return completed view
        return convertView;
    }

}
