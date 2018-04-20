package com.busu.cyy.customview.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.busu.cyy.customview.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_calender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_calender = (Button)findViewById(R.id.btn_calender);
        btn_calender.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_calender:
                startActivity(new Intent(MainActivity.this,CalenderActivity.class));
                break;
        }
    }
}
