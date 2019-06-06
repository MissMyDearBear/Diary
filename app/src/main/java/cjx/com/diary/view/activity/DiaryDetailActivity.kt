package cjx.com.diary.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import butterknife.ButterKnife
import cjx.com.diary.R
import cjx.com.diary.base.BaseActivity
import cjx.com.diary.view.fragment.DiaryAddFrag
import cjx.com.diary.view.fragment.DiaryPreviewFrag

/**
 * description: 日记详情
 * author: bear .
 * Created date:  2017/5/11.
 */
class DiaryDetailActivity : BaseActivity() {

    var mFragment: Fragment? = null

    /**
     * 当前的action
     */
    private var mAction: Int = 0

    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_detail)
        ButterKnife.bind(this)

        mAction = intent.getIntExtra("action", 0)
        id = intent.getStringExtra("diaryId")
        initViewByAction()
    }

    /**
     * 根据action设置view
     */
    private fun initViewByAction() {
        if (mAction == ACTION_ADD) {
            initAddView()
        } else if (mAction == ACTION_PREVIEW) {
            initPreview()
        }
        if (mFragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.fl_container, mFragment).commit()
        }

    }

    private fun initAddView() {
        initTitleBar("添加日记")
        mFragment = DiaryAddFrag.newInstance()
    }

    private fun initPreview() {
        initTitleBar("我的日记")
        mFragment = DiaryPreviewFrag.newInstance(id!!)
    }

    companion object {
        /**
         * 预览动作
         */
        val ACTION_PREVIEW = 0x101
        /**
         * 添加动作
         */
        val ACTION_ADD = 0x103

        /**
         * 预览日记
         *
         * @param context 上下文
         * @param diaryId 日记ID
         */
        fun previewDiary(context: Context, diaryId: String) {
            val intent = Intent(context, DiaryDetailActivity::class.java)
            intent.putExtra("action", ACTION_PREVIEW)
            intent.putExtra("diaryId", diaryId)
            context.startActivity(intent)
        }

        fun addDiary(context: Context) {
            val intent = Intent(context, DiaryDetailActivity::class.java)
            intent.putExtra("action", ACTION_ADD)
            context.startActivity(intent)
        }
    }

}
