package cjx.com.diary.base;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * @author: bear
 *
 * @Description: BaseFragments
 *
 * @date: 2017/5/10
 */
public abstract class BaseFragment extends Fragment implements BaseView {
    public BaseActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }
}
