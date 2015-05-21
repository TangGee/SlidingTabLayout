package com.rizhi.tablayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by tangtang on 15/5/21.
 */
public class MyAdapter extends FragmentPagerAdapter {
    public MyAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return position+"xxxx";
    }

    @Override
    public Fragment getItem(int i) {


        MyFragment myFragment=new MyFragment();

        Bundle bundle=new Bundle();
        bundle.putInt("INDEX",i);
        myFragment.setArguments(bundle);


        return myFragment;
    }

    @Override
    public int getCount() {
        return 10;
    }
}
