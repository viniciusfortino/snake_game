package br.com.vinicius.teste.gazeus;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.SoundPool;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

class SnakeGame extends SurfaceView implements Runnable  {
    private Thread mThread = null;
    private long mNextFrameTime;
    private volatile boolean mPlaying = false;
    private volatile boolean mPaused = true;

    private SoundPool mSP;
    private int mEat_ID = -1;
    private int mCrashID = -1;

    private final int NUM_BLOCKS_WIDE = 40;
    private int mNumBlocksHigh;
    private int mblockSize;

    private int mScore;

    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;
    private Bitmap mBackgroundBitmap;

    private Snake mSnake;
    private Apple mApple;

    /**
     * Class responsible for the game, initialize all objects and response to touchEvents
     * @param context receveid from activity
     * @param size contains screen size
     */
    public SnakeGame(Context context, Point size) {
        super(context);
        mblockSize = size.x / NUM_BLOCKS_WIDE;
        mNumBlocksHigh = size.y / mblockSize;

        Bitmap preScaledBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        mBackgroundBitmap = Bitmap.createScaledBitmap(preScaledBitmap, size.x, size.y, true);

        mSurfaceHolder = getHolder();
        mPaint = new Paint();

        mApple = new Apple(context,
                new Point(NUM_BLOCKS_WIDE,
                        mNumBlocksHigh),
                mblockSize);
    }

    @Override
    public void run() {

    }
}
