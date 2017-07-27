package cjx.com.diary.view.activity

import android.content.Context
import android.content.DialogInterface
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
    val role: Array<String> = arrayOf("平民", "狼人", "预言家", "女巫", "猎人","白狼王","白痴神")
    val roleList: ArrayList<Role> = ArrayList()
     var adapter: MyAdapter ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wolf_kill)
        initTitleBar("狼人杀法官助手")
        tv_extend.text = "配置"
        tv_extend.visibility = View.VISIBLE
        tv_extend.setOnClickListener { setPeopleCount() }
        tv_count.text = count.toString() + "人局"
        var manager = GridLayoutManager(mActivity, 3)

        recycle_view.layoutManager = manager
        adapter= MyAdapter(roleList)
        adapter!!.setOnItemClickListener({ adapter, view, position ->
            showSelectIdentityDialog(position)
        })
        recycle_view.adapter=adapter
        initRoleList()

    }


    fun setPeopleCount() {
        val et: EditText = EditText(mActivity)
        et.inputType = InputType.TYPE_CLASS_NUMBER
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("设置").setIcon(android.R.drawable.ic_dialog_info).setView(et)
                .setNegativeButton("Cancel", null)
        builder.setPositiveButton("ok", { dialog, which ->
            if (et.text.toString().isNotEmpty()) {
                count = et.text.toString().toInt()
                tv_count.text = count.toString() + "人局"
                initRoleList()
            }
        })
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
            helper!!.setText(R.id.tv_position,item!!.position.toString()+"号玩家")
            helper!!.setText(R.id.tv_identity,item!!.roleName)
            var status:TextView=helper.getView(R.id.tv_status)
            if(item!!.isAlive){
                status!!.setText("生")
                status!!.setTextColor(ContextCompat.getColor(MyApplication.INSTANCE,R.color.color_greenA))
            }else{
                status!!.setText("死")
                status!!.setTextColor(ContextCompat.getColor(MyApplication.INSTANCE,R.color.color_redA))
            }
            status.setOnClickListener { item.isAlive=!item.isAlive
            this.notifyItemChanged(helper.layoutPosition)}
        }

    }

    fun showSelectIdentityDialog( position:Int){
        val builder:AlertDialog.Builder=AlertDialog.Builder(mActivity)
        builder.setTitle("请选择身份")
        builder.setSingleChoiceItems(role,android.R.layout.simple_list_item_1, { dialog, which ->
           val mRole:Role=roleList[position]
            mRole.roleName=role[which]
            adapter!!.notifyItemChanged(position)
            dialog.dismiss()
        })
        builder.create().show()
    }
    companion object {
        fun action(context: Context) {
            val intent: Intent = Intent(context, WolfKillActivity::class.java)
            context.startActivity(intent)
        }
    }
}