package com.busu.cyy.customview.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.busu.cyy.customview.View.CalanderView;

import java.util.List;

/**
 * Created by Cyy513 on 2018/4/19.
 */

public class CalenderAdapter extends PagerAdapter {

    private static final int YEAR_MAX = 2040;
    private static final int YEAR_MIN = 1960;

    //2400个月

    private List<CalanderView> calanderViews;

    public CalenderAdapter(List<CalanderView> calanderViews)
    {
        this.calanderViews = calanderViews;
    }

    @Override
    public int getCount() {

        return (YEAR_MAX - YEAR_MIN) * 12;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view ==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView(calanderViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(calanderViews.get(position));
        return calanderViews.get(position);
    }
}
