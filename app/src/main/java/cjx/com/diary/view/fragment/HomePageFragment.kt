package cjx.com.diary.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.*
import cjx.com.diary.R
import cjx.com.diary.base.BaseActivity
import cjx.com.diary.base.BaseFragment
import cjx.com.diary.mode.diary.Diary
import cjx.com.diary.thirdtools.rx.rxbus.RxBus
import cjx.com.diary.thirdtools.rx.rxbus.RxBusAction
import cjx.com.diary.util.DiaryUtils
import cjx.com.diary.util.Utils
import cjx.com.diary.view.activity.DiaryDetailActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.hwangjr.rxbus.thread.EventThread
import kotlinx.android.synthetic.main.frag_homepage.*
import java.lang.reflect.InvocationTargetException
import java.util.*

/**
 * @author: bear
 * @Description: 主页
 * @date: 2017/5/10
 */
class HomePageFragment : BaseFragment() {

    var mSearchView: SearchView? = null

    private val mList = ArrayList<Diary>()

    private var mAdapter: MyAdapter? = null


    private var key = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_homepage, container, false)
        RxBus.get().register(this)
        //在fragment中使用onCreateOptionsMenu时需要在onCrateView中添加此方法，否则不会调用
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTitleBar()
        sw_layout.setOnRefreshListener { onRefreshData() }
        recycle_view.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mAdapter = MyAdapter(mList)
        recycle_view.adapter = mAdapter
        mAdapter!!.setOnItemLongClickListener { baseQuickAdapter, view1, i ->
            showDeleteDialog(mList[i])
            true
        }
        mAdapter!!.setOnItemClickListener { baseQuickAdapter, view1, i -> DiaryDetailActivity.previewDiary(activity, mList[i].uid) }
        recycle_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    closeSearchView()
                }
            }
        })
        onRefreshData()
    }

    /**
     * 收起searchView
     */
    private fun closeSearchView() {
        if (null != mSearchView && !mSearchView!!.isIconified) {
            mSearchView!!.isIconified = true
        }
    }

    private fun initTitleBar() {
        tb_title_bar.title = "我的日记"
        tb_title_bar.navigationIcon = null
        tb_title_bar.setLogo(R.drawable.ic_diary_title)
        //不写的话不会运行onCreateOptionsMenu方法
        mActivity((activity as BaseActivity?)!!).setSupportActionBar(tb_title_bar)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.toolbar_menu, menu)
        val searchItem = menu!!.findItem(R.id.toolbar_search)
        mSearchView = MenuItemCompat.getActionView(searchItem) as SearchView
        val autoComplete = mSearchView!!.findViewById<View>(R.id.search_src_text) as SearchView.SearchAutoComplete
        autoComplete.textSize = 15f
        mSearchView!!.isIconified = true
        mSearchView!!.isSubmitButtonEnabled = true
        mSearchView!!.queryHint = "请输入标题"

        mSearchView!!.setOnCloseListener {
            key = ""
            onRefreshData()
            false
        }
        mSearchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                key = query
                onRefreshData()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (TextUtils.isEmpty(newText)) {
                    key = newText
                    onRefreshData()
                    return true
                }
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.add_diary -> {
                add()
                return true
            }
        }
        return false
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        if (menu != null) {
            if (menu.javaClass.simpleName == "MenuBuilder") {
                try {
                    val m = menu.javaClass.getDeclaredMethod("setOptionalIconsVisible", java.lang.Boolean.TYPE)
                    m.isAccessible = true
                    m.invoke(menu, true)
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                }

            }
        }
        super.onPrepareOptionsMenu(menu)
    }

    /**
     * 显示删除弹框
     *
     * @param diary
     */
    private fun showDeleteDialog(diary: Diary) {
        AlertDialog.Builder(activity!!)
                .setTitle("提示")
                .setMessage("确定删除吗？")
                .setPositiveButton("确定") { dialog, which ->
                    if (DiaryUtils.delete(diary)) {
                        Utils.showToast(activity, "删除成功")
                        onRefreshData()
                    } else {
                        Utils.showToast(activity, "删除失败")
                    }
                }
                .setNegativeButton("取消", null)
                .create().show()
    }

    private fun onRefreshData() {
        mList.clear()
        mAdapter!!.notifyDataSetChanged()
        if (DiaryUtils.queryByTitle(key) != null) {
            mList.addAll(DiaryUtils.queryByTitle(key))
            mAdapter!!.notifyDataSetChanged()
        }
        sw_layout.isRefreshing = false
    }

    /**
     * 添加日记
     *
     * @param diary
     */
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = [Tag(RxBusAction.DIARY_ADD)])
    fun onEvent_addDiary(diary: Diary) {
        if (mList != null) {
            if (!mList.contains(diary)) {
                mList.add(0, diary)
                mAdapter!!.notifyDataSetChanged()
            }
        }
    }

    /**
     * 删除日记
     *
     * @param diary
     */
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = [Tag(RxBusAction.DIARY_DELETE)])
    fun onEvent_deleteDiary(diary: Diary) {
        if (mList != null) {
            if (mList.contains(diary)) {
                mList.remove(diary)
                mAdapter!!.notifyDataSetChanged()
            }
        }
    }

    /**
     * 更新日记
     *
     * @param diary
     */
    @Subscribe(thread = EventThread.MAIN_THREAD, tags = [Tag(RxBusAction.DIARY_UPDATE)])
    fun onEvent_updateDiary(diary: Diary) {
        if (mList != null) {
            for (i in mList.indices) {
                if (TextUtils.equals(diary.uid, mList[i].uid)) {
                    mList[i].title = diary.title
                    mList[i].createDate = diary.createDate
                    mList[i].content = diary.content
                    mAdapter!!.notifyItemChanged(i)
                    break
                }
            }
        }
    }


    /**
     * 添加日记
     */
    private fun add() {
        DiaryDetailActivity.addDiary(activity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        RxBus.get().unregister(this)
    }

    private inner class MyAdapter(data: List<Diary>?) : BaseQuickAdapter<Diary, BaseViewHolder>(R.layout.item_diary, data) {

        override fun convert(baseViewHolder: BaseViewHolder, diary: Diary) {
            baseViewHolder.setText(R.id.tv_title, diary.title)
            baseViewHolder.setText(R.id.tv_date, diary.createDate)
            baseViewHolder.setText(R.id.tv_content, diary.content)
        }
    }

    companion object {

        fun newInstance(): Fragment {
            return HomePageFragment()
        }
    }
}
