package com.run.funny.rxjavasampledemorun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxTakeActivity extends AppCompatActivity {

    private TextView tv_show;
    private TextView tv_result;
    private Button btn_start;
    private Integer [] number={1,2,3,4,5,6,7,8,9,10,11,12,13,14};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_take);
        initView();
    }
    private void initView(){
        tv_result = (TextView) findViewById(R.id.tv_result);
        tv_show = (TextView) findViewById(R.id.tv_show);
        btn_start = (Button) findViewById(R.id.btn_start);
        tv_show.setText("输出[1,2,3,4,5,6,7,8,9,10,11,12,13,14]中第三个和第四个奇数，\n\n" +
                "take(i) 取前i个事件 \n" +
                "takeLast(i) 取后i个事件 \n" +
                "doOnNext(Action1) 每次观察者中的onNext调用之前调用");
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
    }

    private void start(){
        Observable.from(number)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer%2==1;
                    }
                })
                //取前四个
                .take(4)
                //取前四个中的后两个
                .takeLast(2)
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        tv_result.append("before onNext（）\n");
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        tv_result.append("onNext()--->"+integer+"\n");
                    }
                });
    }
}
