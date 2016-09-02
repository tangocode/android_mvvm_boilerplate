package com.boiler_plate.mobile.util.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.boiler_plate.mobile.R;

/**
 * This class draws a line
 *
 * @author      Daniel Coellar
 * @created 	2016-02-04
 */
public class PathView extends View {
    //Integration with activity
    PathViewListener listener;
    int step;
    boolean smooth;

    int framesPerSecond = 60;
    long animationDuration = 500; // 10 seconds

    Path path = new Path();       // your path
    Paint paint = new Paint();    // your paint

    long startTime;

    //Constructors
    public PathView(Context context) {
        super(context);
        // start the animation:
        this.startTime = System.currentTimeMillis();
        this.postInvalidate();
    }
    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public PathView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        long elapsedTime = System.currentTimeMillis() - startTime;

        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.darkGreen1));
        paint.setStrokeWidth(Measures.dipToPixels(getContext(),1));
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        //paint.setShadowLayer(7, 0, 0, Color.RED);

        path = new Path();
        path.moveTo(0, Measures.dipToPixels(getContext(),30));
        path.lineTo(Measures.dipToPixels(getContext(),1*(elapsedTime/20)), Measures.dipToPixels(getContext(),30));

        canvas.drawPath(path, paint);

        //invalidate();
        if(elapsedTime < animationDuration){
            this.postInvalidateDelayed(20 / framesPerSecond);
        }else if (this.listener != null) {
            this.listener.finish(this.step,this.smooth);
            this.listener = null;
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
