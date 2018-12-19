package com.szy.calenderdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnDefault = (Button) findViewById(R.id.btn_default);
        Button btnHealth = (Button) findViewById(R.id.btn_health);
        btnDefault.setOnClickListener(this);
        btnHealth.setOnClickListener(this);
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

        }
    }
}
