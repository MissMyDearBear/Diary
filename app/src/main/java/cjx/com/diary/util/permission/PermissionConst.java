package cjx.com.diary.util.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


/**
 * 说明:${适配高版本Android系统静态变量，以及相关方法}
 * 日期:2017/12/22
 * 时间:11:18
 * 创建者：bear
 * 修改者：bear
 **/
public class PermissionConst {

    public static final int REQUEST_PERMISSION_STORY_WRITE = 1101;//请求读权限

    public static final int REQUEST_WRITE_CONSTACTS_PERMISSIONS = 1102;//请求获取写入通讯录的权限
    public static final int REQUEST_READ_PHONE_STATE_PERMISSIONS = 1103;//请求获取写入通讯录的权限

    public static boolean canUseThisPermission(final Activity mActivity, String permissionName, final String permission, final int request) {
        int permissionCheck = ContextCompat.checkSelfPermission(mActivity, permission);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission)) {
                if (mActivity != null && !mActivity.isFinishing()) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                    builder.setTitle("权限设置")
                            .setMessage("请开通" + permissionName +
                                    "权限再使用该功能！")
                            .setPositiveButton("开启", (dialog, which) -> {
                                ActivityCompat.requestPermissions(mActivity, new String[]{permission}, request);
                                dialog.dismiss();
                            })
                            .setNegativeButton("取消", null).
                            create().show();
                }
            } else {
                ActivityCompat.requestPermissions(mActivity, new String[]{permission}, request);
            }
            return false;
        } else {
            return true;
        }
    }

}
