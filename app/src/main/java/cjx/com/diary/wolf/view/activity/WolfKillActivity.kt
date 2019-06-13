package cjx.com.diary.wolf.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.text.InputType
import android.view.View
import android.widget.EditText
import cjx.com.diary.R
import cjx.com.diary.base.BaseActivity
import cjx.com.diary.wolf.model.role.Role
import cjx.com.diary.wolf.model.role.Witch
import cjx.com.diary.wolf.view.adapter.WolfAdapter
import cjx.com.diary.wolf.viewmodel.WolfViewModel
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_wolf_kill.*
import kotlinx.android.synthetic.main.view_title_bar.*

/**
 * description: 狼人杀助手
 * author: bear .
 * Created date:  2017/7/27.
 * Modify date： 2019/6/10
 */
class WolfKillActivity : BaseActivity() {
    private var count = 9 //默认人数（3狼，3民，3神（预言家，女巫，猎人）
    private val role: Array<String> by lazy {
        resources.getStringArray(R.array.role_names)
    }
    private val action: Array<String> by lazy {
        resources.getStringArray(R.array.wolf_action)
    }
    private val roleList: ArrayList<Role> = ArrayList()
    private var adapter: WolfAdapter? = null
    private var isLocked: Boolean = false
    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(WolfViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wolf_kill)
        initView()
        initViewModel()
        mViewModel.productRoleList(count)
        mViewModel.resetData()
    }


    private fun initView() {
        initTitleBar(resources.getString(R.string.wolf_title))
        tv_extend.text = resources.getString(R.string.wolf_setting)
        tv_extend.visibility = View.VISIBLE
        tv_extend.setOnClickListener { setPeopleCount() }
        tv_count.text = resources.getString(R.string.wolf_count, count)
        val manager = GridLayoutManager(this, 3)

        recycle_view.layoutManager = manager
        adapter = WolfAdapter(roleList)
        adapter?.setOnItemClickListener { _, _, position ->
            if (!isLocked) {
                showSelectIdentityDialog(position)
            }
        }
        adapter?.onItemLongClickListener = object : BaseQuickAdapter.OnItemLongClickListener {
            override fun onItemLongClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int): Boolean {
                showActionDialog(position)
                return true
            }
        }
        recycle_view.adapter = adapter
        tv_lock.setOnClickListener {
            if (isLocked) {
                tv_lock.text = resources.getString(R.string.wolf_lock)
                tv_lock.setTextColor(ContextCompat.getColor(this, R.color.color_blueA))
            } else {
                tv_lock.text = resources.getString(R.string.wolf_unlock)
                tv_lock.setTextColor(ContextCompat.getColor(this, R.color.color_redA))
            }
            isLocked = !isLocked
        }

        btn_down.setOnClickListener {
            val curDay:Int= mViewModel.curDay.value?:1
            mViewModel.curDay.setValue(curDay+1)
        }

        btn_record.setOnClickListener {
            WolfRecordActivity.action(this)
        }
    }

    private fun initViewModel() {
        mViewModel.curDay.observe(this, Observer { curDay ->
            curDay?.let {
                tv_cur_night.text = resources.getString(R.string.wolf_record_title, it)
            }
        })

        mViewModel.roleListLiveData.observe(this, Observer { list ->
            list?.let {
                roleList.clear()
                roleList.addAll(it)
                adapter?.notifyDataSetChanged()
                mViewModel.resetData()
            }
        })

    }

    private fun setPeopleCount() {
        val et = EditText(this)
        et.inputType = InputType.TYPE_CLASS_NUMBER
        et.hint = resources.getString(R.string.wolf_setting_dialog_hint)
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.wolf_setting_dialog_title))
                .setIcon(android.R.drawable.ic_dialog_info).setView(et)
                .setNegativeButton(resources.getString(R.string.wolf_setting_dialog_btn_cancel), null)
        builder.setPositiveButton(resources.getString(R.string.wolf_setting_dialog_btn_ok)) { _, _ ->
            if (et.text.toString().isNotEmpty()) {
                count = et.text.toString().toInt()
                tv_count.text = resources.getString(R.string.wolf_count, count)
                mViewModel.productRoleList(count)
            }
        }
        builder.show()
    }


    private fun showSelectIdentityDialog(position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.wolf_dialog_title))
        builder.setSingleChoiceItems(role, android.R.layout.simple_list_item_1) { dialog, which ->
            val mRole: Role = roleList[position]
            mRole.roleName = role[which]
            if (mRole.roleName == Witch.NAME) {
                mRole.hasAntidote = true
                mRole.hasPoison = true
            } else {
                mRole.hasAntidote = false
                mRole.hasPoison = false
            }
            adapter?.notifyItemChanged(position)
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun showActionDialog(position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.wolf_dialog_title2))
        builder.setSingleChoiceItems(action, android.R.layout.simple_list_item_1) { dialog, which ->
            val mRole: Role = roleList[position]
            val actionName = action[which]
            when (actionName) {
                "上警" -> mRole.police = 2
                "授予警长" -> mRole.police = 1
                else -> mRole.police = -1
            }
            mViewModel.addRecord(actionName, position+1)
            adapter?.notifyItemChanged(position)
            dialog.dismiss()
        }
        builder.create().show()
    }

    companion object {
        fun action(context: Context) {
            val intent = Intent(context, WolfKillActivity::class.java)
            context.startActivity(intent)
        }
    }
}