package com.example.jack.spielist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListCreation extends AppCompatActivity {


    public static String[] Country = new String[]{"USA", "India", "Belgium", "France", "China", "Australia"};

    private ArrayList<String> listItems;
    private CustomAdapter customAdapter;
    private ListView list;
    //private String[] arrayItems;
    //private View customView  = getLayoutInflater().inflate(R.layout.customlayout, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_creation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("What do you need to do today?");
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setSubtitle("");

        //Input AutoComplete List
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.InputTask);
        ArrayAdapter<String> inputAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, Country);
        autoCompleteTextView.setAdapter(inputAdapter);

        //Initialising ListTasks
        list = findViewById(R.id.list);
        listItems = new ArrayList<String>();
        customAdapter = new CustomAdapter();
        list.setAdapter(customAdapter);

    }

    @Override
    public void onBackPressed() {
    }

    public void Clear(View view)
    {
        EditText Input = (EditText) findViewById(R.id.InputTask);
        Input.setText("");
    }

    public void Okay (View view)
    {
        TextView Input = findViewById(R.id.InputTask);
        if(!Input.getText().toString().equals(""))
        {
            if(findViewById(R.id.GetStarted).getVisibility() == View.INVISIBLE)
            {
                findViewById(R.id.GetStarted).setVisibility(View.VISIBLE);
            }
            listItems.add(Input.getText().toString());
            customAdapter.notifyDataSetChanged();
            Clear(view);
        }
    }

    public void GetStarted(View view)
    {
        Intent intent = new Intent(this, ListScreenActivity.class);
        ArrayList<String> listString = listItems;
        intent.putExtra("key", listString);
        startActivity(intent);
    }



    class CustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return listItems.size();
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

            view  = getLayoutInflater().inflate(R.layout.customlayout, null);
            //view = customView;
            TextView textViewTask = (TextView)view.findViewById(R.id.taskTitle);

            textViewTask.setText(listItems.get(i));

            return view;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_creation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
