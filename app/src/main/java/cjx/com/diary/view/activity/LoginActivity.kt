package cjx.com.diary.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import butterknife.OnClick
import cjx.com.diary.R
import cjx.com.diary.base.BaseActivity
import cjx.com.diary.presenter.impl.LoginPresenterImpl
import cjx.com.diary.util.Utils
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.view_title_bar.*

/**
 * Created by bear on 2017/4/17.
 */

class LoginActivity : BaseActivity() {


    private var mLoginPresenter: LoginPresenterImpl? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initTitleBar("登录")
        tv_extend.visibility = View.VISIBLE
        tv_extend.text = "退出登录"

        mLoginPresenter = LoginPresenterImpl()
        mLoginPresenter!!.bindView(this@LoginActivity, null)

    }

    @OnClick(R.id.btn_login, R.id.btn_register, R.id.tv_extend)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.btn_login -> {
                val account = act_account.text.toString()
                val psd = et_psd.text.toString()
                mLoginPresenter!!.login(account, psd)
            }
            R.id.btn_register -> mLoginPresenter!!.jumpToRegister(this)
            R.id.tv_extend -> if (mLoginPresenter!!.logout("user_001").isOk) {
                Utils.showToast(this, "退出登录成功")
                finish()
            } else {
                Utils.showToast(this, "操作失败")
            }
        }
    }

    companion object {

        fun action(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

}
