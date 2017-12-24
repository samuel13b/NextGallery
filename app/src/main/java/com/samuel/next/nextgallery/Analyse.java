package com.samuel.next.nextgallery;

import android.graphics.Bitmap;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by D067472 on 21.12.2017.
 */

public class Analyse extends ClassifierActivity
{
    public String[][] analyse(Classifier classifier, Bitmap bitmap)
    {
        bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);

        final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);

        //create array with the name and the score
        String[][] resultswithpercent = new String[results.size()][2];
        for (int i = 0; i < results.size(); i++)
        {
            String[] splitedString = results.get(i).toString().split(Pattern.quote("("));

            resultswithpercent[i][0] = splitedString[0].replaceAll("\\[.*?\\]", "").trim();
            resultswithpercent[i][1] = splitedString[1].replace("%)", "").trim();
        }
        return resultswithpercent;
    }
}
