package cjx.com.diary.wolf.view.adapter

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import cjx.com.diary.R
import cjx.com.diary.base.DiaryApplication
import cjx.com.diary.wolf.model.role.Role
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * description: 狼人杀列表
 * author: bear .
 * Created date:  2019-06-10.
 */
class WolfAdapter(data: MutableList<Role>?) : BaseQuickAdapter<Role, BaseViewHolder>(R.layout.item_wolf, data) {


    override fun convert(helper: BaseViewHolder?, item: Role?) {
        helper?.let {
            item?.let { role ->
                it.setText(R.id.tv_position, mContext.resources.getString(R.string.wolf_item_title, role.position))
                it.setText(R.id.tv_identity, role.roleName)
                var status: TextView = it.getView(R.id.tv_status)
                var antidote: TextView = it.getView(R.id.tv_antidote)
                var poison: TextView = it.getView(R.id.tv_poison)
                if (role.isAlive) {
                    status.text = mContext.getString(R.string.wolf_item_active)
                    status.setTextColor(ContextCompat.getColor(DiaryApplication.INSTANCE, R.color.color_greenB))
                } else {
                    status.text = mContext.getString(R.string.wolf_item_unactive)
                    status.setTextColor(ContextCompat.getColor(DiaryApplication.INSTANCE, R.color.color_redA))
                }
                antidote.visibility = if (role.hasAntidote) View.VISIBLE else View.GONE
                poison.visibility = if (role.hasPoison) View.VISIBLE else View.GONE
                status.setOnClickListener {
                    role.isAlive = !role.isAlive
                    this.notifyItemChanged(helper.layoutPosition)
                }
                antidote.setOnClickListener {
                    role.hasAntidote = !role.hasAntidote
                    this.notifyItemChanged(helper.layoutPosition)
                }
                poison.setOnClickListener {
                    role.hasPoison = !role.hasPoison
                    this.notifyItemChanged(helper.layoutPosition)
                }
            }
        }
    }

}
