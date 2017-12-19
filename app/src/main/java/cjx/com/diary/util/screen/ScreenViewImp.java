package cjx.com.diary.util.screen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.os.Build;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ScrollView;


/**
 * description:
 * author: bear .
 * Created date:  2017/12/19.
 */
public class ScreenViewImp<T> implements ScreenView {
    private T mScreenView;
    private Context mActivity;

    public ScreenViewImp(T view, Context context) {
        this.mScreenView = view;
        mActivity = context;
    }

    @Override
    public Bitmap getBitmap() {
        final Bitmap[] contBmp = {null};
        if (mScreenView instanceof RecyclerView) {
            //TODO 根据自己项目使用的RecyclerView Adapter计算出当前数据能够渲染出来的页面总长度shotHeight,这里写死为1000
            RecyclerView recyclerView= (RecyclerView) mScreenView;
            int shotHeight=1000;
            BitmapUtils.screenShotRecycleView(recyclerView, shotHeight, new ScreenRecyclerView() {
                @Override
                public void onRecFinished(Bitmap bitmap) {
                    contBmp[0]=bitmap;
                }
            });

        } else if (mScreenView instanceof ScrollView) {
            ScrollView scrollView = (ScrollView) mScreenView;
            int h = 0;
            for (int i = 0; i < scrollView.getChildCount(); i++) {
                h += scrollView.getChildAt(i).getHeight();
            }
            try {
                contBmp[0] = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.ARGB_4444);
                Canvas contentC = new Canvas(contBmp[0]);
                scrollView.draw(contentC);
            } catch (Exception e) {
                e.printStackTrace();
                contBmp[0] = null;
            } catch (OutOfMemoryError error) {
                contBmp[0] = null;
            }
        } else if (mScreenView instanceof WebView) {
            WebView webView = (WebView) mScreenView;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //获取webview缩放率
                float scale = webView.getScale();
                //得到缩放后webview内容的高度
                int webViewHeight = (int) (webView.getContentHeight() * scale);
                contBmp[0] = Bitmap.createBitmap(webView.getWidth(), webViewHeight, Bitmap.Config.ARGB_4444);
                Canvas canvas = new Canvas(contBmp[0]);
                //绘制
                webView.draw(canvas);
            } else {
                //获取Picture对象
                Picture picture = webView.capturePicture();
                //得到图片的宽和高（没有reflect图片内容）
                int width = picture.getWidth();
                int height = picture.getHeight();
                if (width > 0 && height > 0) {
                    //创建位图
                    contBmp[0] = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(contBmp[0]);
                    //绘制(会调用native方法，完成图形绘制)
                    picture.draw(canvas);
                } else {
                    contBmp[0] = null;
                }
            }
        } else {
            View view = (View) mScreenView;
            view.setDrawingCacheEnabled(true);
            view.measure(View.MeasureSpec.makeMeasureSpec(ScreenShotUtils.getScreenHightOrWidth(ScreenShotUtils.SCREEN_WIDTH, mActivity), View.MeasureSpec.EXACTLY), View.MeasureSpec.UNSPECIFIED);
            //这个方法也非常重要，设置布局的尺寸和位置
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            //获得绘图缓存中的Bitmap
            view.buildDrawingCache();
            contBmp[0] = view.getDrawingCache();
            Canvas canvas = new Canvas(contBmp[0]);
            view.draw(canvas);
        }
        return contBmp[0];
    }
}
