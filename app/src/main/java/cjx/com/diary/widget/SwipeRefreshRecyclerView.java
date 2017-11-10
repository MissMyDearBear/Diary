package cjx.com.diary.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * description:
 * author: bear .
 * Created date:  2017/5/24.
 */
public class SwipeRefreshRecyclerView extends SwipeRefreshLayout {
    public RecyclerView recyclerView;

    public SwipeRefreshRecyclerView(Context context) {
        super(context);
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
         bindRecycleView(context);
    }

    private void bindRecycleView(Context context) {
        recyclerView = new RecyclerView(context);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        recyclerView.setLayoutParams(params);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        this.addView(recyclerView);
    }

    /**
     * 默认不自定义recycleView时调用
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (recyclerView != null) {
            recyclerView.setAdapter(adapter);
        }
    }

    public void addRecyclerView(RecyclerView recyclerView){
        this.removeAllViews();
        this.addView(recyclerView);
    }

    public void setRefreshListener(OnRefreshListener listener){
        this.setOnRefreshListener(listener);
    }
}
