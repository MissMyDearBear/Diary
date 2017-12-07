package cjx.com.diary.widget.imagedetail;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cjx.com.diary.R;
import cjx.com.diary.util.ImageUtils;

/**
 * description:
 * author: bear .
 * Created date:  2017/11/28.
 */
public class ImageDetailFrag extends Fragment {
    /**
     * 在前一个页面的位置
     */
    private SparseArray<Integer> originalBound;

    private String imageUrl;

    private DragScaleImageView mImageView;

    private RelativeLayout relativeLayout;

    private DragScaleImageView.CallBack mCallBack;


    public static Fragment newInstance(SparseArray<Integer> viewBound, String url, DragScaleImageView.CallBack callBack) throws Exception {
        ImageDetailFrag fragment = new ImageDetailFrag();
        fragment.originalBound = viewBound;
        fragment.imageUrl = url;
        fragment.mCallBack = callBack;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_imagedetail, container, false);
        mImageView = (DragScaleImageView) rootView.findViewById(R.id.iv_show);
        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.rl_content);
        mImageView.setSparseArray(originalBound);
        mImageView.setmCallBack(mCallBack);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageUtils.getInstance().displayImage(this.getContext(), mImageView, imageUrl);
        relativeLayout.setOnClickListener(v -> {
            if (mImageView != null) {
                mImageView.dismissImage();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        relativeLayout.setBackgroundColor(0xff000000);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            relativeLayout.setBackgroundColor(0xff000000);
        }
    }
}
