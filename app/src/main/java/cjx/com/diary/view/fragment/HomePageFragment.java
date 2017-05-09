package cjx.com.diary.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cjx.com.diary.R;
import cjx.com.diary.base.BaseFragment;
import cjx.com.diary.mode.diary.Diary;
import cjx.com.diary.util.DateUtils;
import cjx.com.diary.util.DiaryUtils;
import cjx.com.diary.util.Utils;

/**
 * Created by bear on 2017/4/26.
 */

public class HomePageFragment extends BaseFragment {
    @BindView(R.id.tb_title_bar)
    Toolbar mTitleBar;
    @BindView(R.id.et_search)
    EditText mSearchEt;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.sw_layout)
    SwipeRefreshLayout mSwLayout;
    Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView mTitleTv;
    @BindView(R.id.tv_extend)
    TextView mExtendTv;
    @BindView(R.id.iv_back)
    ImageView mBackIv;

    private List<Diary>mList=new ArrayList<>();

    private MyAdapter mAdapter;
    public static Fragment newInstance() {
        return new HomePageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_homepage, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitleTv.setText("日记列表");
        mBackIv.setVisibility(View.GONE);
        mExtendTv.setText("添加");
        mExtendTv.setOnClickListener(v -> add());
        mSearchEt.setFocusable(false);
        mSearchEt.setEnabled(false);
        mSwLayout.setOnRefreshListener(() -> onRefreshData());
        mRecycleView.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false));
        mAdapter =new MyAdapter(mList);
        mRecycleView.setAdapter(mAdapter);
        onRefreshData();
    }

    private void onRefreshData() {
        mList.clear();
        mAdapter.notifyDataSetChanged();
       if(DiaryUtils.getDiaryList()!=null){
           mList.addAll(DiaryUtils.getDiaryList());
           mAdapter.notifyDataSetChanged();
       }
        mSwLayout.setRefreshing(false);
    }

    int count=0;

    /**
     * 添加日记
     */
    private void add() {
        count+=1;
        Diary diary = new Diary();
        diary.id= count;
        diary.title = "我的日记一";
        diary.content = "我的日记内容";
        diary.createDate = DateUtils.getCurrentTime();
        DiaryUtils.addDiary(diary);
        onRefreshData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class MyAdapter extends BaseQuickAdapter<Diary, BaseViewHolder> {
        public MyAdapter(@Nullable List<Diary> data) {
            super(R.layout.item_diary, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, Diary diary) {
            baseViewHolder.setText(R.id.tv_title, diary.title);
            baseViewHolder.setText(R.id.tv_content, diary.content);
            baseViewHolder.setText(R.id.tv_date, diary.createDate);

        }
    }
}
