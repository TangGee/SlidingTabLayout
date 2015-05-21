package com.rizhi.tablayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by tangtang on 15/5/21.
 */
public class SlidingTabStrip extends LinearLayout {


    private static final int DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 0;
    private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 0x26;
    private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 3;
    private static final int DEFAULT_SELECTED_INDICATOR_COLOR = 0xFF33B5E5;


    private final int mBottomBorderThickess;

    private final Paint mBottomBorderPaint;

    private  final int mDefaultBottomBorderColor;



    private final int mIndicatorThickess;

    private final Paint mIndicatorPaint;


    private int mPosition;
    private float mPositionOffset;





    public SlidingTabStrip(Context context) {
        this(context, null, 0);
    }

    public SlidingTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingTabStrip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setWillNotDraw(false);

        float denisty=getResources().getDisplayMetrics().density;

        mBottomBorderThickess= (int) (DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS*denisty);
        mIndicatorThickess= (int) (SELECTED_INDICATOR_THICKNESS_DIPS*denisty);

        /**
         * 设置mBottomBorderPaint颜色
         */
        TypedValue typedValue=new TypedValue();

        getContext().getTheme().resolveAttribute(android.R.attr.colorForeground,typedValue,true);

        int color=typedValue.data;
        mDefaultBottomBorderColor = setColorAlpha(color,
                DEFAULT_BOTTOM_BORDER_COLOR_ALPHA);



        mBottomBorderPaint=new Paint();
        mIndicatorPaint=new Paint();

        mBottomBorderPaint.setColor(mDefaultBottomBorderColor);

        mIndicatorPaint.setColor(DEFAULT_SELECTED_INDICATOR_COLOR);

    }

    private int setColorAlpha(int color, byte defaultBottomBorderColorAlpha) {

        return Color.argb(defaultBottomBorderColorAlpha, Color.red(color), Color.green(color), Color.blue(color));

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int childCount=getChildCount();
        View selectView=getChildAt(mPosition);

        int left=selectView.getLeft();
        int right=selectView.getRight();


        Log.i("AAAA","onDraw");

        //滚动
        if (childCount>0)
        {
            if(mPosition>=0&& mPosition<childCount-1)
            {
                //计算需要画的位置
                View nextTitle=getChildAt(mPosition+1);


                left= (int) (left+(nextTitle.getLeft()-left)*mPositionOffset);
                right = (int) ((nextTitle.getRight()-right)*mPositionOffset+right);

                Log.i("AAAA","left: "+left+"  right:"+right+" top:"+(getHeight()-mIndicatorThickess)+"  bottom:"+getHeight()+" offset"+mPositionOffset);


            }


            canvas.drawRect(left,getHeight()-mIndicatorThickess,right,getHeight(),mIndicatorPaint);
        }

        canvas.drawRect(0,getHeight()-mBottomBorderThickess,getWidth(),getHeight(),mBottomBorderPaint);
    }


    public void onPageChanage(int position,float positionffset)
    {

          mPosition=position;
          mPositionOffset=  positionffset;

        Log.i("BBBB",position+"");

           invalidate();
    }

}
