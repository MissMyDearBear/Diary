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

import bear.com.domain.repository.Response;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cjx.com.diary.R;
import cjx.com.diary.base.BaseActivity;
import cjx.com.diary.presenter.RegisterPresenter;
import cjx.com.diary.presenter.impl.RegisterPresentImpl;
import cjx.com.diary.util.Utils;

/**
 * description:
 * author: bear .
 * Created date:  2018/6/20.
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.tv_extend)
    TextView tvExtend;
    @BindView(R.id.act_account)
    AutoCompleteTextView actAccount;
    @BindView(R.id.et_psd)
    EditText etPsd;
    @BindView(R.id.btn_register)
    Button btnRegister;

    private RegisterPresenter mRegisterP;

    public static void action(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initTitleBar("注册");
        mRegisterP = new RegisterPresentImpl();
        mRegisterP.bindView(this, null);
        tvExtend.setVisibility(View.VISIBLE);
        tvExtend.setText("清楚用户数据");
        tvExtend.setOnClickListener(view -> mRegisterP.removeAllUser());
    }

    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        String account = actAccount.getText().toString();
        String psd = etPsd.getText().toString();
        Response response = mRegisterP.register(account, psd);
        if (response != null) {
            Utils.showToast(this, response.message);
            if (response.isOk()) {
                finish();
            } else {
                actAccount.setText("");
                etPsd.setText("");
            }
        }

    }
}
