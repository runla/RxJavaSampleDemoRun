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
    private Button btn4;
    private Button btn5;
    private Button btn6;
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
        btn4 = (Button) findViewById(R.id.btn_4);
        btn5 = (Button) findViewById(R.id.btn_5);
        btn6 = (Button) findViewById(R.id.btn_6);
    }

    private void initEvent(){
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
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
            case R.id.btn_4:
                Intent intent4 = new Intent(MainActivity.this,RxBindingActivity.class);
                startActivity(intent4);
                break;
            case R.id.btn_5:
                Intent intent5 = new Intent(MainActivity.this,RxConnetActivity.class);
                startActivity(intent5);
                break;
            case R.id.btn_6:
                Intent intent6 = new Intent(MainActivity.this,RxFlatMapActivity.class);
                startActivity(intent6);
                break;
        }
    }
}
