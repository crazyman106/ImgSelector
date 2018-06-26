package com.fz.rx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<File> files = new ArrayList<>();
        files.add(new File("fasdf/fasd1.png"));
        files.add(new File("fasdf/fasd.png"));
        files.add(new File("fasdf/fasd"));
        files.add(new File("fasdf/fasd4.png"));

        /*Subscriber<Bitmap> subscriber = new Subscriber<Bitmap>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("thread_rx_error", e.getMessage().toString());
            }

            @Override
            public void onNext(Bitmap bitmap) {
                Log.e("thread_rx4", Thread.currentThread().getName());
            }
        };
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                // subscriber.onError(new Throwable("onError"));
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });
        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e("subscribe", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e("subscribe", s.toString());
            }
        });


        final Subscription subscribe = Observable.from(files)
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        Log.e("thread_rx1", Thread.currentThread().getName());
                        return Observable.from(file.listFiles());
                    }
                })
                .filter(new Func1<File, Boolean>() {
                    @Override
                    public Boolean call(File file) {
                        Log.e("thread_rx2", Thread.currentThread().getName());
                        return file.getName().endsWith(".png");
                    }
                })
                .map(new Func1<File, Bitmap>() {
                    @Override
                    public Bitmap call(File file) {
                        Log.e("thread_rx3", Thread.currentThread().getName());
                        return BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
//        if (subscribe.isUnsubscribed()){
//            subscribe.unsubscribe();
//        }
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };

        Observable<String> observable1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onStart();
                subscriber.onCompleted();
                subscriber.onError(new Throwable(""));
                subscriber.onNext("");
            }
        });
        observable1.subscribe(observer);

        Observable<String> just = Observable.just("a", "b", "c", "d", "e");
        just.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e("observable_just", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e("observable_just", s);
            }
        });

        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("RxJava_Action", s);
            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("RxJava_Action", throwable.getMessage().toString());
            }
        };
        Action0 onCompleteAction = new Action0() {
            @Override
            public void call() {
                Log.e("RxJava_Action", "onCompleteAction");
            }
        };
        observable.subscribe(onNextAction);
        observable.subscribe(onNextAction, onErrorAction);
        observable.subscribe(onNextAction, onErrorAction, onCompleteAction);

        String args[] = {"a", "b", "c", "d"};
        Observable.from(args).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("test_rxjava", s);
            }
        });
        Observable.from(args).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e("test_rxjava", s);
            }
        });

        ImageView imageView = new ImageView(this);
        final int imgRes = R.mipmap.ic_launcher;
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getTheme().getDrawable(imgRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Drawable drawable) {

            }
        });

        // 假设有一个数据结构『学生』，现在需要打印出一组学生的名字
        List<Student> students = new ArrayList<>();
        Observable.from(students).map(new Func1<Student, String>() {
            @Override
            public String call(Student student) {
                return student.getName();
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d("text_map", s);
            }
        });
        Observable.from(students).flatMap(new Func1<Student, Observable<Course>>() {
            @Override
            public Observable<Course> call(Student student) {
                return student.getCursor;
            }
        }).subscribe(new Subscriber<Course>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Course course) {

            }
        });

        Observable.from(students).lift(new Observable.Operator<String, Student>() {
            @Override
            public Subscriber<? super Student> call(Subscriber<? super String> subscriber) {
                return null;
            }
        });
        List<Integer> integers = new ArrayList<>();
        Observable.from(integers).lift(new Observable.Operator<String, Integer>() {
            @Override
            public Subscriber<? super Integer> call(Subscriber<? super String> subscriber) {
                return new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }
                };
            }
        });*/

        List<Integer> integers = new ArrayList<>();
        Observable.from(integers)
                .lift(new Observable.Operator<String, Integer>() {
                    @Override
                    public Subscriber<? super Integer> call(Subscriber<? super String> subscriber) {
                        return new Subscriber<Integer>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Integer integer) {

                            }
                        };
                    }
                }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        });
    }

    class Student {
        String name;

        public String getName() {
            return name;
        }
    }

}
