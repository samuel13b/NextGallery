package com.samuel.next.nextgallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by D067472 on 21.12.2017.
 */

public class ImageAdapter extends BaseAdapter
{

  private Context mContext;
    private ArrayList<String> filesFound;
    private ArrayList<Integer> filesGray;

  public ImageAdapter(Context c, ArrayList<String> filesFound, ArrayList<Integer> filesGray) {
      mContext = c;
      this.filesFound = filesFound;
      this.filesGray = filesGray;
  }

  public int getCount() {
     // return mThumbIds.length;
      return filesFound.size();
  }

  public Object getItem(int position) {
      return null;
  }

  public long getItemId(int position) {
      return 0;
  }

  // create a new ImageView for each item referenced by the Adapter
  public View getView(int position, View convertView, ViewGroup parent) {
      ImageView imageView;
      if (convertView == null) {
          // if it's not recycled, initialize some attributes
          imageView = new ImageView(mContext);
          imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
          imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
         // imageView.setPadding(2, 2, 2, 2);
      } else {
          imageView = (ImageView) convertView;
      }

      Bitmap myBitmap = BitmapFactory.decodeFile(filesFound.get(position));
      Bitmap scaled = Bitmap.createScaledBitmap(myBitmap, 400,400,true);
      imageView.setImageBitmap(scaled);
      if(filesGray.contains(position))
        setGray(imageView);
      return imageView;
  }

    public void setGray(ImageView v)
    {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);  //0 means grayscale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
        v.setImageAlpha(128);   // 128 = 0.5
    }
    public void setColourful(ImageView v)
    {
        v.setColorFilter(null);
        v.setImageAlpha(255);
    }
  // references to our images can be deleted
  private Integer[] mThumbIds = {
          R.drawable.sample_2, R.drawable.sample_3,
          R.drawable.sample_4, R.drawable.sample_5,
          R.drawable.sample_6, R.drawable.sample_7,
          R.drawable.sample_0, R.drawable.sample_1,
          R.drawable.sample_2, R.drawable.sample_3,
          R.drawable.sample_4, R.drawable.sample_5,
          R.drawable.sample_6, R.drawable.sample_7,
          R.drawable.sample_0, R.drawable.sample_1,
          R.drawable.sample_2, R.drawable.sample_3,
          R.drawable.sample_4, R.drawable.sample_5,
          R.drawable.sample_6, R.drawable.sample_7
  };
}
