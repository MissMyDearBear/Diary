package cjx.com.diary.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import cjx.com.diary.api.ApiService;
import cjx.com.diary.base.BaseFragment;
import cjx.com.diary.common.MyObserver;
import cjx.com.diary.mode.BaiDuImageBean;
import cjx.com.diary.util.ImageUtils;
import cjx.com.diary.util.Utils;
import cjx.com.diary.util.miscreenshot.ScreenShotHelper;
import cjx.com.diary.widget.CustomLoadMoreView;
import cjx.com.diary.widget.SwipeRefreshRecyclerView;
import cjx.com.diary.widget.imagedetail.ImageHelper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * description: 图片
 * author: bear .
 * Created date:  2017/5/24.
 */
public class PhotoFragment extends BaseFragment {

    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.tv_title)
    TextView mTitleTv;
    @BindView(R.id.tv_extend)
    TextView mExtendTv;
    @BindView(R.id.swp_recycle_view)
    SwipeRefreshRecyclerView mSwpRecycleView;
    Unbinder unbinder;


    MyAdapter adapter;

    List<BaiDuImageBean.DataBean> mList = new ArrayList<>();

    int index = 1;
    int curCount = 0;
    boolean isLoadMore = false;

    private View scrollView;

    public static Fragment newInstance() {
        return new PhotoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_photo, container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitleBar();
        mSwpRecycleView.setOnRefreshListener(() -> {
            isLoadMore = false;
            index = 1;
            mList.clear();
            adapter.notifyDataSetChanged();
            getData();
        });
        adapter = new MyAdapter(mList);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.setOnLoadMoreListener(() -> {
            mSwpRecycleView.setRefreshing(false);
            isLoadMore = true;
            index += 1;
            getData();
        }, mSwpRecycleView.recyclerView);
        adapter.setLoadMoreView(new CustomLoadMoreView());
        mSwpRecycleView.setAdapter(adapter);
        getData();
    }

    private void getData() {
        curCount = mList.size();
        ApiService.getApiService().getBaiDuImage(index - 1, "美女", "全部")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(baiDuImageBean -> baiDuImageBean)
                .subscribeWith(new MyObserver<BaiDuImageBean>() {
                    @Override
                    public void onSuccess(BaiDuImageBean baiDuImageBean) {
                        if (curCount >= baiDuImageBean.totalNum) {
                            adapter.loadMoreEnd(true);
                        }
                        if (baiDuImageBean != null && baiDuImageBean.data != null) {
                            adapter.addData(baiDuImageBean.data);
                            curCount = adapter.getData().size();
                            adapter.loadMoreComplete();
                        }
                        View scrollView = ScreenShotHelper.getCanScrollView((ViewGroup) getView());
                        if (scrollView != null) {
                            Utils.logOut("滑动的View:" + scrollView.toString());
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        mSwpRecycleView.setRefreshing(false);
                        adapter.setEnableLoadMore(false);
                    }

                    @Override
                    public void onFinish() {
                        mSwpRecycleView.setRefreshing(false);
                    }
                });
    }

    private void initTitleBar() {
        mBackIv.setVisibility(View.GONE);
        mTitleTv.setText(R.string.title_photo);
        mExtendTv.setVisibility(View.VISIBLE);
        mExtendTv.setText("保存截图");
        mExtendTv.setOnClickListener(v -> {
            scrollView = ScreenShotHelper.getCanScrollView((ViewGroup) getView());
            ScreenShotHelper.screenShot(mActivity, scrollView, (bitmap, filePath) -> mActivity.runOnUiThread(() -> Utils.showToast(mActivity,"保存成功！")));
        });
    }

    private class MyAdapter extends BaseQuickAdapter<BaiDuImageBean.DataBean, BaseViewHolder> {
        public MyAdapter(@Nullable List<BaiDuImageBean.DataBean> data) {
            super(R.layout.item_photo, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, BaiDuImageBean.DataBean imagesResult) {
            ImageView imageView = baseViewHolder.getView(R.id.iv_photo);
            ImageUtils.getInstance().displayImage(mActivity, imageView, imagesResult.thumbnail_url);
            baseViewHolder.setText(R.id.tv_name, imagesResult.abs);
            baseViewHolder.itemView.setOnClickListener(v -> ImageHelper.goToImageDetail(mActivity, imageView, imagesResult.image_url));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
