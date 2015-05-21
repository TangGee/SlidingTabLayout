package com.rizhi.tablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by tangtang on 15/5/21.
 */
public class MyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        TextView textView=new TextView(getActivity());

        Bundle bundle=getArguments();
        int index=bundle.getInt("INDEX");

        textView.setText(index+"");

        textView.setGravity(Gravity.CENTER);

       return textView;

    }
}
