package cjx.com.diary.widget.dragview;

import android.view.MotionEvent;
import android.view.View;

/**
 * description:
 * author: bear .
 * Created date:  2017/11/17.
 */
public class DragTouchListener implements View.OnTouchListener {


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action=event.getActionMasked();
        switch (action){

        }
        return false;
    }
}
