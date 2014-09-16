package com.example.charlesmarino.tasky;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;

/**
 * Created by charlesmarino on 9/11/14.
 */
public class AddTask extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
    }

    public void createTask(View view) throws JSONException {
        EditText taskName = (EditText) findViewById(R.id.task_name_edit_text);
        EditText taskDescription = (EditText) findViewById(R.id.task_description_edit_text);

        String name = String.valueOf(taskName.getText());
        String description = String.valueOf(taskDescription.getText());

        Intent sendBack = new Intent();

        sendBack.putExtra("name", name);
        sendBack.putExtra("description", description);

        setResult(RESULT_OK, sendBack);

        finish();
    }
}
