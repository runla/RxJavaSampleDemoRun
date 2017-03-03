package com.run.funny.rxjavasampledemorun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxCompoundButton;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxBindingActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;
    private Button btn4;
    private CheckBox checkBox;
    private EditText editText;
    private EditText editText1;
    private TextView tv_result;
    private TextView tv_result1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_binding);

        initView();
    }
    private void initView(){
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn4 = (Button) findViewById(R.id.btn4);
        editText = (EditText) findViewById(R.id.editText);
        editText1 = (EditText) findViewById(R.id.editText1);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        tv_result = (TextView) findViewById(R.id.tv_result);
        tv_result1 = (TextView) findViewById(R.id.tv_result1);
        throttleFirst();
        longClick();
        textChange();
        buttonStatus();
        textChanges();
    }

    /**
     * 按钮防抖处理
     */
    private void throttleFirst(){
        RxView.clicks(btn1)
                .throttleFirst(5, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Toast.makeText(RxBindingActivity.this,"已经点击按钮",Toast.LENGTH_LONG).show();
                    }
                });
    }


    /**
     * 按钮长按事件监听
     */
    private void longClick(){
        RxView.longClicks(btn2)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Toast.makeText(RxBindingActivity.this,"长按.....",Toast.LENGTH_LONG).show();
                    }
                });

    }

    /**
     * 监听EditText的输入，同时将数字转换为 ‘ * ’显示在下边 textview中
     */
    private void textChange(){
        RxTextView.textChanges(editText)
                .debounce(500,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())//主线程
                .map(new Func1<CharSequence, StringBuffer>() {
                    @Override
                    public StringBuffer call(CharSequence charSequence) {
                        int len = charSequence.length();
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < len; i++) {
                            if (charSequence.charAt(i) >= '0' && charSequence.charAt(i)  <='9'){
                                sb.append("*");
                            }
                            else{
                                sb.append(charSequence.charAt(i));
                            }
                        }
                        return sb;
                    }
                })
                .subscribe(new Action1<StringBuffer>() {
                    @Override
                    public void call(StringBuffer stringBuffer) {
                        //editText.setText(stringBuffer.toString());
                        tv_result.setText(stringBuffer);
                    }
                });
    }

    /**
     * 勾选用户协议，登录按钮变亮
     */
    private void buttonStatus(){
        RxCompoundButton.checkedChanges(checkBox)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        btn4.setEnabled(true);
                        btn4.setBackgroundResource( aBoolean ? R.color.colorPrimary:R.color.colorAccent );

                    }
                });


    }

    /**
     * 用来监听edittext输入，同时匹配输入数数据来提示
     */
    private void textChanges(){
        RxTextView.textChanges(editText1)
                //500毫秒内没有新的操作，则发出该事件
                .debounce(500,TimeUnit.MILLISECONDS)
                //切换到一个新的线程中
                .observeOn(Schedulers.newThread())
                //通过输入的数据进行匹配，含1 则打印出1-20的偶数
                .map(new Func1<CharSequence, List<String>>() {
                    @Override
                    public List<String> call(CharSequence charSequence) {
                        List<String> list = new ArrayList<String>();
                        if (charSequence.toString().contains("1")){
                            for (int i = 0; i < 20; i++) {
                                list.add(""+i);
                            }
                        }
                        return list;
                    }
                })
                //不想要list列表,使用flatMap来分解成一个个的数据进行发送
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {
                        return Observable.from(strings);
                    }
                })
                //切换到主线程，不然无法操作
                .observeOn(AndroidSchedulers.mainThread())
                //再做一些过滤操作，如果不添加这个过滤，那么，每一次监听都会重复添加数据
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return !tv_result1.getText().toString().contains(s);
                    }
                })
                //过滤掉奇数
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return Integer.valueOf(s)%2==0;
                    }
                })

                //订阅
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                        tv_result1.append(s+"\n");
                    }
                });
    }
}
