package me.khrystal.leancloudmsg.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import me.khrystal.leancloudmsg.BuildConfig;
import me.khrystal.leancloudmsg.annoation.ContentInject;
import me.khrystal.leancloudmsg.event.EmptyEvent;
import me.khrystal.leancloudmsg.utils.ViewUtil;

/**
 * usage: Activity基类
 * author: kHRYSTAL
 * create time: 16/6/8
 * update time:
 * email: 723526676@qq.com
 */
public abstract class BaseActivity <T extends BasePresenter> extends AppCompatActivity
        implements View.OnClickListener,BaseView{

    protected T mPresenter;

    protected int mContentViewId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getClass().isAnnotationPresent(ContentInject.class)){
            ContentInject annotation = getClass()
                    .getAnnotation(ContentInject.class);
            mContentViewId = annotation.contentViewId();
        } else {
            throw new RuntimeException(
                    "Class must add annotations of ContentInject.class"
            );
        }

        if (BuildConfig.DEBUG){
            StrictMode.setThreadPolicy(
                    new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build()
            );
            StrictMode.setVmPolicy(
                    new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build()
            );
        }


        if (mContentViewId != -1)
            setContentView(mContentViewId);
        else
            throw new RuntimeException(
                    "can not find layout file, the layoutId==-1"
            );
        ButterKnife.bind(this);
        initView();
    }

    protected abstract void initView();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        if (mPresenter != null) mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null) mPresenter.onDestory();
        ViewUtil.fixInputMethodManagerLeak(this);
    }

    protected View getDecorView(){
        return getWindow().getDecorView();
    }

    protected void showSnackbar(String msg){
        Snackbar.make(getDecorView(),msg, Snackbar.LENGTH_SHORT).show();
    }

    protected void showSnackBar(int id){
        Snackbar.make(getDecorView(),id,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void toast(String msg) {
        showSnackbar(msg);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }


    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    protected void startActivity(Class<?> cls, String... objs) {
        Intent intent = new Intent(this, cls);
        for (int i = 0; i < objs.length; i++) {
            intent.putExtra(objs[i], objs[++i]);
        }
        startActivity(intent);
    }

    public void onEvent(EmptyEvent event) {}
}
