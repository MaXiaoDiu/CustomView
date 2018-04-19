package com.busu.cyy.customview.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.busu.cyy.customview.R;
import com.busu.cyy.customview.View.CircleCheck;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private Button btn_Success;
    private CircleCheck mycirclecheck;
    private Button btn_calender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Success = (Button)findViewById(R.id.btn_Success);
        mycirclecheck = (CircleCheck) findViewById(R.id.mycirclecheck);
        btn_Success.setOnClickListener(this);
        btn_calender = (Button)findViewById(R.id.btn_calender);
        btn_calender.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_Success:
                mycirclecheck.StopLoadingAnimator();
                break;
            case R.id.btn_calender:
                startActivity(new Intent(MainActivity.this,CalenderActivity.class));
                break;
        }
    }
}
