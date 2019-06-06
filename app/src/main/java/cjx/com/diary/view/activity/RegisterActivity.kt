package cjx.com.diary.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import butterknife.OnClick
import cjx.com.diary.R
import cjx.com.diary.base.BaseActivity
import cjx.com.diary.presenter.RegisterPresenter
import cjx.com.diary.presenter.impl.RegisterPresentImpl
import cjx.com.diary.util.Utils
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.view_title_bar.*

/**
 * description:
 * author: bear .
 * Created date:  2018/6/20.
 */
class RegisterActivity : BaseActivity() {

    private var mRegisterP: RegisterPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initTitleBar("注册")
        mRegisterP = RegisterPresentImpl()
        mRegisterP?.bindView(this, null!!)
        tv_extend.visibility = View.VISIBLE
        tv_extend.text = "清楚用户数据"
        tv_extend.setOnClickListener { view -> mRegisterP!!.removeAllUser() }
    }

    @OnClick(R.id.btn_register)
    fun onViewClicked() {
        val account = act_account.text.toString()
        val psd = et_psd.text.toString()
        val response = mRegisterP!!.register(account, psd)
        if (response != null) {
            Utils.showToast(this, response.message)
            if (response.isOk) {
                finish()
            } else {
                act_account.setText("")
                et_psd.setText("")
            }
        }

    }

    companion object {

        fun action(context: Context) {
            context.startActivity(Intent(context, RegisterActivity::class.java))
        }
    }
}
