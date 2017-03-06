package com.run.funny.rxjavasampledemorun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Timestamped;

public class TimestampActivity extends AppCompatActivity {

    private TextView tv_show;
    private TextView tv_result;
    private Button btn_start;
    private Integer [] number={1,3,5,2,34,7,5,86,23,43};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timestamp);
        initView();
    }
    private void initView(){
        tv_result = (TextView) findViewById(R.id.tv_result);
        tv_show = (TextView) findViewById(R.id.tv_show);
        btn_start = (Button) findViewById(R.id.btn_start);
        tv_show.setText("为给定数据列表：1,3,5,2,34,7,5,86,23,43中每一个数据加上一个时间戳   \n\ntimestamp() :为每个事件加上一个时间戳");
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
    }
    private void start(){
        Observable.from(number)
                .timestamp()
                .subscribe(new Action1<Timestamped<Integer>>() {
                    @Override
                    public void call(Timestamped<Integer> integerTimestamped) {
                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
                        tv_show.append("value: "+integerTimestamped.getValue()+"       time:   ");
                        tv_show.append(sdf.format(new Date(integerTimestamped.getTimestampMillis()))+"\n");
                    }
                });
    }
}
