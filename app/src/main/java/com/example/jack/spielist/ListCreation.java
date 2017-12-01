package com.example.jack.spielist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class ListCreation extends AppCompatActivity {


    public static String[] Country = new String[]{"USA", "India", "Belgium", "France", "China", "Australia"};
    private Context context = this;
    private ArrayList<String> listItems;
    private ArrayList<String> savedListItems;
    private CustomAdapter customAdapter;
    private ListView list;
    private String filename = "SavedTasksFile";
    private File saveFile = null;
    private BufferedReader reader;
    private TextView textView;
    private int length1;
    //private String[] arrayItems;
    //private View customView  = getLayoutInflater().inflate(R.layout.customlayout, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_creation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("What do you need to do today?");
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setSubtitle("");

        //Input AutoComplete List
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.InputTask);
        ArrayAdapter<String> inputAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, Country);
        autoCompleteTextView.setAdapter(inputAdapter);

        //Initialising ListTasks
        list = findViewById(R.id.list);
        listItems = new ArrayList<>();
        customAdapter = new CustomAdapter();
        list.setAdapter(customAdapter);
        savedListItems = new ArrayList<>();

        textView = findViewById(R.id.SaveFileLength);
        length1 = savedListItems.size();


        textView.setText(Integer.toString(length1));

        saveFile = new File(context.getFilesDir(), filename);

        Scanner s = null;
        //readFile(context, filename);
        try {
            s = new Scanner(new File(context.getFilesDir(), filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(s.hasNextLine())
        {
            savedListItems.add(s.nextLine());
        }
        s.close();

        //\\==============================================//\\
       //Try Reading
        //Try opening input

       /* FileInputStream in = null;
        try {
            in = new FileInputStream(saveFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        reader = new BufferedReader(new InputStreamReader(in));
        String line = "";
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(line != null)
        {
            if(!savedListItems.contains(line))
            {
                savedListItems.add(line);
            }
            try {
                Log.d("StackOverflow", line);
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*
        //Try Reading
        try {
            in.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Try closing
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String contents = new String(bytes);
        if(!savedListItems.contains(contents))
        {
            savedListItems.add(contents);
        }
        */
        //\\==============================================//\\

    }

    public void readFile(Context context, String filename)
    {
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            //StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                savedListItems.add(line);//.add("\n");
            }
            //return sb.toString();
        } catch (FileNotFoundException e) {
            //return "";
        } catch (UnsupportedEncodingException e) {
            //return "";
        } catch (IOException e) {
            //return "";
        }
    }

    public void SaveToPhone(String param)
    {
        //Initialise file to save

        FileOutputStream stream = null;
        OutputStreamWriter myOutWriter = null;
        //Try opening the file
        try {
            stream = new FileOutputStream(saveFile);
            myOutWriter = new OutputStreamWriter(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Try writing
        try {
            myOutWriter.append(param);
            //myOutWriter.append("\n\r");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Try closing
            try {
                myOutWriter.close();
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
    }

    public void Clear(View view)
    {
        EditText Input = findViewById(R.id.InputTask);
        Input.setText("");
    }

    public void Okay (View view)
    {
        TextView Input = findViewById(R.id.InputTask);
        if(!Input.getText().toString().equals("") && !listItems.contains(Input.getText().toString()))
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
        intent.putExtra("GetStartedKey", listString);
        startActivity(intent);
    }

    public void SavedTasks(View view)
    {
        Intent intent = new Intent(this, SavedTasks.class);
        ArrayList<String> listString = savedListItems;
        intent.putExtra("SavedTasksKey", listString);
        startActivity(intent);
    }

    public void Save(String param) throws IOException {
        if(!savedListItems.contains(param)) {
            savedListItems.add(param);
            SaveToPhone(param);
        }
    }

    public void Delete(String param)
    {
        if(listItems.contains(param))
        {
            listItems.remove(param);
            customAdapter.notifyDataSetChanged();

        }
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
            final TextView textViewTask = view.findViewById(R.id.taskTitle);
            textViewTask.setText(listItems.get(i));

            //Create Save onclick listener
            final Button saveButtonViewTask = view.findViewById(R.id.Save);
            saveButtonViewTask.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    String savedTaskString = textViewTask.getText().toString();
                    try {
                        Save(savedTaskString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    saveButtonViewTask.setEnabled(false);
                }
            });
            //Create Delete onclick listener
            final Button deleteButtonViewTask = view.findViewById(R.id.Delete);
            deleteButtonViewTask.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    String savedTaskString = textViewTask.getText().toString();
                    Delete(savedTaskString);
                    if(savedListItems.contains(savedTaskString))
                    {
                        saveButtonViewTask.setEnabled(false);
                    }

                }
            });



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
