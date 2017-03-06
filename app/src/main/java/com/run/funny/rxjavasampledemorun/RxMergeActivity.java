package com.run.funny.rxjavasampledemorun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxMergeActivity extends AppCompatActivity {

    private Button btn_statr;
    private TextView tv_show1;
    private TextView tv_show2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_merge);
        initView();
    }

    private void initView(){
        btn_statr = (Button) findViewById(R.id.btn_start);
        tv_show1 = (TextView) findViewById(R.id.tv_show1);
        tv_show2 = (TextView) findViewById(R.id.tv_show2);
        btn_statr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
    }
    private void start(){
        Observable observableA = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Thread.sleep(500);
                    subscriber.onNext("666666");
                    subscriber.onCompleted();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        Observable observableB = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Thread.sleep(3000);
                    subscriber.onNext("99999");
                    subscriber.onCompleted();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Observable.merge(observableA,observableB)//只有并发的事件都执行完了才执行onComplete()
                .subscribeOn(Schedulers.newThread())//事件发生的线程为新创建的线程
                .observeOn(AndroidSchedulers.mainThread())//指定事件消耗的线程为主线程
                .subscribe(new Subscriber<String>() {
                    StringBuffer sb = new StringBuffer();
                    @Override
                    public void onCompleted() {
                        tv_show2.append("更新数据："+sb+"\n");
                        tv_show2.append("两个任务都处理完毕！！\n");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String string) {
                        sb.append(string+"\n");
                        tv_show1.append("收到数据："+string+"\n");
                    }
                });
    }
}
