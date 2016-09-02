package com.boiler_plate.mobile.util.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.boiler_plate.mobile.R;

/**
 * This class draws a circle
 *
 * @author      Daniel Coellar
 * @created 	2016-02-04
 */
public class PathViewCircle extends View {
    //Integration with activity
    PathViewListener listener;
    int step;
    boolean smooth;

    int framesPerSecond = 60;
    long animationDuration = 1440;

    Matrix matrix = new Matrix();
    Path path = new Path();
    Paint paint = new Paint();

    long startTime;

    //Constructors
    public PathViewCircle(Context context) {
        super(context);
        this.startTime = System.currentTimeMillis();
        this.postInvalidate();
    }
    public PathViewCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.startTime = System.currentTimeMillis();
        this.postInvalidate();
    }
    public PathViewCircle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.startTime = System.currentTimeMillis();
        this.postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(getResources().getColor(R.color.darkGreen1));
        paint.setStrokeWidth(Measures.dipToPixels(getContext(),1));
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);

        //path.moveTo(0, 80);

        long elapsedTime = System.currentTimeMillis() - startTime;
        float angle = 5 * elapsedTime / 20;
        RectF rect = new RectF(1, 1, Measures.dipToPixels(getContext(),59), Measures.dipToPixels(getContext(),59));
        canvas.drawArc(rect, 180, angle, false, paint);

        if(elapsedTime < animationDuration) {
            this.postInvalidateDelayed(20 / framesPerSecond);
        }else{
            if (this.listener != null) {
                this.listener.finish(this.step,this.smooth);
            }
        }

    }

    //Properties getters and setters
    public void setListener(PathViewListener listener){
        this.listener = listener;
    }
    public void setStep(int step) {
        this.step = step;
    }
    public void setSmooth(boolean smooth) {
        this.smooth = smooth;
    }

    //Interface for returning after animation
    public interface PathViewListener {
        void finish(int step, boolean smooth);
    }
}
