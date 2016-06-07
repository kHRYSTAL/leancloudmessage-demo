package me.khrystal.leancloudmsg.callback;

/**
 * @FileName: me.khrystal.leancloudmsg.callback.RequestCallback.java
 * @Fuction: 网络请求回调接口 通过BasePresenterImpl实现基础
 *              子类可以继承BasePresenterImpl或直接实现该接口
 * @author: kHRYSTAL
 * @email: 723526676@qq.com
 * @date: 2016-03-08 10:57
 * @UpdateUser:
 * @UpdateDate:
 */
public interface RequestCallback<T> {
    /**
     * before request server task
     */
    void beforeRequest();

    /**
     * request error
     */
    void requestError(String msg);

    /**
     * request complete
     */
    void requestComplete();

    /**
     * request Success
     */
    void requestSuccess(T data);
}
