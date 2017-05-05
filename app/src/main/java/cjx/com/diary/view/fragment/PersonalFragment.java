package cjx.com.diary.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cjx.com.diary.R;
import cjx.com.diary.base.BaseFragment;
import cjx.com.diary.common.Const;
import cjx.com.diary.mode.user.UserBean;
import cjx.com.diary.util.UserUtils;

/**
 * Created by bear on 2017/5/5.
 */

public class PersonalFragment extends BaseFragment {

    @BindView(R.id.tv_git)
    TextView mGitTv;

    public static Fragment newInstance() {
        Fragment fragment = new PersonalFragment();
        return fragment;
    }

    @BindView(R.id.tb_title_bar)
    Toolbar mToolBar;
    @BindView(R.id.iv_header)
    ImageView mHeaderIv;
    @BindView(R.id.tv_name)
    TextView mNameTv;
    @BindView(R.id.tv_homepage_website)
    TextView mWebsiteTv;
    @BindView(R.id.tv_email)
    TextView mEmailTv;
    @BindView(R.id.nes_scroll_view)
    NestedScrollView mNsScrollview;
    Unbinder unbinder;

    private String mEmail = Const.MY_EMAIL;
    private String mName = Const.MY_NAME;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_personal, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        UserBean userBean = UserUtils.getAccountInfo();
        if (userBean != null) {
            mEmail = userBean.email;
            mName = userBean.account;
        }
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWebsiteTv.setText(Const.MY_WEBSITE);
        mEmailTv.setText(mEmail);
        mNameTv.setText(mName);
        mHeaderIv.setImageResource(R.drawable.ic_head);
        mGitTv.setText(Const.MY_GIT_HUB);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
