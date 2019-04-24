package cjx.com.diary.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import butterknife.ButterKnife
import cjx.com.diary.R
import cjx.com.diary.base.BaseActivity
import cjx.com.diary.presenter.impl.MainPresenterImp
import cjx.com.diary.util.Utils
import cjx.com.diary.view.fragment.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


    private var fm: FragmentManager? = null

     var homePageFragment: HomePageFragment? = null

     var findFragment: FindFragment? = null

     var weightManagerFragment: WeightManagerFragment? = null

     var photoFragment: PhotoFragment? = null

     var personalFragment: PersonalFragment? = null

     var mCurrentFragmentIndex = 0

    private var mPresenter: MainPresenterImp? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{ item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                switchFragment(0)
                 true
            }
            R.id.navigation_dashboard -> {
                switchFragment(1)
                 true
            }
            R.id.navigation_weight -> {
                switchFragment(2)
                 true
            }
            R.id.navigation_photo -> {
                switchFragment(3)
                 true
            }
            R.id.navigation_notifications -> {
                switchFragment(4)
                 true
            }
        }
        false
    }


    private var mLastBackPress: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        mPresenter = MainPresenterImp()
        mPresenter?.bindView(this, null)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        switchFragment(0)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        switchFragment(0)
    }

    /**
     * 切换fragment
     */
    private fun switchFragment(fragmentIndex: Int) {
        mCurrentFragmentIndex = fragmentIndex
        if (fm == null)
            fm = supportFragmentManager
        val transaction = fm!!.beginTransaction()
        if (homePageFragment == null) {
            homePageFragment = HomePageFragment.newInstance() as HomePageFragment
            transaction.add(R.id.fl_container, homePageFragment)
        }
        if (findFragment == null) {
            findFragment = FindFragment.newInstance() as FindFragment
            transaction.add(R.id.fl_container, findFragment)
        }
        if (weightManagerFragment == null) {
            weightManagerFragment = WeightManagerFragment()
            transaction.add(R.id.fl_container, weightManagerFragment)
        }
        if (photoFragment == null) {
            photoFragment = PhotoFragment.newInstance() as PhotoFragment
            transaction.add(R.id.fl_container, photoFragment)
        }
        if (personalFragment == null) {
            personalFragment = PersonalFragment.newInstance() as PersonalFragment
            transaction.add(R.id.fl_container, personalFragment)
        }
        transaction.hide(homePageFragment).hide(findFragment).hide(weightManagerFragment).hide(photoFragment).hide(personalFragment)
        when (fragmentIndex) {
            0 -> transaction.show(homePageFragment)
            1 -> transaction.show(findFragment)
            2 -> transaction.show(weightManagerFragment)
            3 -> transaction.show(photoFragment)
            4 -> transaction.show(personalFragment)
        }
        transaction.commitAllowingStateLoss()
        navigation.menu.getItem(mCurrentFragmentIndex).isChecked = true
    }

    override fun onBackPressed() {
        val time = System.currentTimeMillis()
        if (time - mLastBackPress < 2000) {
            super.onBackPressed()
        } else {
            mLastBackPress = time
            Utils.showToast(this, getString(R.string.tip_exit))
        }
    }

    companion object {

        fun action(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}
