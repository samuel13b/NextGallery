package com.samuel.next.nextgallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/* Desc
in this class the user can select the or unselect th photos he does not like.
the ones he like will be copied to another folder
*/
public class SecondActivity extends AppCompatActivity
{
    TextView tv1;
    Files files;
    public  ArrayList<Integer> filesGray;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        files = Files.getInstance();
        Intent intent = getIntent();
        String message = intent.getStringExtra(ClassifierActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
         ArrayList<String> copy = files.getFilesfound();
        filesGray = new ArrayList<Integer>();
        setGridLayout(copy,filesGray);

    }

    public void setGridLayout(final ArrayList<String>filesFound, final ArrayList<Integer> filesGray)
    {
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, filesFound, filesGray));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                handlefilesGray(position);
                setGridLayout(filesFound, filesGray);

            }
        });
    }
    public void handlefilesGray(int position)
    {
        if(filesGray.contains(position))
            filesGray.remove(filesGray.indexOf(position));
        else
            filesGray.add(position);

    }

}
