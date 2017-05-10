package cjx.com.diary.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cjx.com.diary.R;
import cjx.com.diary.base.BaseFragment;
import cjx.com.diary.mode.diary.Diary;
import cjx.com.diary.thirdtools.rx.rxbus.RxBus;
import cjx.com.diary.thirdtools.rx.rxbus.RxBusAction;
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

    private List<Diary> mList = new ArrayList<>();

    private MyAdapter mAdapter;

    public static Fragment newInstance() {
        return new HomePageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_homepage, container, false);
        unbinder = ButterKnife.bind(this, view);
        RxBus.get().register(this);
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
        mRecycleView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MyAdapter(mList);
        mRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemLongClickListener((baseQuickAdapter, view1, i) -> {
            showDeleteDialog(mList.get(i));
            return true;
        });
        onRefreshData();
    }

    /**
     * 显示删除弹框
     * @param diary
     */
    private void showDeleteDialog(final Diary diary){
        new AlertDialog.Builder(mActivity)
                .setTitle("提示")
                .setMessage("确定删除吗？")
                .setPositiveButton("确定", (dialog, which) -> {
                    if(DiaryUtils.delete(diary)){
                        Utils.showToast(mActivity,"删除成功");
                        onRefreshData();
                    } else{
                        Utils.showToast(mActivity,"删除失败");
                    }
                })
                .setNegativeButton("取消",null)
                .create().show();
    }

    private void onRefreshData() {
        mList.clear();
        mAdapter.notifyDataSetChanged();
        if (DiaryUtils.getDiaryList() != null) {
            mList.addAll(DiaryUtils.getDiaryList());
            mAdapter.notifyDataSetChanged();
        }
        mSwLayout.setRefreshing(false);
    }

    /**
     * 添加日记
     * @param diary
     */
    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags ={@Tag(RxBusAction.DIARY_ADD)}
    )
    public void onEvent_addDiary(Diary diary){
        if(mList!=null){
            if(!mList.contains(diary)){
               mList.add(0,diary);
               mAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 删除日记
     * @param diary
     */
    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags ={@Tag(RxBusAction.DIARY_DELETE)}
    )
    public void onEvent_deleteDiary(Diary diary){
        if(mList!=null){
            if(mList.contains(diary)){
                mList.remove(diary);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 更新日记
     * @param diary
     */
    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags ={@Tag(RxBusAction.DIARY_UPDATE)}
    )
    public void onEvent_updateDiary(Diary diary){
        if(mList!=null){
            if(mList.contains(diary)){
                for(int i=0;i<mList.size();i++){
                    if(TextUtils.equals(diary.uid,mList.get(i).uid)){
                        mList.get(i).title=diary.title;
                        mList.get(i).createDate=diary.createDate;
                        mList.get(i).content=diary.content;
                        mAdapter.notifyItemChanged(i);
                    }
                }
            }
        }
    }




    /**
     * 添加日记
     */
    private void add() {
        Diary diary = new Diary();
        diary.uid = Utils.getUUID();
        diary.title = "我的日记一";
        diary.content = "我的日记内容";
        diary.createDate = DateUtils.getCurrentTime();
        if (DiaryUtils.addDiary(diary)) {
            Utils.showToast(mActivity, "添加成功！");
//            onRefreshData();
            RxBus.get().post(RxBusAction.DIARY_ADD,diary);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RxBus.get().unregister(this);
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