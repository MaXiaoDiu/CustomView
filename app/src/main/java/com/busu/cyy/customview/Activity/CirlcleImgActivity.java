package com.busu.cyy.customview.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.busu.cyy.customview.R;
import com.busu.cyy.customview.View.CircleImageView;

public class CirlcleImgActivity extends AppCompatActivity {
    private CircleImageView mycircleimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cirlcle_img);
        mycircleimg = (CircleImageView)findViewById(R.id.mycircleimg);
        mycircleimg.setType(CircleImageView.TYPE_ROUND)
        .setCornerRadius(40)
        .setBorderColor(Color.WHITE)
        .setBorderWidth(10);
    }
}
