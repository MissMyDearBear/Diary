package cjx.com.diary.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cjx.com.diary.R
import cjx.com.diary.base.BaseFragment
import cjx.com.diary.base.BaseView
import cjx.com.diary.mode.diary.Diary
import cjx.com.diary.presenter.impl.DiaryDetailPresenterImp
import cjx.com.diary.util.DateUtils
import cjx.com.diary.util.Utils
import cjx.com.diary.view.activity.DiaryDetailActivity
import kotlinx.android.synthetic.main.frag_diary_detail.*
import kotlinx.android.synthetic.main.view_title_bar.*
import java.util.*

/**
 * description:
 * author: bear .
 * Created date:  2017/5/11.
 */
class DiaryAddFrag : BaseFragment() {


    private var mPresenter: DiaryDetailPresenterImp? = null

    /**
     * 获取标题
     *
     * @return
     */
    private val title: String
        get() = et_title.text.toString().trim { it <= ' ' }

    /**
     * 获取内容
     *
     * @return
     */
    private val content: String
        get() = et_content.text.toString().trim { it <= ' ' }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = DiaryDetailPresenterImp()
        mPresenter!!.bindView(context as BaseView?, null)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contView = inflater.inflate(R.layout.frag_diary_detail, container, false)
        return contView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAddView()
    }

    private fun initAddView() {
        tv_date.visibility = View.GONE
        val saveTv = (activity as DiaryDetailActivity).tv_extend
        saveTv.text = "保存"
        saveTv.setOnClickListener saveTv@{
            if (TextUtils.isEmpty(title)) {
                et_title.error = "标题不能为空"
                et_title.requestFocus()
                return@saveTv
            }
            val diary = Diary()
            diary.title = title
            diary.content = content
            diary.createDate = DateUtils.getCurrentTime()
            diary.uid = UUID.randomUUID().toString()
            if (mPresenter!!.save(diary)) {
                Utils.showToast(context, "添加成功！")
                activity!!.finish()
            }
        }
    }



    companion object {

        fun newInstance(): Fragment {
            return DiaryAddFrag()
        }
    }
}
