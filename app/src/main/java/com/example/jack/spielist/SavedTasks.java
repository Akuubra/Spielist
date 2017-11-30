package com.example.jack.spielist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SavedTasks extends AppCompatActivity {

    private ArrayList<String> listArray;
    private CustomAdapter customAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_task_screen);

        //Get Intent
        Intent intent = getIntent();
        listArray = (ArrayList<String>) intent.getSerializableExtra("SavedTasksKey");

        customAdapter = new CustomAdapter();

        //Get View
        listView = findViewById(R.id.SavedTasksList);
        listView.setAdapter(customAdapter);

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

            view  = getLayoutInflater().inflate(R.layout.save_task_layout, null);
            //view = customView;
            TextView textViewTask = view.findViewById(R.id.saveTaskTitle);

            textViewTask.setText(listArray.get(i));

            return view;
        }
    }
}