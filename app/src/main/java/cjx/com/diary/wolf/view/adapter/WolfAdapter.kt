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


    override fun convert(helper: BaseViewHolder, item: Role?) {
        helper?.let {
            item?.let { role ->
                var policeStr = when (role.police) {
                    1 -> "警长"
                    2 -> "上警"
                    else -> ""
                }
                it.setText(R.id.tv_position, mContext.resources.getString(R.string.wolf_item_title, role.position))
                it.setText(R.id.tv_identity, role.roleName)
                if (policeStr.isEmpty()) {
                    it.setVisible(R.id.tv_police, false)
                } else {
                    it.setVisible(R.id.tv_police, true)
                    it.setText(R.id.tv_police, policeStr)
                }

                val status: TextView = it.getView(R.id.tv_status)
                val antidote: TextView = it.getView(R.id.tv_antidote)
                val poison: TextView = it.getView(R.id.tv_poison)
                if (role.aliveStatus()) {
                    status.text = mContext.getString(R.string.wolf_item_active)
                    status.setTextColor(ContextCompat.getColor(DiaryApplication.INSTANCE, R.color.color_greenB))
                } else {
                    status.text = mContext.getString(R.string.wolf_item_unactive)
                    status.setTextColor(ContextCompat.getColor(DiaryApplication.INSTANCE, R.color.color_redA))
                }
                it.setVisible(R.id.tv_kill, item.isKilled)
                it.setVisible(R.id.tv_check, item.isChecked)
                it.setVisible(R.id.tv_save, item.isSaved)
                it.setVisible(R.id.tv_position2, item.isPositioned)
                it.setVisible(R.id.tv_protect, item.isProtected)
                it.setVisible(R.id.tv_hunter_kill, item.isHunterKilled)
                antidote.visibility = if (role.hasAntidote) View.VISIBLE else View.GONE
                poison.visibility = if (role.hasPoison) View.VISIBLE else View.GONE
            }
        }
    }

}
