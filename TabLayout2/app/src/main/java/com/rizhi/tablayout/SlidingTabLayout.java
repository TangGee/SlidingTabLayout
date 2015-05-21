package com.rizhi.tablayout;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by tangtang on 15/5/21.
 */
public class SlidingTabLayout extends HorizontalScrollView {


    private ViewPager mViewPager;


    private static final int TAB_VIEW_TEXT_SIZE_SP = 12;

    private static final int TITLE_OFFSET_DIPS=24;

    private static  final int TAB_VIEW_PADDING_DIPS=16;

    private final int mTitleOffset;


    private SlidingTabStrip slidingTabStrip;

    private  boolean mDistributeEvenly;




    public SlidingTabLayout(Context context) {
        this(context, null, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setFillViewport(false);
        setHorizontalScrollBarEnabled(false);

        slidingTabStrip=new SlidingTabStrip(context);

        mTitleOffset = (int) (TITLE_OFFSET_DIPS * getResources().getDisplayMetrics().density);

    }



    public void setDistributeEvenly(boolean distributeEvenly)
    {
        this.mDistributeEvenly=distributeEvenly;

    }

    public void setViewPager(ViewPager viewPager)
    {

        mViewPager=viewPager;

        if(viewPager!=null)
        {

            mViewPager.setOnPageChangeListener(new IndicatorListener());
            populateTabStrip();

        }
    }

    private void populateTabStrip() {

       PagerAdapter adapter= mViewPager.getAdapter();

        if(adapter.getCount()<=0)
            return ;

        for (int i=0;i< adapter.getCount();i++){

            TextView titleView=getDefaultTitleView(getContext());

            titleView.setText(adapter.getPageTitle(i));


            slidingTabStrip.addView(titleView);

            if(mDistributeEvenly){
                titleView.getLayoutParams().width=0;
                ((LinearLayout.LayoutParams)titleView.getLayoutParams()).weight=1;
            }

        }

        addView(slidingTabStrip);


    }



    private  TextView getDefaultTitleView(Context context)
    {
        TextView textView=new TextView(context);
        textView.setGravity(Gravity.CENTER);

        TypedValue typedValue=new TypedValue();
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground,typedValue,true);
        textView.setBackgroundResource(typedValue.resourceId);
        textView.setAllCaps(true);

        int padding = (int) (TAB_VIEW_PADDING_DIPS * getResources().getDisplayMetrics().density);
        textView.setPadding(padding, padding, padding, padding);

        return textView;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if(mViewPager==null|| slidingTabStrip.getChildCount()<0)
            return;

        /**
         * 需要移动到位置
         */

        int selectItem=mViewPager.getCurrentItem();

        scrollToTab(selectItem,0);

    }

    private void scrollToTab(int selectItem, float positionOffset) {

        int tabStripChildCount=slidingTabStrip.getChildCount();

        if (tabStripChildCount == 0 || selectItem < 0 || selectItem >= tabStripChildCount)
            return;

        /**
         * 设置到滚动到相应位置
         */
        View selectView=slidingTabStrip.getChildAt(selectItem);

        float targetScrollX = selectView.getLeft() + positionOffset;

        if (selectItem > 0 || positionOffset > 0) {
            // If we're not at the first child and are mid-scroll, make sure we obey the offset
            targetScrollX -= mTitleOffset;
        }

        scrollTo((int)targetScrollX,0);



    }

    private class IndicatorListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float v, int i2) {
            int tabStripChildCount = slidingTabStrip.getChildCount();
            if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
                return;
            }

            Log.i("CCCC",v+"");

            slidingTabStrip.onPageChanage(position,v);

            View selectedTitle = slidingTabStrip.getChildAt(position);
            int extraOffset = (selectedTitle != null)
                    ? (int) (v * selectedTitle.getWidth())
                    : 0;
            scrollToTab(position, extraOffset);

        }

        @Override
        public void onPageSelected(int i) {

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }
}
