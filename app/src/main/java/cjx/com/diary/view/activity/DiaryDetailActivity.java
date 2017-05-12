package cjx.com.diary.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cjx.com.diary.R;
import cjx.com.diary.base.BaseActivity;
import cjx.com.diary.view.fragment.DiaryAddFrag;
import cjx.com.diary.view.fragment.DiaryPreviewFrag;

/**
 * description: 日记详情
 * author: bear .
 * Created date:  2017/5/11.
 */
public class DiaryDetailActivity extends BaseActivity {
    /**
     * 预览动作
     */
    public static final int ACTION_PREVIEW = 0x101;
    /**
     * 添加动作
     */
    public static final int ACTION_ADD = 0x103;
    @BindView(R.id.tv_extend)
    TextView mSaveTv;
    public TextView getRightTv(){
        return mSaveTv;
    }

    Fragment mFragment;

    /**
     * 预览日记
     *
     * @param context 上下文
     * @param diaryId 日记ID
     */
    public static void previewDiary(Context context,String diaryId) {
        Intent intent = new Intent(context, DiaryDetailActivity.class);
        intent.putExtra("action", ACTION_PREVIEW);
        intent.putExtra("diaryId", diaryId);
        context.startActivity(intent);
    }

    public static void addDiary(Context context) {
        Intent intent = new Intent(context, DiaryDetailActivity.class);
        intent.putExtra("action", ACTION_ADD);
        context.startActivity(intent);
    }

    /**
     * 当前的action
     */
    private int mAction;

    private String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_detail);
        ButterKnife.bind(this);

        mAction = getIntent().getIntExtra("action", 0);
        id = getIntent().getStringExtra("diaryId");
        initViewByAction();
    }

    /**
     * 根据action设置view
     */
    private void initViewByAction() {
        if (mAction == ACTION_ADD) {
            initAddView();
        }  else if (mAction == ACTION_PREVIEW) {
            initPreview();
        }
        if(mFragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,mFragment).commit();
        }

    }

    private void initAddView() {
        initTitleBar("添加日记");
        mFragment= DiaryAddFrag.newInstance();
    }

    private void initPreview() {
        initTitleBar("我的日记");
        mFragment= DiaryPreviewFrag.newInstance(id);
    }

}
