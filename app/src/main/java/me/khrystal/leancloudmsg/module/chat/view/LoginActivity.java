package me.khrystal.leancloudmsg.module.chat.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;

import butterknife.Bind;
import butterknife.OnClick;
import me.khrystal.leancloudmsg.R;
import me.khrystal.leancloudmsg.annoation.ContentInject;
import me.khrystal.leancloudmsg.base.BaseActivity;
import me.khrystal.leancloudmsg.module.chat.presenter.ILaunchPresenter;
import me.khrystal.leancloudmsg.net.LeanCloudClientManager;
import me.khrystal.leancloudmsg.utils.Constants;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/6/8
 * update time:
 * email: 723526676@qq.com
 */
@ContentInject(contentViewId = R.layout.activity_login)
public class LoginActivity extends BaseActivity<ILaunchPresenter> implements ILoginView{

    @Bind(R.id.activity_login_tv_title)
    TextView tvLoginTitle;

    /**
     * 此处 xml 里限制了长度为 30，汉字算一个
     */
    @Bind(R.id.activity_login_et_username)
    EditText etLoginUsername;

    @Bind(R.id.activity_login_btn_login)
    Button btnLogin;

    @OnClick(R.id.activity_login_btn_login)
    public void onLoginClick(View view) {
        openClient(etLoginUsername.getText().toString().trim());
    }

    private void openClient(String selfId) {
        if (TextUtils.isEmpty(selfId)) {
            showSnackbar(R.string.login_null_name_tip);
            return;
        }

        btnLogin.setEnabled(false);
        etLoginUsername.setEnabled(false);
//        登录
        LeanCloudClientManager.getInstance().open(selfId, new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                btnLogin.setEnabled(true);
                etLoginUsername.setEnabled(true);
                if (filterException(e)) {
                    Intent intent = new Intent(LoginActivity.this, SquareActivity.class);
                    intent.putExtra(Constants.CONVERSATION_ID, Constants.SQUARE_CONVERSATION_ID);
                    intent.putExtra(Constants.ACTIVITY_TITLE, getString(R.string.square_name));
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void initView() {

    }
}
