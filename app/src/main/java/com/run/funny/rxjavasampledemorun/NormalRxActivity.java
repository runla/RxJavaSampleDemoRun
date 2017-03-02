package com.run.funny.rxjavasampledemorun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class NormalRxActivity extends AppCompatActivity {

    private TextView textView;
    private Button btn_start;


    private String[] strings={"1","2","3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_rx);

        textView = (TextView) findViewById(R.id.textView);
        btn_start = (Button) findViewById(R.id.btn_start);

        initEvent();
    }

    private void initEvent(){
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    private void start(){
        //创建被观察者
        Observable observable=createObservable();
        //创建观察者
        Subscriber subscriber= createSubscriber();
        //Action1
        Action1 action1 = createAction1();

        textView.append("开始订阅，准备观察...\n");
        /**
         * 使用create（） 的方法创建Observable
         */
        //observable.subscribe(subscriber);

        /**
         * 使用from 发送数据subscriber
         */
        Observable.from(strings).subscribe(subscriber);
        /**
         * 使用from 发送数据action1
         */
        //Observable.from(strings).subscribe(action1);
    }
    /**
     * 创建被观察者
     * @return
     */
    private Observable createObservable(){
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello");
                subscriber.onNext("world");
                subscriber.onNext("!!!!!");
                subscriber.onCompleted();
            }
        });

        return observable;
    }

    /**
     * 创建一个观察者
     * @return
     */
    private Subscriber createSubscriber(){
        Subscriber subscriber = new Subscriber<String>(){

            @Override
            public void onCompleted() {
                textView.append("执行观察者中的onCompleted()...\n");
                textView.append("订阅完毕，结束观察...\n");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                textView.append("执行观察者中的onNext()...\n");
                textView.append(s+"...\n");
            }
        };
        return subscriber;
    }

    private Action1 createAction1(){
        Action1 action1 = new Action1<String>(){

            @Override
            public void call(String s) {
                textView.append("Action1 中的onNext()...\n");
                textView.append(s+"...\n");
            }
        };

        return action1;
    }
}
