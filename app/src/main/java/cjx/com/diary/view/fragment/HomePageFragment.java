package cjx.com.diary.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cjx.com.diary.R;
import cjx.com.diary.base.BaseFragment;
import cjx.com.diary.util.ImageUtils;

/**
 * Created by bear on 2017/4/26.
 */

public class HomePageFragment extends BaseFragment {

    @BindView(R.id.tv_text)
    TextView mTextView;
    Unbinder unbinder;
    @BindView(R.id.image)
    ImageView imageView;

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
//        ImageUtils.getInstance().displayImage(mActivity,imageView,"");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
