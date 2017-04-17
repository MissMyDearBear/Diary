package cjx.com.diary.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cjx.com.diary.R;
import cjx.com.diary.api.ApiService;
import cjx.com.diary.api.HttpInterface;
import cjx.com.diary.base.BaseActivity;
import cjx.com.diary.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    public static void action(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @BindView(R.id.message)
    TextView mTextMessage;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.iv_photo)
    ImageView mPhotoIv;
    HttpInterface api;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    getData();
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    LoginActivity.action(mActivity);
                    return true;
            }
            return false;
        }
    };
    private String url = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1492049578&di=d83c271d96ddb3ed2f84b99ab11049ea&src=http://npic7.edushi.com/cn/zixun/zh-chs/2017-03/03/3824368-2017030316251198.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        api = ApiService.getInstance();

        mTextMessage = (TextView) findViewById(R.id.message);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        ImageUtils.getInstance().displayImage(mActivity, mPhotoIv, url);
        mTextMessage.setOnClickListener(view -> {
            Utils.showToast(mActivity, mTextMessage.getText().toString());
        });
    }

    private void getData() {
//        api.getData()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableObserver<String>() {
//                    @Override
//                    public void onNext(String value) {
//                        Utils.showToast(mActivity,value);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
        api.getString().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Utils.showToast(mActivity, response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private long mLastBackPress = 0;

    @Override
    public void onBackPressed() {
        long time = System.currentTimeMillis();
        if (time - mLastBackPress < 2000) {
            super.onBackPressed();
        } else {
            mLastBackPress = time;
            Utils.showToast(mActivity, getString(R.string.tip_exit));
        }
    }
}
