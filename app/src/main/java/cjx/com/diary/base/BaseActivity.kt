package cjx.com.diary.base

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import cjx.com.diary.R
import cjx.com.diary.util.permission.PermissionConst

/**
 * description:
 * author: bear .
 * Created date:  2019-06-04.
 */
open class BaseActivity : AppCompatActivity(), BaseView {

    init {
        //用于控制5.0以下的系统可以使用Vector Drawable
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    fun initTitleBar(title: String) {
        val toolbar = findViewById<Toolbar>(R.id.tb_title_bar)
        toolbar?.let {
            setSupportActionBar(it)
            val mTitleTv = findViewById<TextView>(R.id.tv_title)
            val mBackIv = findViewById<ImageView>(R.id.iv_back)
            mTitleTv.text = title
            mBackIv.setOnClickListener { finish() }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var permissionName: String = when (requestCode) {
            PermissionConst.REQUEST_PERMISSION_STORY_WRITE ->
                "存储"
            PermissionConst.REQUEST_WRITE_CONSTACTS_PERMISSIONS ->
                "联系人"
            else ->
                "相关"

        }

        for (i in 0 until grantResults.size) {

            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                //在用户已经拒绝授权的情况下，如果shouldShowRequestPermissionRationale返回false则
                // 可以推断出用户选择了“不在提示”选项，在这种情况下需要引导用户至设置页手动授权
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    //解释原因，并且引导用户至设置页手动授权
                    AlertDialog.Builder(this)
                            .setMessage("获取" + permissionName +
                                    "权限失败,将导致该功能无法正常使用，需要到设置页面手动授权")
                            .setPositiveButton("去授权") { dialog, _ ->
                                //引导用户至设置页手动授权
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri = Uri.fromParts("package", applicationContext.packageName, null)
                                intent.data = uri
                                startActivity(intent)
                                dialog?.dismiss()
                            }
                            .setNegativeButton("取消", null).create().show()
                }
                break
            }
        }
    }
}