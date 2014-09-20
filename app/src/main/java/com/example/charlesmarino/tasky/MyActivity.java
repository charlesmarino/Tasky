package com.example.charlesmarino.tasky;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
        theListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                openAlert(view, position);
                return true;
            }
        });
        theListView.setLongClickable(true);
        // make ActionBar Name lobster
        Typeface lobster = Typeface.createFromAsset(getAssets(), "lobster.ttf");
        int titleId = getResources().getIdentifier("action_bar_title", "id",
                "android");
        TextView yourTextView = (TextView) findViewById(titleId);
        yourTextView.setTypeface(lobster);

    }

    private void openAlert(final View view, int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyActivity.this);
        final Task taskSelected = arrayOfTasks.get(position);
        alertDialogBuilder.setTitle(taskSelected.name);
        alertDialogBuilder.setMessage("Are you sure?");
        alertDialogBuilder.setPositiveButton("Complete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialogBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbtools.deleteTask(taskSelected.ID);
                adapter.clear();
                adapter.addAll(dbtools.getAllTasks());
                adapter.notifyDataSetChanged();
            }
        });
        alertDialogBuilder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editItem(taskSelected);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

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

        if(resultCode == Activity.RESULT_OK && requestCode ==1) {
            Task task = new Task(null, data.getStringExtra("name"), data.getStringExtra("description"));
            dbtools.insertTask(task);
            adapter.clear();
            adapter.addAll(dbtools.getAllTasks());
            adapter.notifyDataSetChanged();
        } else if(resultCode == Activity.RESULT_OK && requestCode ==2) {
            Task task =new Task(data.getStringExtra("id"),data.getStringExtra("name"), data.getStringExtra("description"));
            dbtools.editTask(task);
            adapter.clear();
            adapter.addAll(dbtools.getAllTasks());
            adapter.notifyDataSetChanged();
        }


    }


    public void addItem(MenuItem item) {
        final int ADD_ITEM_REQUEST = 1;

        Intent startAddTask = new Intent(this, AddTask.class);

        startActivityForResult(startAddTask, ADD_ITEM_REQUEST);
    }

    public void editItem(Task task){
        final int EDIT_ITEM_REQUEST = 2;

        Intent startEditTask = new Intent(this, EditTask.class);
        startEditTask.putExtra("taskSelected", task);
        startActivityForResult(startEditTask, EDIT_ITEM_REQUEST);
    }

}
