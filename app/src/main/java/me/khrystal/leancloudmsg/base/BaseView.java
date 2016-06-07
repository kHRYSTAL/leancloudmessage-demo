package me.khrystal.leancloudmsg.base;

/**
 * usage: View基类
 *        Activity和Fragment需要实现此接口子类供Presenter调用
 * author: kHRYSTAL
 * create time: 16/6/8
 * update time:
 * email: 723526676@qq.com
 */
public interface BaseView {

    void toast(String msg);

    void showProgress();

    void hideProgress();
}
