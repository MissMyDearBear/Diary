package cjx.com.diary.widget.dragview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * description:
 * author: bear .
 * Created date:  2017/11/17.
 */
public class DragImageView extends android.support.v7.widget.AppCompatImageView {
    private DragTouchListener mTouchListener;
    private Bitmap mBitmap;
    private Matrix matrix = new Matrix();
    private float scale;

    public DragImageView(Context context) {
        super(context);
    }

    public DragImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DragImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setImageBitmap(Bitmap bm) {
        initMatrix(bm);
        super.setImageBitmap(bm);
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public Matrix getMatrix() {
        return matrix;
    }
    private void initMatrix(Bitmap bitmap) {
        if (bitmap != null) {
            this.mBitmap = bitmap;
            scale = getWidth() * 1f / bitmap.getWidth();
            matrix.reset();
            matrix.setScale(scale, scale);
            float scaledHeight = bitmap.getHeight() * scale;
            if(scaledHeight<getHeight()){
                float dy = getHeight() - scaledHeight;
                matrix.postTranslate(0,dy/2);
            }
            matrix.set(matrix);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
