package com.example.i303390.remembrall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText location = (EditText) findViewById(R.id.location);
        EditText timeDuration = (EditText) findViewById(R.id.duration);
// hide until its title is clicked
        location.setVisibility(View.GONE);
        timeDuration.setVisibility(View.GONE);
    }
    public void toggle_contents(View v){
        EditText location = (EditText) findViewById(R.id.location);
        EditText timeDuration = (EditText) findViewById(R.id.duration);
        location.setVisibility( location.isShown()
                ? View.GONE
                : View.VISIBLE );
        timeDuration.setVisibility( timeDuration.isShown()
                ? View.GONE
                : View.VISIBLE );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.share_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
//    public void addtask(View v){
//        String task = String.valueOf(findViewById(R.id.EditTextName).getText());
//        SQLiteDatabase db = mHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
//        db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
//                null,values,SQLiteDatabase.CONFLICT_REPLACE);
//        db.close();
//        updateUI();
//    }
}
