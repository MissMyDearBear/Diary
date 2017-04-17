package cjx.com.diary.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cjx.com.diary.R;

/**
 * Created by bear on 2017/4/13.
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
        toolbar.setTitle(title);
    }
}
