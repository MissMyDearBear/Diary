package cjx.com.diary.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cjx.com.diary.R;
import cjx.com.diary.base.BaseFragment;
import cjx.com.diary.base.BaseView;
import cjx.com.diary.mode.diary.Diary;
import cjx.com.diary.presenter.impl.DiaryDetailPresenterImp;
import cjx.com.diary.util.DateUtils;
import cjx.com.diary.util.Utils;
import cjx.com.diary.view.activity.DiaryDetailActivity;

/**
 * description:
 * author: bear .
 * Created date:  2017/5/12.
 */
public class DiaryPreviewFrag extends BaseFragment {

    @BindView(R.id.et_title)
    TextView mTitleEt;
    @BindView(R.id.tv_date)
    TextView mDateTv;
    @BindView(R.id.et_content)
    TextView mContentEt;
    Unbinder unbinder;
    private DiaryDetailPresenterImp mPresenter;

    public static BaseFragment newInstance(String id) {
        DiaryPreviewFrag frag = new DiaryPreviewFrag();
        frag.id = id;
        return frag;
    }

    private String id;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new DiaryDetailPresenterImp();
        mPresenter.bindView((BaseView) getActivity(), null);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_diary_detail_preview, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView saveTv=((DiaryDetailActivity)getActivity()).getRightTv();
        saveTv.setText("保存");
        saveTv.setOnClickListener((view1)->{
            Diary diary = getCurrentDiary();
            if(mPresenter.update(diary)){
                Utils.showToast(getContext(),"修改成功！");
            }
        });
        Diary diary=mPresenter.query(id);
        if(diary!=null){
            mTitleEt.setText(diary.title);
            mContentEt.setText(diary.content);
            mDateTv.setText(diary.createDate);
        }
    }


    @NonNull
    private Diary getCurrentDiary() {
        Diary diary=new Diary();
        diary.uid=id;
        diary.title=getTitle();
        diary.content=getContent();
        diary.createDate= DateUtils.getCurrentTime();
        return diary;
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
        return mContentEt.getText().toString().trim();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
