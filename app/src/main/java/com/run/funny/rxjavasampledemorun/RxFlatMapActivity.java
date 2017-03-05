package com.run.funny.rxjavasampledemorun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxFlatMapActivity extends AppCompatActivity {

    private TextView tv_tip;
    private TextView tv_show;
    private TextView tv_result;
    private Button btn_flatMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_flat_map);
        initView();
        initEvent();
    }

    private void initView(){
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        tv_show = (TextView) findViewById(R.id.tv_show);
        tv_result = (TextView) findViewById(R.id.tv_result);
        btn_flatMap = (Button) findViewById(R.id.btn_flatMap);
        tv_tip.setText("打印一个学校所有班级所有学生姓名");
    }

    private void initEvent(){
        btn_flatMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
    }
    private void start(){
        Student[] students1 = new Student[5];
        for (int i =0;i<5;i++){
            Student student = new Student(i+18,"小明"+i+1+"号");
            students1[i] = student;
        }
        Student[] students2 = new Student[5];
        for (int i =0;i<5;i++){
            Student student = new Student(i+18,"小红"+i+1+"号");
            students2[i] = student;
        }
        Class[] classes = new Class[2];
        classes[0] = new Class(students1);
        classes[1] = new Class(students2);

        Observable.from(classes)
                .flatMap(new Func1<Class, Observable<Student>>() {
                    //将Class列表使用from方法将学生一个一个发出去
                    @Override
                    public Observable<Student> call(Class aClass) {
                        return Observable.from(aClass.getStudents());
                    }
                })
                .subscribe(new Action1<Student>() {
                    @Override
                    public void call(Student student) {
                        tv_result.append(student.getName()+"  "+student.getAge()+"\n");
                    }
                });

    }
    public class Class{
        private Student[] students;

        public Class(Student[] students) {
            this.students = students;
        }

        public Student[] getStudents() {
            return students;
        }

        public void setStudents(Student[] students) {
            this.students = students;
        }
    }
    public class Student{
        private int age;
        private String name;

        public Student(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
