package cjx.com.diary.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cjx.com.diary.R;
import cjx.com.diary.base.BaseFragment;
import cjx.com.diary.mode.diary.Diary;
import cjx.com.diary.presenter.impl.DiaryDetailPresenterImp;
import cjx.com.diary.util.DateUtils;
import cjx.com.diary.util.Utils;
import cjx.com.diary.view.activity.DiaryDetailActivity;

/**
 * description:
 * author: bear .
 * Created date:  2017/5/11.
 */
public class DiaryAddFrag extends BaseFragment {

    @BindView(R.id.et_title)
    EditText mTitleEt;
    @BindView(R.id.tv_date)
    TextView mDateTv;
    @BindView(R.id.et_content)
    EditText mContentTV;
    Unbinder unbinder;

    public static Fragment newInstance() {
        return new DiaryAddFrag();
    }


    private DiaryDetailPresenterImp mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new DiaryDetailPresenterImp();
        mPresenter.bindView(mActivity, null);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contView = inflater.inflate(R.layout.frag_diary_detail, container, false);
        unbinder = ButterKnife.bind(this, contView);
        return contView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAddView();
    }

    private void initAddView() {
        mDateTv.setVisibility(View.GONE);
        TextView saveTv = ((DiaryDetailActivity) mActivity).getRightTv();
        saveTv.setText("保存");
        saveTv.setOnClickListener((view) -> {
            if (TextUtils.isEmpty(getTitle())) {
                mTitleEt.setError("标题不能为空");
                mTitleEt.requestFocus();
                return;
            }
            Diary diary = new Diary();
            diary.title = getTitle();
            diary.content = getContent();
            diary.createDate = DateUtils.getCurrentTime();
            diary.uid = UUID.randomUUID().toString();
            if (mPresenter.save(diary)) {
                Utils.showToast(mActivity, "添加成功！");
                mActivity.finish();
            }
        });
    }

    /**
     * 获取标题
     *
     * @return
     */
    private String getTitle() {
        return mTitleEt.getText().toString().trim();
    }

    /**
     * 获取内容
     *
     * @return
     */
    private String getContent() {
        return mContentTV.getText().toString().trim();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
