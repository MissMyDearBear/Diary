package cjx.com.diary.view.fragment

import android.os.Bundle
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
import kotlinx.android.synthetic.main.frag_diary_detail_preview.*
import kotlinx.android.synthetic.main.view_title_bar.*

/**
 * description:
 * author: bear .
 * Created date:  2017/5/12.
 */
class DiaryPreviewFrag : BaseFragment() {

    private var mPresenter: DiaryDetailPresenterImp? = null

    private var id: String? = null


    private val currentDiary: Diary
        get() {
            val diary = Diary()
            diary.uid = id
            diary.title = title
            diary.content = content
            diary.createDate = DateUtils.getCurrentTime()
            return diary
        }

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
        mPresenter!!.bindView(activity as BaseView?, null)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_diary_detail_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveTv = (activity as DiaryDetailActivity).tv_extend
        saveTv.text = "保存"
        saveTv.setOnClickListener {
            val diary = currentDiary
            if (mPresenter!!.update(diary)) {
                Utils.showToast(context, "修改成功！")
            }
        }
        val diary = mPresenter!!.query(id)
        if (diary != null) {
            et_title.setText(diary.title)
            et_content.setText(diary.content)
            tv_date.text = diary.createDate
        }
    }


    companion object {

        fun newInstance(id: String): BaseFragment {
            val frag = DiaryPreviewFrag()
            frag.id = id
            return frag
        }
    }
}
