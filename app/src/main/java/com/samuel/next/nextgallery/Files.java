package com.samuel.next.nextgallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

/**
 * Created by D067472 on 20.12.2017.
 */

//its a singleton
public class Files
{
    private static Files instance = null;
    private String copytoFoldername = "Studynotes & documents 3";
    private ArrayList<String> filesfound = new ArrayList<String>();

    public ArrayList<String> getFilesfound()
    {
        return filesfound;
    }

    protected Files() {
        // Exists only to defeat instantiation.
    }
    public static Files getInstance() {
        if(instance == null) {
            instance = new Files();
        }
        return instance;
    }


    public int copyFilesfound()
    {
        int erfolgreichkopiert=0;
        filesfound.size();

        if(filesfound.isEmpty()==false)
        {
            for(int i = 0; i<filesfound.size();i++)
            {
                erfolgreichkopiert+= copyFile(filesfound.get(i));
            }
        }
        return erfolgreichkopiert;
    }
    public int copyFile(String sourcePath)
    {
        String filename, destPath;
        File folder;

        folder = new File(Environment.getExternalStorageDirectory() + "/"+copytoFoldername);
        if (!folder.exists())
            folder.mkdir();

        filename =  sourcePath.substring(sourcePath.lastIndexOf("/")+1);
        destPath = Environment.getExternalStoragePublicDirectory(copytoFoldername+"/"+filename).getPath();

        try
        {
            File source = new File(sourcePath);
            File destination= new File(destPath);
            if (source.exists())
            {
                FileChannel src = new FileInputStream(source).getChannel();
                FileChannel dst = new FileOutputStream(destination).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
            return 1;
        }
        catch (Exception e) {
            return 0;
        }
    }
    //check if all patam are required
    public int analyseAlbum(Classifier classifier, Analyse myAnalyse, File [] file, String trainedcat)
    {
        //count how many are abfotografiert or as its called now "train model" if accurecy > 50%
        int countAbfotogrfiert=0;
        filesfound.clear();
        String path;
        Bitmap bp;
        String[][] results;
        double score;
        String scorename;

        //Analyse eachfile
        for(int i=0; i<file.length;i++)
        {
            //replace with filefilter
            if (file[i].isFile() && (file[i].getPath().endsWith(".jpeg")||file[i].getPath().endsWith(".jpg")))
            {
                path = file[i].getAbsolutePath();
                //by removing set Pic accurancy dropped by 10 percent , has shown flight booking pics
                bp = BitmapFactory.decodeFile(path);
                results = myAnalyse.analyse(classifier, bp);

                score = Double.parseDouble(results[0][1]);
                scorename = results[0][0].toString();
                if (score > 90 && scorename.equals(trainedcat))
                {
                    countAbfotogrfiert++;
                    filesfound.add(path);

                    if(score > 98)
                    {
                        //produces error
                        //setImageResult(bp);
                    }
                }
                else
                {
                    //throw new Error ("model could not found any");
                }
            }
        }
        return  countAbfotogrfiert;
    }
}
