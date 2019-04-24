package cjx.com.diary.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cjx.com.diary.R
import cjx.com.diary.base.BaseFragment
import cjx.com.diary.common.Const
import cjx.com.diary.util.UserUtils
import cjx.com.diary.view.activity.LoginActivity
import cjx.com.diary.wolf.view.activity.WolfKillActivity
import kotlinx.android.synthetic.main.frag_personal.*
import kotlinx.android.synthetic.main.view_title_bar.*

/**
 * Created by bear on 2017/5/5.
 */

class PersonalFragment : BaseFragment() {


    private var mEmail = Const.MY_EMAIL
    private var mName = Const.MY_NAME

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.frag_personal, container, false)
        val userBean = UserUtils.getAccountInfo()
        if (userBean != null) {
            mEmail = userBean.email
            mName = userBean.name
        }
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iv_back.visibility = View.GONE
        tv_title.text = "关于我"
        tv_extend.visibility = View.VISIBLE
        tv_extend.text = "框架示例"
        tv_extend.setOnClickListener {  LoginActivity.action(activity!!) }
        tv_homepage_website.text = Const.MY_WEBSITE
        tv_email.text = mEmail
        tv_name.text = mName
        iv_header.setImageResource(R.drawable.ic_head)
        tv_git.text = Const.MY_GIT_HUB
        iv_wolf_kill.setOnClickListener { WolfKillActivity.action(activity!!) }
    }


    companion object {

        fun newInstance(): Fragment {
            return PersonalFragment()
        }
    }

}
