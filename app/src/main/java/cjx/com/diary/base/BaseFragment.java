package cjx.com.diary.base;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by bear on 2017/4/26.
 */

public abstract class BaseFragment extends Fragment implements BaseView {
    public BaseActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }
}
