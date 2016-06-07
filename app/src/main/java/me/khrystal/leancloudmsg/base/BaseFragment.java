package me.khrystal.leancloudmsg.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;
import me.khrystal.leancloudmsg.annoation.ContentInject;
import me.khrystal.leancloudmsg.app.App;

/**
 * usage: Activity基类
 * author: kHRYSTAL
 * create time: 16/6/8
 * update time:
 * email: 723526676@qq.com
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment
        implements BaseView,View.OnClickListener{

    protected T mPresenter;

    protected View fragmentRootView;

    protected int mContentViewId;


    public BaseFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == fragmentRootView){
            if (getClass().isAnnotationPresent(ContentInject.class)){
                ContentInject annotation = getClass()
                        .getAnnotation(ContentInject.class);
                mContentViewId = annotation.contentViewId();
            }else {
                throw new RuntimeException(
                        "Class must add annotations of ContentInject.class"
                );
            }
            fragmentRootView = inflater.inflate(mContentViewId,container,false);
        }
        return fragmentRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView(fragmentRootView);
    }

    protected abstract void initView(View fragmentRootView);

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter!=null){
            mPresenter.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ViewGroup parent = (ViewGroup)fragmentRootView.getParent();
        if (null!=parent){
            parent.removeView(fragmentRootView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.onDestory();
        }
        // watch memory when fragment destory
        RefWatcher refWatcher = App.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    protected void showSnackbar(String msg){
        Snackbar.make(fragmentRootView,msg,Snackbar.LENGTH_SHORT).show();
    }

    protected void showSnackbar(int id){
        Snackbar.make(fragmentRootView,id,Snackbar.LENGTH_SHORT).show();
    }

    /**
     * BaseView method
     */

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

    @Override
    public void onClick(View v) {

    }
}
