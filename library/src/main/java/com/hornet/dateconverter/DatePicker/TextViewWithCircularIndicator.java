package com.hornet.dateconverter.DatePicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.hornet.dateconverter.R;

/**
 * Created by Hornet on 5/2/2016.
 */
public class TextViewWithCircularIndicator extends TextView {

    private static final int SELECTED_CIRCLE_ALPHA=255;

    Paint mCirclePaint=new Paint();

    private int mCircleColor;
    private final String mItemIsSelectedText;

    private boolean mDrawCircle;


    public TextViewWithCircularIndicator(Context context,AttributeSet attrs) {
        super(context,attrs);
        mCircleColor= ContextCompat.getColor(context, R.color.mdtp_accent_color);
        mItemIsSelectedText="(2073) selected";

        init();
    }

    private void init() {
        mCirclePaint.setFakeBoldText(true);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setTextAlign(Paint.Align.CENTER);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setAlpha(SELECTED_CIRCLE_ALPHA);
    }

    public void setAccentColor(int color,boolean darkMode){
        mCircleColor=color;
        mCirclePaint.setColor(mCircleColor);
        setTextColor(createTextColor(color, darkMode));
    }

    private ColorStateList createTextColor(int accentColor,boolean darkMode){
        int[][] states=new int[][]{
                new int[]{android.R.attr.state_pressed}, // pressed
                new int[]{android.R.attr.state_selected}, // selected
                new int[]{}

        };
        int[] colors = new int[]{
                accentColor,
                Color.WHITE,
                darkMode ? Color.WHITE : Color.BLACK
        };
        return new ColorStateList(states, colors);

    }

    public void drawIndicator(boolean drawCircle){mDrawCircle=drawCircle;}

    @Override
    protected void onDraw(Canvas canvas) {

        if(mDrawCircle){
            final int width=getWidth();
            final int height=getHeight();
            int radius=Math.min(width,height)/2;
            canvas.drawCircle(width/2,height/2,radius,mCirclePaint);
        }
        setSelected(mDrawCircle);
        super.onDraw(canvas);
    }

    @Override
    public CharSequence getContentDescription() {
        CharSequence itemText = getText();
        if (mDrawCircle) {
            return String.format(mItemIsSelectedText, itemText);
        } else {
            return itemText;
        }
    }
}
