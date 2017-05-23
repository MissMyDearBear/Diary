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
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cjx.com.diary.R;
import cjx.com.diary.base.BaseFragment;
import cjx.com.diary.mode.QiuBaiBean;
import cjx.com.diary.presenter.impl.FindPresenterImp;
import cjx.com.diary.util.ImageUtils;

/**
 * Created by bear on 2017/4/27.
 */

public class FindFragment extends BaseFragment {
    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.tv_title)
    TextView mTitleTv;
    @BindView(R.id.tv_extend)
    TextView mExtendTv;

    public static Fragment newInstance() {
        return new FindFragment();
    }

    @BindView(R.id.sw_layout)
    SwipeRefreshLayout mSwl;
    @BindView(R.id.tb_title_bar)
    Toolbar tbTitleBar;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    Unbinder unbinder;

    private FindPresenterImp mPresenter;

    private MyAdapter mAdapter;
    List<QiuBaiBean.DataBean.ItemBean> mData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contView = inflater.inflate(R.layout.frag_find, container, false);
        unbinder = ButterKnife.bind(this, contView);
        mPresenter = new FindPresenterImp();
        mPresenter.bindView(this, null);
        return contView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBackIv.setVisibility(View.GONE);
        mTitleTv.setText("趣闻");
        mSwl.setOnRefreshListener(() -> {
            mPresenter.getImageList();
        });
        recycleView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MyAdapter(mData);
        recycleView.setAdapter(mAdapter);
        mPresenter.getImageList();
    }

    public void onRefresh(List<QiuBaiBean.DataBean.ItemBean> tem) {
        if (mActivity == null || mActivity.isFinishing()) return;
        mSwl.setRefreshing(false);
        if (null != tem && tem.size() > 0) {
            mData.clear();
            mData.addAll(tem);
            mAdapter.notifyDataSetChanged();
        }
    }


    private class MyAdapter extends BaseQuickAdapter<QiuBaiBean.DataBean.ItemBean, BaseViewHolder> {
        public MyAdapter(List<QiuBaiBean.DataBean.ItemBean> data) {
            super(R.layout.item_find_images, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, QiuBaiBean.DataBean.ItemBean s) {
            ImageView mImageView = baseViewHolder.getView(R.id.image);
            ImageUtils.getInstance().displayImage(mActivity, mImageView, s.image_url);
            baseViewHolder.setText(R.id.tv_user_name, s.user_name);
            baseViewHolder.setText(R.id.tv_content, s.content);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
