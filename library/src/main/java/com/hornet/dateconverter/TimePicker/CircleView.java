package com.hornet.dateconverter.TimePicker;

/**
 * Created by Hornet on 5/22/2016.
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.hornet.dateconverter.R;

/**
 * Draws a simple white circle on which the numbers will be drawn.
 */
public class CircleView extends View {
    private static final String TAG = "CircleView";

    private final Paint mPaint = new Paint();
    private boolean mIs24HourMode;
    private int mCircleColor;
    private int mDotColor;
    private float mCircleRadiusMultiplier;
    private float mAmPmCircleRadiusMultiplier;
    private boolean mIsInitialized;

    private boolean mDrawValuesReady;
    private int mXCenter;
    private int mYCenter;
    private int mCircleRadius;

    public CircleView(Context context) {
        super(context);

        mIsInitialized = false;
    }

    public void initialize(Context context, TimePickerController controller) {
        if (mIsInitialized) {
            Log.e(TAG, "CircleView may only be initialized once.");
            return;
        }

        Resources res = context.getResources();

        int colorRes = controller.isThemeDark() ? R.color.mdtp_circle_background_dark_theme : R.color.mdtp_circle_color;
        mCircleColor = ContextCompat.getColor(context, colorRes);
        mDotColor = controller.getAccentColor();
        mPaint.setAntiAlias(true);

        mIs24HourMode = controller.is24HourMode();
        if (mIs24HourMode) {
            mCircleRadiusMultiplier = Float.parseFloat(
                    res.getString(R.string.mdtp_circle_radius_multiplier_24HourMode));
        } else {
            mCircleRadiusMultiplier = Float.parseFloat(
                    res.getString(R.string.mdtp_circle_radius_multiplier));
            mAmPmCircleRadiusMultiplier =
                    Float.parseFloat(res.getString(R.string.mdtp_ampm_circle_radius_multiplier));
        }

        mIsInitialized = true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        int viewWidth = getWidth();
        if (viewWidth == 0 || !mIsInitialized) {
            return;
        }

        if (!mDrawValuesReady) {
            mXCenter = getWidth() / 2;
            mYCenter = getHeight() / 2;
            mCircleRadius = (int) (Math.min(mXCenter, mYCenter) * mCircleRadiusMultiplier);

            if (!mIs24HourMode) {
                // We'll need to draw the AM/PM circles, so the main circle will need to have
                // a slightly higher center. To keep the entire view centered vertically, we'll
                // have to push it up by half the radius of the AM/PM circles.
                int amPmCircleRadius = (int) (mCircleRadius * mAmPmCircleRadiusMultiplier);
                mYCenter -= amPmCircleRadius*0.75;
            }

            mDrawValuesReady = true;
        }

        // Draw the white circle.
        mPaint.setColor(mCircleColor);
        canvas.drawCircle(mXCenter, mYCenter, mCircleRadius, mPaint);

        // Draw a small black circle in the center.
        mPaint.setColor(mDotColor);
        canvas.drawCircle(mXCenter, mYCenter, 8, mPaint);
    }
}
