package cjx.com.diary.wolf.view.activity

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import cjx.com.diary.R
import cjx.com.diary.base.BaseActivity
import cjx.com.diary.wolf.viewmodel.WolfViewModel
import kotlinx.android.synthetic.main.activity_wolf_record.*

/**
 * description: 复盘记录
 * author: bear .
 * Created date:  2019-06-13.
 */
class WolfRecordActivity : BaseActivity() {
    private val mViewModel: WolfViewModel by lazy {
        ViewModelProviders.of(this).get(WolfViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wolf_record)
        initTitleBar("复盘记录")
        tv_content.text = mViewModel.getRecord()
    }

    companion object {
        @JvmStatic
        fun action(context: Context) {
            val intent = Intent(context, WolfRecordActivity::class.java)
            context.startActivity(intent)
        }
    }

}