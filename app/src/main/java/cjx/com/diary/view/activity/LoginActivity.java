package cjx.com.diary.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cjx.com.diary.R;
import cjx.com.diary.base.BaseActivity;
import cjx.com.diary.presenter.impl.LoginPresenterImpl;
import cjx.com.diary.util.Utils;

/**
 * Created by bear on 2017/4/17.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.act_account)
    AutoCompleteTextView mAccountAct;
    @BindView(R.id.et_psd)
    EditText mPsdEt;
    @BindView(R.id.btn_login)
    Button mLoginBtn;
    @BindView(R.id.btn_register)
    Button mRegisterBtn;
    @BindView(R.id.tv_extend)
    TextView tvExtend;

    private LoginPresenterImpl mLoginPresenter;

    public static void action(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initTitleBar("登录");
        tvExtend.setVisibility(View.VISIBLE);
        tvExtend.setText("退出登录");

        mLoginPresenter = new LoginPresenterImpl();
        mLoginPresenter.bindView(LoginActivity.this, null);

    }

    @OnClick({R.id.btn_login, R.id.btn_register,R.id.tv_extend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String account = mAccountAct.getText().toString();
                String psd = mPsdEt.getText().toString();
                mLoginPresenter.login(account, psd);
                break;
            case R.id.btn_register:
                mLoginPresenter.jumpToRegister(mActivity);
                break;
            case R.id.tv_extend:
                if(mLoginPresenter.logout("user_001").isOk()){
                    Utils.showToast(mActivity,"退出登录成功");
                    finish();
                }else{
                    Utils.showToast(mActivity,"操作失败");
                }
                break;
        }
    }

}
