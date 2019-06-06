package cjx.com.diary.view.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import bear.com.domain.model.News
import cjx.com.diary.R
import cjx.com.diary.base.BaseFragment
import cjx.com.diary.presenter.impl.FindPresenterImp
import cjx.com.diary.util.Utils
import cjx.com.diary.util.miscreenshot.ScreenShotHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.frag_find.*
import kotlinx.android.synthetic.main.view_title_bar.*
import java.util.*

/**
 * Created by bear on 2017/4/27.
 */

class FindFragment : BaseFragment() {

    private var scrollView: View? = null

    private var mPresenter: FindPresenterImp? = null

    private var mAdapter: MyAdapter? = null
    var mData: MutableList<News> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contView = inflater.inflate(R.layout.frag_find, container, false)
        mPresenter = FindPresenterImp()
        mPresenter?.bindView(this, null)
        return contView
    }

    fun setRefresh(refresh: Boolean) {
        if (sw_layout != null) {
            sw_layout.isRefreshing = refresh
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iv_back.visibility = View.GONE
        tv_title.text = "趣闻"
        tv_extend.visibility = View.VISIBLE
        tv_extend.text = "保存截图"
        tv_extend.setOnClickListener { screenShot() }


        sw_layout.setOnRefreshListener { mPresenter?.getQiuBai() }
        mAdapter = MyAdapter(mData)
        recycle_view.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recycle_view.adapter = mAdapter
        mPresenter?.getQiuBai()
    }

    private fun screenShot() {
        scrollView = ScreenShotHelper.getCanScrollView(view as ViewGroup?)
        ScreenShotHelper.screenShot(activity, scrollView) { _, _ -> activity?.runOnUiThread { Utils.showToast(activity, "保存成功！") } }
    }

    fun onRefresh(tem: List<News>?) {
        if (activity == null || activity!!.isFinishing) return
        sw_layout.isRefreshing = false
        if (null != tem && tem.isNotEmpty()) {
            mData.clear()
            mData.addAll(tem)
            mAdapter?.notifyDataSetChanged()
        }
    }


    private inner class MyAdapter(data: List<News>) : BaseQuickAdapter<News, BaseViewHolder>(R.layout.item_find_images, data) {

        override fun convert(baseViewHolder: BaseViewHolder, s: News) {
            val mImageView = baseViewHolder.getView<ImageView>(R.id.image)
            Glide.with(activity).load(s.image_url).asBitmap().centerCrop().into<BitmapImageViewTarget>(object : BitmapImageViewTarget(mImageView) {
                override fun setResource(resource: Bitmap) {
                    val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(activity!!.resources, resource)
                    circularBitmapDrawable.isCircular = true
                    mImageView.setImageDrawable(circularBitmapDrawable)
                }
            })
            baseViewHolder.setText(R.id.tv_user_name, s.user_name)
            baseViewHolder.setText(R.id.tv_content, s.content)
        }
    }


    companion object {

        fun newInstance(): Fragment {
            return FindFragment()
        }
    }
}
