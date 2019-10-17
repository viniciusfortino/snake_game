package br.com.vinicius.teste.gazeus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;

class Snake {
    private ArrayList<Point> segmentLocations;
    private int mSegmentSize;
    private Point mMoveRange;

    private enum Heading {
        UP, RIGHT, DOWN, LEFT
    }

    private Heading heading = Heading.RIGHT;

    private Bitmap mBitmapHeadRight;
    private Bitmap mBitmapHeadLeft;
    private Bitmap mBitmapHeadUp;
    private Bitmap mBitmapHeadDown;
    private Bitmap mBitmapBody;

    /**
     * Constrcutor of snake
     *
     * @param context    receveid from activity
     * @param blockRange contains the amount of side blocks
     * @param blockSize  contais the blocksize
     */
    Snake(Context context, Point blockRange, int blockSize) {
        segmentLocations = new ArrayList<>();
        mSegmentSize = blockSize;
        mMoveRange = blockRange;

        //Definition each bitmap forsnake head
        mBitmapHeadRight = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.head);

        mBitmapHeadLeft = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.head);

        mBitmapHeadUp = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.head);

        mBitmapHeadDown = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.head);

        mBitmapHeadRight = Bitmap
                .createScaledBitmap(mBitmapHeadRight,
                        blockSize, blockSize, false);

        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);

        //Rotation of each bitmap previous defined
        mBitmapHeadLeft = Bitmap
                .createBitmap(mBitmapHeadRight,
                        0, 0, blockSize, blockSize, matrix, true);

        matrix.preRotate(-90);
        mBitmapHeadUp = Bitmap
                .createBitmap(mBitmapHeadRight,
                        0, 0, blockSize, blockSize, matrix, true);

        matrix.preRotate(180);
        mBitmapHeadDown = Bitmap
                .createBitmap(mBitmapHeadRight,
                        0, 0, blockSize, blockSize, matrix, true);

        //Definition of body bitmap
        mBitmapBody = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.body);

        mBitmapBody = Bitmap
                .createScaledBitmap(mBitmapBody,
                        blockSize, blockSize, false);
    }

    /**
     * Method responsible for restart snake position and its vector
     *
     * @param width
     * @param height
     */
    void reset(int width, int height) {
        heading = Heading.RIGHT;
        segmentLocations.clear();
        segmentLocations.add(new Point(width / 2, height / 2));
    }


    /**
     * Method responsible for move tsnake block by block using snake head vector
     */
    void move() {
        for (int i = segmentLocations.size() - 1; i > 0; i--) {

            segmentLocations.get(i).x = segmentLocations.get(i - 1).x;
            segmentLocations.get(i).y = segmentLocations.get(i - 1).y;
        }

        Point p = segmentLocations.get(0);

        //Snake head vector
        switch (heading) {
            case UP:
                p.y--;
                break;

            case RIGHT:
                p.x++;
                break;

            case DOWN:
                p.y++;
                break;

            case LEFT:
                p.x--;
                break;
        }

    }

    /**
     * Method responsible for check snake death
     *
     * @return true when sanek is dead
     */
    boolean detectDeath() {
        boolean dead = false;

        //Check if snake reach the corners
        if (segmentLocations.get(0).x == -1 ||
                segmentLocations.get(0).x > mMoveRange.x ||
                segmentLocations.get(0).y == -1 ||
                segmentLocations.get(0).y > mMoveRange.y) {

            dead = true;
        }

        //Check if snake eated itself
        for (int i = segmentLocations.size() - 1; i > 0; i--) {
            if (segmentLocations.get(0).x == segmentLocations.get(i).x &&
                    segmentLocations.get(0).y == segmentLocations.get(i).y) {

                dead = true;
            }
        }
        return dead;
    }

    /**
     * Method responsible for check if snake eated apple
     *
     * @param appleLocation contains (X,Y) from apple
     * @return true when snake eats apple
     */
    boolean checkDinner(Point appleLocation) {
        if (segmentLocations.get(0).x == appleLocation.x &&
                segmentLocations.get(0).y == appleLocation.y) {

            segmentLocations.add(new Point(-10, -10));
            return true;
        }
        return false;
    }

    /**
     * Method reponsible for draw the snake movement
     *
     * @param canvas where painting snake
     * @param paint  definitions
     */
    void draw(Canvas canvas, Paint paint) {
        if (!segmentLocations.isEmpty()) {
            switch (heading) {
                case RIGHT:
                    canvas.drawBitmap(mBitmapHeadRight,
                            segmentLocations.get(0).x
                                    * mSegmentSize,
                            segmentLocations.get(0).y
                                    * mSegmentSize, paint);
                    break;

                case LEFT:
                    canvas.drawBitmap(mBitmapHeadLeft,
                            segmentLocations.get(0).x
                                    * mSegmentSize,
                            segmentLocations.get(0).y
                                    * mSegmentSize, paint);
                    break;

                case UP:
                    canvas.drawBitmap(mBitmapHeadUp,
                            segmentLocations.get(0).x
                                    * mSegmentSize,
                            segmentLocations.get(0).y
                                    * mSegmentSize, paint);
                    break;

                case DOWN:
                    canvas.drawBitmap(mBitmapHeadDown,
                            segmentLocations.get(0).x
                                    * mSegmentSize,
                            segmentLocations.get(0).y
                                    * mSegmentSize, paint);
                    break;
            }

            for (int i = 1; i < segmentLocations.size(); i++) {
                canvas.drawBitmap(mBitmapBody,
                        segmentLocations.get(i).x
                                * mSegmentSize,
                        segmentLocations.get(i).y
                                * mSegmentSize, paint);
            }
        }
    }

    /**
     * Method responsible for define where snake is looking
     *
     * @param touchedBlock contains (X,Y) from block that player touched
     */
    void switchHeading(Point touchedBlock) {
        Point p = segmentLocations.get(0);
        float dx = touchedBlock.x - p.x;
        float dy = touchedBlock.y - p.y;

        if (Math.abs(dx) > 1 && Math.abs(dy) > 1) {
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0)
                    heading = Heading.RIGHT;
                else
                    heading = Heading.LEFT;
            } else {
                if (dy > 0)
                    heading = Heading.DOWN;
                else
                    heading = Heading.UP;
            }
        }
    }
}
