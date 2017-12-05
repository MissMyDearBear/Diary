package cjx.com.diary.widget.imagedetail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * description:
 * author: bear .
 * Created date:  2017/11/28.
 */
public class ImageHelper {
    private static final String TAG = "BearShowImage";

    public static void addFragment(AppCompatActivity activity, View decorView, SparseArray<Integer> viewBound, String url, DragScaleImageView.CallBack callBack) {
        if (activity != null && !activity.isFinishing() && decorView != null) {
            try {
                Fragment fragment = ImageDetailFrag.newInstance(viewBound, url, callBack);
                FragmentManager manager = activity.getSupportFragmentManager();
                manager.beginTransaction().add(android.R.id.content, fragment, TAG).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean finishImageShow(AppCompatActivity activity) {
        if (activity != null && !activity.isFinishing()) {
            try {
                FragmentManager manager = activity.getSupportFragmentManager();
                Fragment fragment = manager.findFragmentByTag(TAG);
                if (fragment != null) {
                    manager.beginTransaction().remove(fragment).commitAllowingStateLoss();
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void goToImageDetail(AppCompatActivity mActivity, ImageView iv, String url) {
        SparseArray<Integer> array = new SparseArray<>();
        int[] ints = new int[2];
        iv.getLocationInWindow(ints);
        array.put(0, ints[0]);
        array.put(1, ints[1]);
        array.put(2, iv.getWidth());
        array.put(3, iv.getHeight());
        iv.setVisibility(View.INVISIBLE);
        ImageHelper.addFragment(mActivity, mActivity.getWindow().getDecorView(), array, url,
                () -> iv.setVisibility(View.VISIBLE)
        );

    }
}
