package br.com.vinicius.teste.gazeus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

class Apple {
    private Point mLocation = new Point();
    private Point mSpawnRange;
    private int mSize;
    private Bitmap mBitmapApple;

    /**
     * Apple Classe definition
     * @param context receveid from activity
     * @param sr contains the amount of side blocks
     * @param s contais the blocksize
     */
    public Apple(Context context, Point sr, int s){
        mSpawnRange = sr;
        mSize = s;
        mLocation.x = -10;

        mBitmapApple = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.apple);

        mBitmapApple = Bitmap
                .createScaledBitmap(mBitmapApple, s, s, false);
    }

}
