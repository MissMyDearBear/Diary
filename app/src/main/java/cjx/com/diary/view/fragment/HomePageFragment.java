package cjx.com.diary.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import cjx.com.diary.util.DiaryUtils;
import cjx.com.diary.util.Utils;
import cjx.com.diary.view.activity.DiaryDetailActivity;
import cjx.com.diary.widget.ExpandedView;

/**
 * @author: bear
 * @Description: 主页
 * @date: 2017/5/10
 **/
public class HomePageFragment extends BaseFragment {
    @BindView(R.id.tb_title_bar)
    Toolbar mTitleBar;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.sw_layout)
    SwipeRefreshLayout mSwLayout;
    Unbinder unbinder;

    SearchView mSearchView;

    private List<Diary> mList = new ArrayList<>();

    private MyAdapter mAdapter;


    private String key = "";

    public static Fragment newInstance() {
        return new HomePageFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_homepage, container, false);
        unbinder = ButterKnife.bind(this, view);
        RxBus.get().register(this);
        //在fragment中使用onCreateOptionsMenu时需要在onCrateView中添加此方法，否则不会调用
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitleBar();
        mSwLayout.setOnRefreshListener(() -> onRefreshData());
        mRecycleView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MyAdapter(mList);
        mRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemLongClickListener((baseQuickAdapter, view1, i) -> {
            showDeleteDialog(mList.get(i));
            return true;
        });
        mAdapter.setOnItemClickListener(((baseQuickAdapter, view1, i) -> {
            DiaryDetailActivity.previewDiary(mActivity, mList.get(i).uid);
        }));
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    closeSearchView();
                }
            }
        });
        onRefreshData();
    }

    /**
     * 收起searchView
     */
    private void closeSearchView() {
        if (null != mSearchView && !mSearchView.isIconified()) {
            mSearchView.setIconified(true);
        }
    }

    private void initTitleBar() {
        mTitleBar.setTitle("我的日记");
        mTitleBar.setNavigationIcon(null);
        mTitleBar.setLogo(R.drawable.ic_diary_title);
        //不写的话不会运行onCreateOptionsMenu方法
        mActivity.setSupportActionBar(mTitleBar);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.toolbar_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        SearchView.SearchAutoComplete autoComplete = (SearchView.SearchAutoComplete) mSearchView.findViewById(R.id.search_src_text);
        autoComplete.setTextSize(15);
        mSearchView.setIconified(true);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("请输入标题");

        mSearchView.setOnCloseListener(() -> {
            key = "";
            onRefreshData();
            return false;
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                key = query;
                onRefreshData();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    key = newText;
                    onRefreshData();
                    return true;
                }
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_diary:
                add();
                return true;
        }
        return false;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onPrepareOptionsMenu(menu);
    }

    /**
     * 显示删除弹框
     *
     * @param diary
     */
    private void showDeleteDialog(final Diary diary) {
        new AlertDialog.Builder(mActivity)
                .setTitle("提示")
                .setMessage("确定删除吗？")
                .setPositiveButton("确定", (dialog, which) -> {
                    if (DiaryUtils.delete(diary)) {
                        Utils.showToast(mActivity, "删除成功");
                        onRefreshData();
                    } else {
                        Utils.showToast(mActivity, "删除失败");
                    }
                })
                .setNegativeButton("取消", null)
                .create().show();
    }

    private void onRefreshData() {
        mList.clear();
        mAdapter.notifyDataSetChanged();
        if (DiaryUtils.queryByTitle(key) != null) {
            mList.addAll(DiaryUtils.queryByTitle(key));
            mAdapter.notifyDataSetChanged();
        }
        mSwLayout.setRefreshing(false);
    }

    /**
     * 添加日记
     *
     * @param diary
     */
    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {@Tag(RxBusAction.DIARY_ADD)}
    )
    public void onEvent_addDiary(Diary diary) {
        if (mList != null) {
            if (!mList.contains(diary)) {
                mList.add(0, diary);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 删除日记
     *
     * @param diary
     */
    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {@Tag(RxBusAction.DIARY_DELETE)}
    )
    public void onEvent_deleteDiary(Diary diary) {
        if (mList != null) {
            if (mList.contains(diary)) {
                mList.remove(diary);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 更新日记
     *
     * @param diary
     */
    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {@Tag(RxBusAction.DIARY_UPDATE)}
    )
    public void onEvent_updateDiary(Diary diary) {
        if (mList != null) {
            for (int i = 0; i < mList.size(); i++) {
                if (TextUtils.equals(diary.uid, mList.get(i).uid)) {
                    mList.get(i).title = diary.title;
                    mList.get(i).createDate = diary.createDate;
                    mList.get(i).content = diary.content;
                    mAdapter.notifyItemChanged(i);
                    break;
                }
            }
        }
    }


    /**
     * 添加日记
     */
    private void add() {
        DiaryDetailActivity.addDiary(mActivity);
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
            baseViewHolder.setText(R.id.tv_date, diary.createDate);
            baseViewHolder.setText(R.id.tv_content, diary.content);
        }
    }
}
