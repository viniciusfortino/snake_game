package br.com.vinicius.teste.gazeus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

class Apple {
    private Point mLocation = new Point();
    private Point mSpawnRange;
    private int mSize;
    private Bitmap mBitmapApple;

    /**
     * Apple Classe definition
     *
     * @param context receveid from activity
     * @param sr      contains the amount of side blocks
     * @param s       contais the blocksize
     */
    public Apple(Context context, Point sr, int s) {
        mSpawnRange = sr;
        mSize = s;
        mLocation.x = -10;

        mBitmapApple = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.apple);

        mBitmapApple = Bitmap
                .createScaledBitmap(mBitmapApple, s, s, false);
    }

    /**
     * Method responsible for spawn apple
     */
    public void spawn() {
        Random random = new Random();
        mLocation.x = random.nextInt(mSpawnRange.x) + 1;
        mLocation.y = random.nextInt(mSpawnRange.y - 1) + 1;
    }

    /**
     *
     * @return apple location
     */
    public Point getLocation() {
        return mLocation;
    }

    /**
     * Method reponsible for draw apple on screen
     * @param canvas where to draw
     * @param paint how to draw
     */
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mBitmapApple,
                mLocation.x * mSize, mLocation.y * mSize, paint);
    }
}
