package me.khrystal.leancloudmsg.module.chat.presenter;

import me.khrystal.leancloudmsg.base.BasePresenterImpl;
import me.khrystal.leancloudmsg.module.chat.view.ILaunchView;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/6/8
 * update time:
 * email: 723526676@qq.com
 */
public class ILaunchPresenterImpl extends BasePresenterImpl<ILaunchView,Void>
    implements ILaunchPresenter{

    public ILaunchPresenterImpl(ILaunchView view) {
        super(view);
        mView.initLaunchView();
    }
}
