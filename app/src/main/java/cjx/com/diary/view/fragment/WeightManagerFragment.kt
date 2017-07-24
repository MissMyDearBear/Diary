package cjx.com.diary.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cjx.com.diary.R
import cjx.com.diary.base.BaseFragment
import kotlinx.android.synthetic.main.view_title_bar.*

/**
 * description:
 * author: bear .
 * Created date:  2017/7/24.
 */
class WeightManagerFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.frag_weight_manager, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       tv_title.text = "体重管理"
       iv_back.visibility = View.GONE
       tv_extend.visibility = View.GONE
    }
}
