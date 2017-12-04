package com.example.jack.spielist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class SavedTasks extends AppCompatActivity {

    private ArrayList<String> listArray;
    private CustomAdapter customAdapter;
    private ListView listView;
    private ArrayList<String> tasksToDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_task_screen);



        //Get Intent
        Intent intent = getIntent();
        listArray = (ArrayList<String>) intent.getSerializableExtra("SavedTasksKey");
        //checkBoxes = new CheckBox[listArray.size()];
        tasksToDelete = new ArrayList<>();
        customAdapter = new CustomAdapter();

        //Get View
        listView = findViewById(R.id.SavedTasksList);
        listView.setAdapter(customAdapter);

    }

    public void Delete(View view) throws IOException {
        for(int i = 0; i < tasksToDelete.size(); i++)
        {
            ListCreation.deleteFromFile(tasksToDelete.get(i));
            listArray.remove(tasksToDelete.get(i));
            customAdapter.notifyDataSetChanged();
            //ListCreation.removeLine();
            //tasksToDelete
        }
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
            final TextView textViewTask = view.findViewById(R.id.saveTaskTitle);
            final CheckBox checkBox = view.findViewById(R.id.checkBox2);
            checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if(((CheckBox) view).isChecked())
                    {
                        tasksToDelete.add(textViewTask.getText().toString());
                    }
                    else
                    {
                        tasksToDelete.remove(textViewTask.getText().toString());
                    }
                    /*try {

                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                }
            });
            //checkBoxes[i] = checkBox;
            textViewTask.setText(listArray.get(i));

            return view;
        }
    }
}
