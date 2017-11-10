package cjx.com.diary.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cjx.com.diary.R;
import cjx.com.diary.base.BaseActivity;
import cjx.com.diary.util.ImageUtils;
import cjx.com.diary.widget.DragLinearLayout;

/**
 * description:
 * author: bear .
 * Created date:  2017/5/24.
 */
public class ImageDetailActivity extends BaseActivity {
    @BindView(R.id.dl_content)
    DragLinearLayout mDrag;
    @BindView(R.id.iv_photo)
    ImageView mPhotoIv;

    public static void action(Activity context, String url) {
        Intent intent = new Intent(context, ImageDetailActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);
    }

    String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        ImageUtils.getInstance().displayImage(mActivity, mPhotoIv, url);
        mDrag.initDrag((releasedChild, xvel, yvel) -> {
            if (Math.abs(yvel) >= 50) {
                mDrag.postDelayed(() -> {
                    finish();
                    mActivity.overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);
                }, 60);
            }
        });
    }
}
