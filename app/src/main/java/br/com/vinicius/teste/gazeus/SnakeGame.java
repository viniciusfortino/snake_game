package br.com.vinicius.teste.gazeus;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

class SnakeGame extends SurfaceView implements Runnable {
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
     *
     * @param context receveid from activity
     * @param size    contains screen size
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

    /**
     * Starts new game spawing snake and apple as reset the score
     */
    public void newGame() {

        mApple.spawn();

        mScore = 0;

        mNextFrameTime = System.currentTimeMillis();
    }


    /**
     * Android method to run thread and render the game
     */
    @Override
    public void run() {
        while (mPlaying) {
            if (!mPaused) {
                if (updateRequired()) {
                    update();
                }
            }
            draw();
        }
    }

    /**
     * Method reponsible to framerate, defined by 10 frames per second
     *
     * @return true when frame must change
     */
    public boolean updateRequired() {
        final long TARGET_FPS = 10;
        final long MILLIS_PER_SECOND = 1000;
        if (mNextFrameTime <= System.currentTimeMillis()) {
            mNextFrameTime = System.currentTimeMillis()
                    + MILLIS_PER_SECOND / TARGET_FPS;
            return true;
        }
        return false;
    }

    /**
     * Method responsible for update not static objects
     */
    public void update() {

    }

    /**
     *  Method responsible for draw game objets and scenario
     */
    public void draw() {
        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawBitmap(mBackgroundBitmap, 0, 0, null);

            mPaint.setColor(Color.argb(255, 255, 255, 255));
            mPaint.setTextSize(120);
            mCanvas.drawText(getResources().getString(R.string.score) + mScore, 20, 120, mPaint);

            mApple.draw(mCanvas, mPaint);

            if (mPaused) {
                mPaint.setColor(Color.argb(255, 255, 255, 255));
                mPaint.setTextSize(250);
                mCanvas.drawText(getResources().
                                getString(R.string.tap_to_play),
                        200, 700, mPaint);
            }

            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    /**
     * Method responsible for get all the touchs events
     *
     * @param motionEvent by default
     * @return by default
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (mPaused) {
                    mPaused = false;
                    newGame();
                    return true;
                }
                break;

            case MotionEvent.ACTION_MOVE | MotionEvent.ACTION_DOWN:
                if (!mPaused) {
                }
                break;
            default:
                break;

        }
        return true;
    }

    /**
     * Method that actualy pause the thread game
     */
    public void pause() {
        mPlaying = false;
        try {
            mThread.join();
        } catch (InterruptedException e) {
            Log.e("ERROR: ", e.getMessage());
        }
    }

    /**
     * Method that actualy start the thread game
     */
    public void resume() {
        mPlaying = true;
        mThread = new Thread(this);
        mThread.start();
    }
}
