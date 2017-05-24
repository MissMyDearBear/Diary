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
import cjx.com.diary.mode.ImagesResult;
import cjx.com.diary.util.ImageUtils;
import cjx.com.diary.util.Utils;
import cjx.com.diary.view.activity.ImageDetailActivity;
import cjx.com.diary.widget.SwipeRefreshRecyclerView;
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

    List<ImagesResult.TngouBean> mList = new ArrayList<>();

        final String PREFIX="http://tnfs.tngou.net/image";
//    final String PREFIX = "http://tnfs.tngou.net/img";
//    final String SUFFIX = "_400x600";
    int index = 1;

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
            index = 1;
            mList.clear();
            adapter.notifyDataSetChanged();
            getData();
        });
        adapter = new MyAdapter(mList);
        adapter.setOnItemClickListener((baseQuickAdapter, view1, i) -> {
            ImageDetailActivity.action(mActivity,mList.get(i).title,PREFIX+mList.get(i).img);
        });
        mSwpRecycleView.setAdapter(adapter);
        getData();
    }

    private void getData() {
        ApiService.getApiService().getImages(2, index, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(info -> {
                    if (info != null && info.status) {
                        return info.tngou;
                    } else {
                        return null;
                    }
                }).subscribeWith(new MyObserver<List<ImagesResult.TngouBean>>() {
            @Override
            public void onSuccess(List<ImagesResult.TngouBean> list) {
                if (list != null) {
                    mList.addAll(list);
                    if (index == 1) {
                        adapter.setOnLoadMoreListener(() -> {
                            index += 1;
                            getData();
                        }, mSwpRecycleView.recyclerView);
                    }
                    adapter.disableLoadMoreIfNotFullPage();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String msg) {
                Utils.showToast(mActivity, msg);
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
    }

    private class MyAdapter extends BaseQuickAdapter<ImagesResult.TngouBean, BaseViewHolder> {
        public MyAdapter(@Nullable List<ImagesResult.TngouBean> data) {
            super(R.layout.item_photo, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, ImagesResult.TngouBean imagesResult) {
            ImageView imageView = baseViewHolder.getView(R.id.iv_photo);
            ImageUtils.getInstance().displayImage(mActivity, imageView, PREFIX + imagesResult.img);
            baseViewHolder.setText(R.id.tv_name, imagesResult.title);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
