package com.szy.calenderdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnDefault = (Button) findViewById(R.id.btn_default);
        Button btnHealth = (Button) findViewById(R.id.btn_health);
        Button btnHealthList = (Button) findViewById(R.id.btn_health_list);
        Button btnHealthLimit = (Button) findViewById(R.id.btn_health_limit);
        btnDefault.setOnClickListener(this);
        btnHealth.setOnClickListener(this);
        btnHealthList.setOnClickListener(this);
        btnHealthLimit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_default:
                DefaultCalendarActivity.start(this);
                break;

            case R.id.btn_health:
                HealthCalendarActivity.start(this);
                break;

            case R.id.btn_health_list:
                HealthListCalendarActivity.start(this);
                break;

            case R.id.btn_health_limit:
                HealthLimitCalendarActivity.start(this);
                break;

        }
    }
}
