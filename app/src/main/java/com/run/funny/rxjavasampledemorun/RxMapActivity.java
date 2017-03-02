package com.run.funny.rxjavasampledemorun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxMapActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_start;
    private TextView tv_result;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_map);

        btn_start = (Button) findViewById(R.id.btn_start);
        tv_result = (TextView) findViewById(R.id.tv_result);
        editText = (EditText) findViewById(R.id.edit_num);

        btn_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        start();
    }

    private void start(){
        int num = Integer.parseInt(editText.getText().toString());
        /**
         * 使用Subscribe的方式
         */
        /*Observable.just(num)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        if (integer > 50){
                            return "true";
                        }
                        return "false";
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        //显示结果
                        tv_result.setText(s);
                    }
                });*/

        /**
         * 使用Action1 的方式
         */
        Observable.just(num)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        if (integer > 50){
                            return "true";
                        }
                        return "false";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        //显示结果
                        tv_result.setText(s);
                    }
                });
    }
}
