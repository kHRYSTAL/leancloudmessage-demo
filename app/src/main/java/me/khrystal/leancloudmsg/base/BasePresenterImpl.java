package me.khrystal.leancloudmsg.base;

import com.socks.library.KLog;

import me.khrystal.leancloudmsg.callback.RequestCallback;
import rx.Subscription;

/**
 * @FileName: me.khrystal.rxnews.base.BasePresenterImpl.java
 * @Fuction: implements BasePresenter
 * @author: kHRYSTAL
 * @email: 723526676@qq.com
 * @date: 2016-03-08 11:21
 * @UpdateUser:
 * @UpdateDate:
 */
public class BasePresenterImpl<T extends BaseView,V>
        implements BasePresenter,RequestCallback<V>{

    protected Subscription mSubscription;
    protected T mView;

    public BasePresenterImpl(T view){
        mView = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestory() {
        if (mSubscription!=null && !mSubscription.isUnsubscribed()){
            mSubscription.unsubscribe();
        }
        mView = null;
    }

    @Override
    public void beforeRequest() {
        mView.showProgress();
    }

    @Override
    public void requestError(String msg) {
        KLog.e(msg);
        mView.toast(msg);
        mView.hideProgress();
    }

    @Override
    public void requestComplete() {
        mView.hideProgress();
    }

    @Override
    public void requestSuccess(V data) {

    }
}
