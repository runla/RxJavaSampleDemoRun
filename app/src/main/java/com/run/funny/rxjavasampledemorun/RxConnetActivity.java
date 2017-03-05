package com.run.funny.rxjavasampledemorun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rx.Observable;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;

public class RxConnetActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_normal;
    private Button btn_connect;
    private TextView tv_normal;
    private TextView tv_connect;
    private TextView tv_tip;
    private Integer [] integers ={1,2,3,4,5,6};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_connet);
        initView();
        initEvent();
    }
    private void initView(){
        btn_connect = (Button) findViewById(R.id.btn_connect);
        btn_normal = (Button) findViewById(R.id.btn_normal);
        tv_connect = (TextView) findViewById(R.id.tv_connect);
        tv_normal = (TextView) findViewById(R.id.tv_normal);
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        tv_tip.setText("Observable发送事件1-6，两个观察者同时观察这个Observable \\n要求：" +
                "每发出一个事件，观察者A和观察者都会收到，而不是先把所有的时间发送A,然后再发送给B");
    }
    private void initEvent(){
        btn_connect.setOnClickListener(this);
        btn_normal.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_connect:
                tv_connect.setVisibility(View.VISIBLE);
                tv_normal.setVisibility(View.GONE);
                tv_connect.setText("connect 的结果:\n");
                connect();
                break;
            case R.id.btn_normal:
                tv_normal.setVisibility(View.VISIBLE);
                tv_connect.setVisibility(View.GONE);
                tv_normal.setText("normal 的结果:\n");
                normal();
                break;
        }
    }

    private void normal(){
        Observable observable = Observable.from(integers);

        Action1 observerA = new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                tv_normal.append("观察者A收到："+integer+"\n");
            }
        };

        Action1 observerB = new Action1<Integer>(){

            @Override
            public void call(Integer integer) {

                tv_normal.append("观察者B收到："+integer+"\n");
            }
        };
        observable.subscribe(observerA);
        observable.subscribe(observerB);
    }

    private void connect(){
        ConnectableObservable observable = Observable.from(integers)
                .publish();//将一个Observable转换为一个可连接的Observable

        Action1 observerA = new Action1<Integer>(){
            @Override
            public void call(Integer integer) {
                tv_connect.append("观察者A收到："+integer+"\n");
            }
        };
        Action1 observerB = new Action1<Integer>(){
            @Override
            public void call(Integer integer) {
                tv_connect.append("观察者B收到："+integer+"\n");
            }
        };
        observable.subscribe(observerA);
        observable.subscribe(observerB);
        observable.connect();
    }


}
