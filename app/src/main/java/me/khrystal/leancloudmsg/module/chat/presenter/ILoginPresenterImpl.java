package me.khrystal.leancloudmsg.module.chat.presenter;

import me.khrystal.leancloudmsg.base.BasePresenterImpl;
import me.khrystal.leancloudmsg.module.chat.view.ILoginView;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/6/8
 * update time:
 * email: 723526676@qq.com
 */
public class ILoginPresenterImpl extends BasePresenterImpl<ILoginView,Void>
        implements ILaunchPresenter{

    public ILoginPresenterImpl(ILoginView view) {
        super(view);
    }
}
