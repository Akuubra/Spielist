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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
    private ArrayList<Button> buttonList = new ArrayList<>();
    private ArrayList<String> descriptionList = new ArrayList<>();
    private ArrayList<Boolean> completedList = new ArrayList<>();
    private EditText taskDescription;
    private ToggleButton toggle;

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

        taskDescription = findViewById(R.id.taskDescription);

        for(int i = 0; i < listArray.size(); i++)
        {
            descriptionList.add("");
        }

        toggle = findViewById(R.id.taskToggleButton);
        toggle.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view) {
                for(int i = 0; i < buttonList.size(); i++)
                {
                    if(!buttonList.get(i).isEnabled())
                    {

                        if(!toggle.isChecked())
                        {
                            progress += 1;
                            progressBar.setProgress(progress);
                            toggle.setEnabled(false);
                            //completedList.set(i, true);
                        }
                    }

                }

            }

        });

    }

    public void Stopwatch(View view) {
        String timerName = "";
        for (int i = 0; i < buttonList.size(); i++)
        {
            if (!buttonList.get(i).isEnabled())
            {
                timerName = buttonList.get(i).getText().toString();
            }
        }

        Timer(view);
    }

    public void Timer(View view)
    {

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

    public void setDescription(View view, int index)
    {
        descriptionList.set(index, taskDescription.getText().toString());
    }

    public String getDescription(View view, int index)
    {
        taskDescription.setText(descriptionList.get(index));
        return descriptionList.get(index);
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
            buttonList.add(textViewTask);

            textViewTask.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    //progress += 1;
                    //progressBar.setProgress(progress);
                    textViewTask.setTextColor(getApplication().getResources().getColor(R.color.grey));
                    toggle.setEnabled(true);
                    for(int i = 0; i < buttonList.size(); i++)
                    {

                        if(!buttonList.get(i).isEnabled())
                        {
                            setDescription(view, i);
                        }
                    }
                    for(int i = 0; i < buttonList.size(); i++)
                    {

                        if(buttonList.get(i).equals(textViewTask))
                        {
                            getDescription(view, i);
                        }
                        else
                        {
                            buttonList.get(i).setTextColor(getApplication().getResources().getColor(R.color.black));
                            buttonList.get(i).setEnabled(true);

                        }
                    }


                    textViewTask.setEnabled(false);
                    //textDescription.
                    //
                    // customAdapter.notifyDataSetChanged();
                }
            });
            //buttonArray.add(textViewTask);
            textViewTask.setText(listArray.get(i));

            return view;
        }
    }


}
