package com.run.funny.rxjavasampledemorun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn1;
    private Button btn2;
    private Button btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initView(){
        btn1 = (Button) findViewById(R.id.btn_1);
        btn2 = (Button) findViewById(R.id.btn_2);
        btn3 = (Button) findViewById(R.id.btn_3);
    }

    private void initEvent(){
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:
                Intent intent = new Intent(MainActivity.this,NormalRxActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_2:
                Intent intent2 = new Intent(MainActivity.this,RxFilterActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_3:
                Intent intent3 = new Intent(MainActivity.this,RxMapActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
