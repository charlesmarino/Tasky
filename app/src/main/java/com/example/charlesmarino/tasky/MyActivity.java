package com.example.charlesmarino.tasky;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MyActivity extends Activity {

    private ArrayList<Task> arrayOfTasks;
    private TaskAdapter adapter;
    public DBTools dbtools = new DBTools(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        // Get Tasks
        arrayOfTasks = dbtools.getAllTasks();
        //Create the adapter and get the ListView
        adapter = new TaskAdapter(this, arrayOfTasks, dbtools);
        ListView theListView = (ListView) findViewById(R.id.theListView);

        //set Adapter to ListView
        theListView.setAdapter(adapter);

        // make ActionBar Name lobster
        Typeface lobster = Typeface.createFromAsset(getAssets(), "lobster.ttf");
        int titleId = getResources().getIdentifier("action_bar_title", "id",
                "android");
        TextView yourTextView = (TextView) findViewById(titleId);
        yourTextView.setTypeface(lobster);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {

                Task task = new Task(null, data.getStringExtra("name"), data.getStringExtra("description"));

                dbtools.insertTask(task);
                adapter.clear();
                adapter.addAll(dbtools.getAllTasks());
                adapter.notifyDataSetChanged();
            }


    }

    public void addItem(MenuItem item) {
        final int result = 1;

        Intent startAddTask = new Intent(this, AddTask.class);

        startActivityForResult(startAddTask, result);
    }

}
