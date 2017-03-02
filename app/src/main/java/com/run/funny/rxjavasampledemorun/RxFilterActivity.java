package com.run.funny.rxjavasampledemorun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class RxFilterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private TextView input;
    private Button btn_start;

    Integer[] integers={1,2,3,4,5,6,7,8,9,10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_filter);

        textView = (TextView) findViewById(R.id.textView);
        input = (TextView) findViewById(R.id.input_text);
        btn_start = (Button) findViewById(R.id.btn_start);

        btn_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        start();
    }

    private void start(){
        /**
         * Action1 的方式进行
         */
       /* Observable.from(integers)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer%2==0;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        textView.append(integer.toString()+"\n");
                    }
                });*/

        /**
         * Subcriber 的方式进行
         */
        Observable.from(integers)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer%2==0;
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        textView.append("过滤完成"+"\n");

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        textView.append("过滤开始"+"\n");
                        textView.append(integer.toString()+"\n");

                    }
                });
    }
}
