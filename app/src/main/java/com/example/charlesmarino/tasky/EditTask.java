package com.example.charlesmarino.tasky;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by charlesmarino on 9/19/14.
 */
public class EditTask extends Activity{
    private String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);
        Intent activityThatCalled = getIntent();
        Task taskSelected = (Task) activityThatCalled.getSerializableExtra("taskSelected");
        EditText name = (EditText) findViewById(R.id.task_name_edit_text);
        EditText description = (EditText) findViewById(R.id.task_description_edit_text);
        name.setText(taskSelected.getName());
        description.setText(taskSelected.getName());
        ID = taskSelected.getID();

    }

    public void editTask(View view) {
        EditText taskName = (EditText) findViewById(R.id.task_name_edit_text);
        EditText taskDescription = (EditText) findViewById(R.id.task_description_edit_text);
        String name = String.valueOf(taskName.getText());
        String description = String.valueOf(taskDescription.getText());
        Intent sendBack = new Intent();
        sendBack.putExtra("name", name);
        sendBack.putExtra("description", description);
        sendBack.putExtra("id", ID);
        setResult(RESULT_OK, sendBack);
        finish();
    }
}
