package com.run.funny.rxjavasampledemorun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxSortActivity extends AppCompatActivity {

    private TextView tv_show;
    private TextView tv_result;
    private Button btn_start;

    private Integer [] words={1,3,5,2,34,7,5,86,23,43};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_sort);
        initView();
    }

    private void initView(){
        tv_result = (TextView) findViewById(R.id.tv_result);
        tv_show = (TextView) findViewById(R.id.tv_show);
        btn_start = (Button) findViewById(R.id.btn_start);
        tv_show.setText("为给定数据列表排序：1,3,5,2,34,7,5,86,23,43   \n\ntoSortedList() :为事件中的数据排序" );
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
    }

    private void start(){
        Observable.from(words)
                .toSortedList()
                .flatMap(new Func1<List<Integer>, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(List<Integer> integers) {
                        return Observable.from(integers);
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        tv_result.append(integer+",");
                    }
                });
    }
}
