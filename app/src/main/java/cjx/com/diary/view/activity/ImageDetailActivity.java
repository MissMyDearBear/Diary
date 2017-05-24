package cjx.com.diary.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cjx.com.diary.R;
import cjx.com.diary.base.BaseActivity;
import cjx.com.diary.util.ImageUtils;

/**
 * description:
 * author: bear .
 * Created date:  2017/5/24.
 */
public class ImageDetailActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.tv_title)
    TextView mTitleTv;
    @BindView(R.id.tv_extend)
    TextView mExtendTv;
    @BindView(R.id.iv_photo)
    ImageView mPhotoIv;

    public static void action(Context context,String title,String  url){
        Intent intent=new Intent(context,ImageDetailActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }

    String title;
    String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        ButterKnife.bind(this);
        title=getIntent().getStringExtra("title");
        url=getIntent().getStringExtra("url");
        initTitleBar(title);
        ImageUtils.getInstance().displayImage(mActivity,mPhotoIv,url);
    }
}
