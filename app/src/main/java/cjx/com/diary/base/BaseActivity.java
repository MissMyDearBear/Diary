package cjx.com.diary.base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import cjx.com.diary.R;
import cjx.com.diary.util.permission.PermissionConst;

/**
 * @author: bear
 *
 * @Description: baseActivity
 *
 * @date: 2017/5/10
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView{
    public BaseActivity mActivity;

    static {
        //用于控制5.0以下的系统可以使用Vector Drawable
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=this;
    }

    public void initTitleBar(String title){
        Toolbar toolbar= (Toolbar) findViewById(R.id.tb_title_bar);
        if(toolbar==null)return;
        setSupportActionBar(toolbar);
        TextView mTitleTv= (TextView) findViewById(R.id.tv_title);
        ImageView mBackIv= (ImageView) findViewById(R.id.iv_back);
        mTitleTv.setText(title);
        mBackIv.setOnClickListener(v -> finish());

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String permissionName;
        switch (requestCode) {
            case PermissionConst.REQUEST_PERMISSION_STORY_WRITE:
                permissionName = "存储";
                break;
            case PermissionConst.REQUEST_WRITE_CONSTACTS_PERMISSIONS:
                permissionName = "联系人";
                break;
            default:
                permissionName = "相关";
                break;
        }
        for (int i = 0; i < grantResults.length; ++i) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                //在用户已经拒绝授权的情况下，如果shouldShowRequestPermissionRationale返回false则
                // 可以推断出用户选择了“不在提示”选项，在这种情况下需要引导用户至设置页手动授权
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    //解释原因，并且引导用户至设置页手动授权
                    new AlertDialog.Builder(this)
                            .setMessage("获取" + permissionName +
                                    "权限失败,将导致该功能无法正常使用，需要到设置页面手动授权")
                            .setPositiveButton("去授权", (dialog, which) -> {
                                //引导用户至设置页手动授权
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                                dialog.dismiss();
                            })
                            .setNegativeButton("取消", null).create().show();
                }
                break;
            }
        }
    }
}
