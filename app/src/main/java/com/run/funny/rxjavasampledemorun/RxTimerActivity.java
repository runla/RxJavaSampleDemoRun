package com.run.funny.rxjavasampledemorun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class RxTimerActivity extends AppCompatActivity {
    private TextView tv_show;
    private TextView tv_result;
    private Button btn_start;
    private Button btn_stop;

    private Subscription subscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_timer);
        initView();
    }
    private void initView(){
        tv_result = (TextView) findViewById(R.id.tv_result);
        tv_show = (TextView) findViewById(R.id.tv_show);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        tv_show.append("定时器，每一秒发送打印一个数字   \\n\\n" +
                "interval(1, TimeUnit.SECONDS)  创建一个每隔一秒发送一次事件的对象");

        //开始订阅
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
        //取消订阅
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subscription != null||!subscription.isUnsubscribed()) {
                    subscription.unsubscribe();//取消监听
                }
            }
        });
    }
    private void start(){
        subscription = Observable.interval(1, TimeUnit.SECONDS)
                //interval（）是运行在computation Scheduler线程中的，因此需要转到主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        tv_result.setText(aLong+"");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null||!subscription.isUnsubscribed()) {
            subscription.unsubscribe();//取消监听
        }
    }
}
