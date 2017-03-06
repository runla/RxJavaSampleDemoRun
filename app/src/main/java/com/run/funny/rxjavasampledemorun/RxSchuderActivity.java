package com.run.funny.rxjavasampledemorun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxSchuderActivity extends AppCompatActivity {

    private Button btn_show;
    private LinearLayout linearLayout;
    private TextView tv_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_schuder);
        initView();
    }
    private void initView(){
        btn_show = (Button) findViewById(R.id.btn_show);
        linearLayout = (LinearLayout) findViewById(R.id.activity_rx_schuder);
        tv_show = (TextView) findViewById(R.id.tv_show);
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
    }

    static  StringBuffer sb=null;
    //从资源文件中获取图片，然后展示出来
    private void start(){
        sb = new StringBuffer();
        Observable.create(new Observable.OnSubscribe<Drawable>() {

            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                sb.append(" Observable.create(): 线程: "+Thread.currentThread().getName()+"\n\n");
                Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())//取出资源文件的事件指定在io线程执行
                .observeOn(Schedulers.newThread())//数据的转换指定在新线程
                .map(new Func1<Drawable,ImageView>() {

                    @Override
                    public ImageView call(Drawable drawable) {
                        sb.append("map():  drawable -->imageview 的线程: "+Thread.currentThread().getName()+"\n\n");
                        ImageView ig = new ImageView(RxSchuderActivity.this);
                        ig.setImageDrawable(drawable);
                        return ig;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//更新UI指定在主线程
                .subscribe(new Action1<ImageView>() {
                    @Override
                    public void call(ImageView imageView) {
                        sb.append("call(): 线程: "+Thread.currentThread().getName()+"\n");
                        tv_show.append(sb);
                        linearLayout.addView(imageView);//显示图片
                    }
                });
    }
}
