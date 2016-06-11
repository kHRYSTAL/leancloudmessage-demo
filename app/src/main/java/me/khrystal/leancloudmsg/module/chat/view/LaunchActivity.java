package me.khrystal.leancloudmsg.module.chat.view;

import android.os.Handler;

import me.khrystal.leancloudmsg.R;
import me.khrystal.leancloudmsg.annoation.ContentInject;
import me.khrystal.leancloudmsg.base.BaseActivity;
import me.khrystal.leancloudmsg.module.chat.presenter.ILaunchPresenter;
import me.khrystal.leancloudmsg.module.chat.presenter.ILaunchPresenterImpl;

/**
 * usage: 启动页
 * author: kHRYSTAL
 * create time: 16/6/8
 * update time:
 * email: 723526676@qq.com
 */
@ContentInject(contentViewId = R.layout.activity_launch)
public class LaunchActivity extends BaseActivity<ILaunchPresenter> implements ILaunchView{
    @Override
    protected void initView() {
        mPresenter = new ILaunchPresenterImpl(this);
    }

    @Override
    public void initLaunchView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(LoginActivity.class);
            }
        },1500);
    }
}
