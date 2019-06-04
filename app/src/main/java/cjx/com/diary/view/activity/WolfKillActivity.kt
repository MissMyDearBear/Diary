package cjx.com.diary.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.TextView
import cjx.com.diary.R
import cjx.com.diary.base.BaseActivity
import cjx.com.diary.base.MyApplication
import cjx.com.diary.mode.wolf.Role
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.activity_wolf_kill.*
import kotlinx.android.synthetic.main.view_title_bar.*

/**
 * description: 狼人杀助手
 * author: bear .
 * Created date:  2017/7/27.
 */
class WolfKillActivity : BaseActivity() {
    var count = 10
    val role: Array<String> = arrayOf("平民", "狼人", "预言家", "女巫", "猎人", "白狼王", "白痴神")
    val roleList: ArrayList<Role> = ArrayList()
    var adapter: MyAdapter? = null
    var isLocked: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wolf_kill)
        initTitleBar("狼人杀法官助手")
        tv_extend.text = "配置"
        tv_extend.visibility = View.VISIBLE
        tv_extend.setOnClickListener { setPeopleCount() }
        tv_count.text = count.toString() + "人局"
        var manager = GridLayoutManager(this, 3)

        recycle_view.layoutManager = manager
        adapter = MyAdapter(roleList)
        adapter!!.setOnItemClickListener { _, _, position ->
            if (!isLocked) {
                showSelectIdentityDialog(position)
            }
        }
        recycle_view.adapter = adapter
        tv_lock.setOnClickListener {
            if (isLocked) {
                tv_lock.setText("锁定")
                tv_lock!!.setTextColor(ContextCompat.getColor(this, R.color.color_blueA))
            } else {
                tv_lock.setText("解锁")
                tv_lock!!.setTextColor(ContextCompat.getColor(this, R.color.color_redA))
            }
            isLocked = !isLocked
        }
        initRoleList()

    }


    fun setPeopleCount() {
        val et: EditText = EditText(this)
        et.inputType = InputType.TYPE_CLASS_NUMBER
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("设置").setIcon(android.R.drawable.ic_dialog_info).setView(et)
                .setNegativeButton("Cancel", null)
        builder.setPositiveButton("ok") { _, _ ->
            if (et.text.toString().isNotEmpty()) {
                count = et.text.toString().toInt()
                tv_count.text = count.toString() + "人局"
                initRoleList()
            }
        }
        builder.show()
    }

    fun initRoleList() {
        roleList.clear()
        var i = 0
        while (i < count) {
            val role: Role = Role()
            role.roleName = "平民"
            role.isAlive = true
            role.position = i + 1
            roleList.add(role)
            i++
        }
        adapter!!.notifyDataSetChanged()
    }

    class MyAdapter(data: MutableList<Role>?) : BaseQuickAdapter<Role, BaseViewHolder>(R.layout.item_wolf, data) {
        override fun convert(helper: BaseViewHolder?, item: Role?) {
            //To change body of created functions use File | Settings | File Templates.
            helper!!.setText(R.id.tv_position, item!!.position.toString() + "号玩家")
            helper!!.setText(R.id.tv_identity, item!!.roleName)
            var status: TextView = helper.getView(R.id.tv_status)
            var antidote: TextView = helper.getView(R.id.tv_antidote)
            var poison: TextView = helper.getView(R.id.tv_poison)
            if (item!!.isAlive) {
                status!!.setText("生")
                status!!.setTextColor(ContextCompat.getColor(MyApplication.INSTANCE, R.color.color_greenB))
            } else {
                status!!.setText("死")
                status!!.setTextColor(ContextCompat.getColor(MyApplication.INSTANCE, R.color.color_redA))
            }
            antidote.visibility = if (item.hasAntidote) View.VISIBLE else View.GONE
            poison.visibility = if (item.hasPoison) View.VISIBLE else View.GONE
            status.setOnClickListener {
                item.isAlive = !item.isAlive
                this.notifyItemChanged(helper.layoutPosition)
            }
            antidote.setOnClickListener {
                item.hasAntidote = !item.hasAntidote
                this.notifyItemChanged(helper.layoutPosition)
            }
            poison.setOnClickListener {
                item.hasPoison = !item.hasPoison
                this.notifyItemChanged(helper.layoutPosition)
            }
        }

    }

    fun showSelectIdentityDialog(position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("请选择身份")
        builder.setSingleChoiceItems(role, android.R.layout.simple_list_item_1) { dialog, which ->
            val mRole: Role = roleList[position]
            mRole.roleName = role[which]
            if (mRole.roleName.equals("女巫")) {
                mRole.hasAntidote = true
                mRole.hasPoison = true
            } else {
                mRole.hasAntidote = false
                mRole.hasPoison = false
            }
            adapter!!.notifyItemChanged(position)
            dialog.dismiss()
        }
        builder.create().show()
    }

    companion object {
        fun action(context: Context) {
            val intent: Intent = Intent(context, WolfKillActivity::class.java)
            context.startActivity(intent)
        }
    }
}