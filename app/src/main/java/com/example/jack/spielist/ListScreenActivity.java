package com.example.jack.spielist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListScreenActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> listArray;
    private CustomAdapter customAdapter;
    private Intent intentToLeave;
    private ProgressBar progressBar;
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_screen);

        //Get Intent
        Intent intent = getIntent();
        listArray = (ArrayList<String>) intent.getSerializableExtra("GetStartedKey");

        customAdapter = new CustomAdapter();

        //Get View
        listView = findViewById(R.id.OutputListTask);
        listView.setAdapter(customAdapter);

        intentToLeave = new Intent(this, ListCreation.class);


        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(listArray.size());
        progressBar.setProgress(progress);

    }

    public void Clear(View view)
    {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to clear your activities for the day?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //ListScreenActivity.this.finish();
                        listArray.clear();
                        customAdapter.notifyDataSetChanged();
                        startActivity(intentToLeave);
                    }
                })
                .setNegativeButton("No", null)
                .show();


    }



    public void Completed(View view, Button btn)
    {


            /* SPANNABLE STRIKETHROUGH
            TextView textView = (TextView)Completed.getText();
            Spannable text = new SpannableString(textView.getText());
            text.setSpan(new StrikethroughSpan(), 0, text.length(), 0);
            Completed.setText(textView, TextView.BufferType.SPANNABLE);
            */

        //android:tint="@color/colorAccent"
        //TextView textView = (TextView)view.findViewById(R.id.taskTitle);
        //listArray.remove(textView.getText());
        //customAdapter.notifyDataSetChanged();
    }

    class CustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return listArray.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view  = getLayoutInflater().inflate(R.layout.listscreenlayout, null);
            //view = customView;
            final Button textViewTask = view.findViewById(R.id.TaskTitleButton);
            textViewTask.setId(i);
            textViewTask.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    progress += 1;
                    progressBar.setProgress(progress);
                    textViewTask.setTextColor(getApplication().getResources().getColor(R.color.grey));
                    textViewTask.setEnabled(false);
                }
            });
            //buttonArray.add(textViewTask);
            textViewTask.setText(listArray.get(i));

            return view;
        }
    }


}
