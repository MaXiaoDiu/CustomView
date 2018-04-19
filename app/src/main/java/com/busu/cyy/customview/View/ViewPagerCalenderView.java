package com.busu.cyy.customview.View;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.busu.cyy.customview.Adapter.CalenderAdapter;
import com.busu.cyy.customview.R;
import com.busu.cyy.customview.Util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cyy513 on 2018/4/19.
 */

public class ViewPagerCalenderView extends RelativeLayout {

    private ViewPager calenderviewpager;
    private List<CalanderView> calanderViews;
    private CalenderAdapter calenderAdapter;
    private CalanderTitleView calander_titleview;


    public ViewPagerCalenderView(Context context) {
        super(context);
        init(context);
    }

    public ViewPagerCalenderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public ViewPagerCalenderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    //初始化
    private void init(Context context) {

        LayoutInflater.from(context).inflate(R.layout.viewpagercalender_lay, this, true);
        calenderviewpager = findViewById(R.id.calenderviewpager);
        calander_titleview = findViewById(R.id.calander_titleview);
        calenderviewpager.setOnPageChangeListener(new MyOnPageChangeListener());
        calander_titleview.setYearAndMonth(Integer.parseInt(DateUtil.getSysYear()),DateUtil.getCurrentMonth());
        calanderViews = new ArrayList<>();
        for (int i=0;i<960;i++)
        {
            calanderViews.add(new CalanderView(context,1960+(i/12),i%12+1));
        }
        calenderAdapter = new CalenderAdapter(calanderViews);
        calenderviewpager.setAdapter(calenderAdapter);
        calenderviewpager.setCurrentItem((Integer.parseInt(DateUtil.getSysYear())-1960)*12+DateUtil.getCurrentMonth()-1);
        calenderviewpager.setOffscreenPageLimit(960);
    }


    public class MyOnPageChangeListener  implements ViewPager.OnPageChangeListener
    {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            calander_titleview.setYearAndMonth(1960+(position/12),position%12+1);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


}
