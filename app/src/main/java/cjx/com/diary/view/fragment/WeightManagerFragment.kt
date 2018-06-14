package cjx.com.diary.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cjx.com.diary.R
import cjx.com.diary.base.BaseFragment
import cjx.com.diary.mode.weight.BodyWeightBean
import cjx.com.diary.util.DateUtils
import cjx.com.diary.util.Utils
import cjx.com.diary.util.WeightUtils
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.frag_weight_manager.*
import kotlinx.android.synthetic.main.view_title_bar.*

/**
 * description:
 * author: bear .
 * Created date:  2017/7/24.
 */
class WeightManagerFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.frag_weight_manager, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (view != null) {
            super.onViewCreated(view, savedInstanceState)
        }
        tv_title.text = "体重管理"
        tv_extend.text = "重置数据"
        iv_back.visibility = View.GONE
        tv_extend.visibility = View.VISIBLE
        tv_extend.setOnClickListener {
            WeightUtils.clearData()
            showChart()
        }
        tv_today.text = "今日(" + DateUtils.getCurrentDate() + ")"
        tv_update.setOnClickListener {
            updateData()
        }
        showChart()
    }

    fun updateData() {
        if (et_morning.text.isNullOrEmpty() && et_night.text.isNullOrEmpty()) {
            Utils.showToast(mActivity, "请输入要更新的数据")
            return
        }
        WeightUtils.insert(BodyWeightBean(et_morning.text.toString(), et_night.text.toString(), DateUtils.getCurrentDate()))
        showChart()
    }

    private fun showChart() {
        if (WeightUtils.getMAndNList(WeightUtils.TYPE_MORNING) == null || WeightUtils.getMAndNList(WeightUtils.TYPE_MORNING).size == 0) {
            chart.clear()
            return
        }
        val morningDataSet: LineDataSet = LineDataSet(WeightUtils.getMAndNList(WeightUtils.TYPE_MORNING), "上午体重chart")
        val nightDataSet: LineDataSet = LineDataSet(WeightUtils.getMAndNList(WeightUtils.TYPE_NIGHT), "下午体重chart")
        morningDataSet.color = R.color.color_blueA
        morningDataSet.valueTextColor = R.color.color_blueA
        nightDataSet.color = R.color.color_redA
        nightDataSet.valueTextColor = R.color.color_redA
        morningDataSet.axisDependency = YAxis.AxisDependency.LEFT
        nightDataSet.axisDependency = YAxis.AxisDependency.LEFT
        val dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(morningDataSet)
        dataSets.add(nightDataSet)
        val lineData: LineData = LineData(dataSets)
        val des: Description = Description()
        des.text = "体重（单位kg）"
        chart.description = des
        chart.data = lineData
        val xAxis: XAxis = chart.xAxis
        xAxis.granularity = 1f
        xAxis.valueFormatter = WeightUtils.formatter
        xAxis.textColor = R.color.color_orangeB
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.invalidate()
    }
}
