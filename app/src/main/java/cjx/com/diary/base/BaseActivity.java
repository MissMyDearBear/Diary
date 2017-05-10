package cjx.com.diary.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cjx.com.diary.R;

/**
 * @author: bear
 *
 * @Description: baseActivity
 *
 * @date: 2017/5/10
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView{
    public BaseActivity mActivity;

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
}
