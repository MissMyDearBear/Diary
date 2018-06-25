package cjx.com.diary.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import bear.com.domain.model.News;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cjx.com.diary.R;
import cjx.com.diary.base.BaseFragment;
import cjx.com.diary.presenter.impl.FindPresenterImp;
import cjx.com.diary.util.Utils;
import cjx.com.diary.util.miscreenshot.ScreenShotHelper;

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
    private View scrollView;

    private FindPresenterImp mPresenter;

    private MyAdapter mAdapter;
    List<News> mData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contView = inflater.inflate(R.layout.frag_find, container, false);
        unbinder = ButterKnife.bind(this, contView);
        mPresenter = new FindPresenterImp();
        mPresenter.bindView(this, null);
        return contView;
    }

    public void setRefresh(boolean refresh) {
        if (mSwl != null) {
            mSwl.setRefreshing(refresh);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBackIv.setVisibility(View.GONE);
        mTitleTv.setText("趣闻");

        mExtendTv.setVisibility(View.VISIBLE);
        mExtendTv.setText("保存截图");
        mExtendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenShot();
            }
        });


        mSwl.setOnRefreshListener(() -> {
            mPresenter.getQiuBai();
        });
        recycleView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MyAdapter(mData);
        recycleView.setAdapter(mAdapter);
        mPresenter.getQiuBai();
    }

    private void screenShot() {
        scrollView = ScreenShotHelper.getCanScrollView((ViewGroup) getView());
        ScreenShotHelper.screenShot(mActivity, scrollView, (bitmap, filePath) -> mActivity.runOnUiThread(() -> Utils.showToast(mActivity, "保存成功！")));
    }

    public void onRefresh(List<News> tem) {
        if (mActivity == null || mActivity.isFinishing()) return;
        mSwl.setRefreshing(false);
        if (null != tem && tem.size() > 0) {
            mData.clear();
            mData.addAll(tem);
            mAdapter.notifyDataSetChanged();
        }
    }


    private class MyAdapter extends BaseQuickAdapter<News, BaseViewHolder> {
        public MyAdapter(List<News> data) {
            super(R.layout.item_find_images, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, News s) {
            ImageView mImageView = baseViewHolder.getView(R.id.image);
            Glide.with(mActivity).load(s.image_url).asBitmap().centerCrop().into(new BitmapImageViewTarget(mImageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(mActivity.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    mImageView.setImageDrawable(circularBitmapDrawable);
                }
            });
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
