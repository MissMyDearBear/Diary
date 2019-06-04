package cjx.com.diary.base

import android.support.v4.app.Fragment

/**
 * description:
 * author: bear .
 * Created date:  2019-06-04.
 */
open class BaseFragment : Fragment(), BaseView{
    fun mActivity(activity: BaseActivity): BaseActivity {
        return activity
    }
}

