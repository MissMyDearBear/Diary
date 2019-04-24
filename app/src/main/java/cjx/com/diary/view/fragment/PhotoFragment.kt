package cjx.com.diary.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import cjx.com.diary.R
import cjx.com.diary.api.ApiService
import cjx.com.diary.base.BaseFragment
import cjx.com.diary.common.MyObserver
import cjx.com.diary.mode.BaiDuImageBean
import cjx.com.diary.util.ImageUtils
import cjx.com.diary.util.Utils
import cjx.com.diary.util.miscreenshot.ScreenShotHelper
import cjx.com.diary.widget.CustomLoadMoreView
import cjx.com.diary.widget.imagedetail.ImageHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.frag_photo.*
import kotlinx.android.synthetic.main.view_title_bar.*
import java.util.*

/**
 * description: 图片
 * author: bear .
 * Created date:  2017/5/24.
 */
class PhotoFragment : BaseFragment() {


     private val adapter: MyAdapter by lazy {
         MyAdapter(mList) }

     var mList: MutableList<BaiDuImageBean.DataBean> = ArrayList()

     var index = 1
     var curCount = 0
     var isLoadMore = false

    private var scrollView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.frag_photo, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTitleBar()
        swp_recycle_view.setOnRefreshListener {
            isLoadMore = false
            index = 1
            mList.clear()
            adapter.notifyDataSetChanged()
            getData()
        }
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        adapter.setOnLoadMoreListener({
            swp_recycle_view.isRefreshing = false
            isLoadMore = true
            index += 1
            getData()
        }, swp_recycle_view.recyclerView)
        adapter.setLoadMoreView(CustomLoadMoreView())
        swp_recycle_view.setAdapter(adapter)
        getData()
    }

    @SuppressLint("CheckResult")
    private fun getData() {
        curCount = mList.size
        ApiService.getApiService().getBaiDuImage(index - 1, "美女", "全部")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { baiDuImageBean -> baiDuImageBean }
                .subscribeWith<MyObserver<BaiDuImageBean>>(object : MyObserver<BaiDuImageBean>() {
                    override fun onSuccess(baiDuImageBean: BaiDuImageBean?) {
                        if (curCount >= baiDuImageBean!!.totalNum) {
                            adapter.loadMoreEnd(true)
                        }
                        if (baiDuImageBean?.data != null) {
                            adapter.addData(baiDuImageBean.data)
                            curCount = adapter.data.size
                            adapter.loadMoreComplete()
                        }
                        val scrollView = ScreenShotHelper.getCanScrollView(view as ViewGroup?)
                        if (scrollView != null) {
                            Utils.logOut("滑动的View:$scrollView")
                        }
                    }

                    override fun onError(msg: String) {
                        swp_recycle_view.isRefreshing = false
                        adapter.setEnableLoadMore(false)
                    }

                    override fun onFinish() {
                        swp_recycle_view.isRefreshing = false
                    }
                })
    }

    private fun initTitleBar() {
        iv_back.visibility = View.GONE
        tv_title.setText(R.string.title_photo)
        tv_extend.visibility = View.VISIBLE
        tv_extend.text = "保存截图"
        tv_extend.setOnClickListener { _ ->
            scrollView = ScreenShotHelper.getCanScrollView(view as ViewGroup?)
            ScreenShotHelper.screenShot(activity, scrollView) { bitmap, filePath -> activity!!.runOnUiThread { Utils.showToast(activity, "保存成功！") } }
        }
    }

    private inner class MyAdapter(data: List<BaiDuImageBean.DataBean>?) : BaseQuickAdapter<BaiDuImageBean.DataBean, BaseViewHolder>(R.layout.item_photo, data) {

        override fun convert(baseViewHolder: BaseViewHolder, imagesResult: BaiDuImageBean.DataBean) {
            val imageView = baseViewHolder.getView<ImageView>(R.id.iv_photo)
            ImageUtils.getInstance().displayImage(activity, imageView, imagesResult.thumbnail_url)
            baseViewHolder.setText(R.id.tv_name, imagesResult.abs)
            baseViewHolder.itemView.setOnClickListener { v -> ImageHelper.goToImageDetail(activity as AppCompatActivity?, imageView, imagesResult.image_url) }
        }
    }


    companion object {

        fun newInstance(): Fragment {
            return PhotoFragment()
        }
    }
}
