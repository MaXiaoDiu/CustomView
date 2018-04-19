package com.busu.cyy.customview.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.busu.cyy.customview.R;
import com.busu.cyy.customview.View.CalanderView;
import com.busu.cyy.customview.View.ViewPagerCalenderView;

public class CalenderActivity extends AppCompatActivity {


    private RelativeLayout mycalenderview;
    private ViewPagerCalenderView calanderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        mycalenderview = (RelativeLayout)findViewById(R.id.mycalenderview);
        calanderView = new ViewPagerCalenderView(this);
        mycalenderview.addView(calanderView);
    }
}
