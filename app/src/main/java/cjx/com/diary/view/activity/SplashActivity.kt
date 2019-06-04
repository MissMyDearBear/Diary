package cjx.com.diary.view.activity

import android.os.Bundle
import cjx.com.diary.R
import cjx.com.diary.base.BaseActivity
import cjx.com.diary.presenter.impl.SplashPresenterImpl
import cjx.com.diary.view.SplashView
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * description:
 * author: bear .
 * Created date:  2019-06-04.
 */
class SplashActivity : BaseActivity(), SplashView {

    private val mSplashPresenter: SplashPresenterImpl by lazy {
        SplashPresenterImpl()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mSplashPresenter?.bindView(this, null)
        mSplashPresenter?.setData()
        btn_skip.setOnClickListener { jumpToMainActivity() }
    }


    override fun jumpToMainActivity() {
        MainActivity.action(this)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mSplashPresenter?.clearTimer()
    }
}