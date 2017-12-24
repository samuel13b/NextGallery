package com.samuel.next.nextgallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ClassifierActivity extends AppCompatActivity
{
    //for GalleryIntent
    private int PICK_IMAGE_REQUEST = 1;

    //new activity
    public static final String EXTRA_MESSAGE = "com.samuel.next.nextgallery";

    //setUp for tensorflow
    public static final int INPUT_SIZE = 224;
    public static final int IMAGE_MEAN = 128;
    public static final float IMAGE_STD = 128.0f;
    public static final String INPUT_NAME = "input";
    public static final String OUTPUT_NAME = "final_result";
    public static final String MODEL_FILE = "file:///android_asset/rounded_graph.pb";
    public static final String LABEL_FILE = "file:///android_asset/retrained_labels.txt";

    public Classifier classifier;
    public Executor executor = Executors.newSingleThreadExecutor();
    public TextView TV1;
    public ImageView iV1;
    Files files;
    Analyse analyse;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifier);
        TV1 = (TextView) findViewById(R.id.textView1);
        iV1 = (ImageView) findViewById(R.id.imageView1);
        initTensorFlowAndLoadModel();

        files = Files.getInstance();
        analyse = new Analyse();

    }

    private void initTensorFlowAndLoadModel()
    {
        executor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    classifier = TensorFlowImageClassifier.create(getAssets(), MODEL_FILE, LABEL_FILE, INPUT_SIZE, IMAGE_MEAN, IMAGE_STD, INPUT_NAME, OUTPUT_NAME);
                } catch (final Exception e)
                {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }

    public List<Classifier.Recognition> analyse(Bitmap bitmap)
    {
        bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
        final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);
        return results;
    }

    public void findStudynotes(View v)
    {
        //had to turn on storage permission manually, did not work before
        String cameraPath =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM+"/camera").getPath();
        File f = new File(cameraPath);
        File file[] = f.listFiles();

        double succesrate = 0;
        int countAbfotogrfiert = 0;
        //no files in folder
        if (file.length == 0)
           Log.e("error", "to small");
        else
            countAbfotogrfiert = files.analyseAlbum(classifier, analyse, file, "studynotes");

        try
        {
            succesrate = (double)countAbfotogrfiert/(double)file.length;
        }
        catch (ArithmeticException e)
        {
            Log.e("null divid", e.toString());
        }
        TV1.setText("Succesrate"+succesrate+" found: "+countAbfotogrfiert);
        startnewActivity();
    }

    public void startnewActivity()
    {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "DJHD");
        startActivity(intent);
    }
}