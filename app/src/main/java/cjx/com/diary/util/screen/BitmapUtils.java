package cjx.com.diary.util.screen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cjx.com.diary.R;

/**
 * description: bitmap处理工具
 * author: bear .
 * Created date:  2017/12/19.
 */
public class BitmapUtils {
    //截图或者保存图片回调接口
    public interface BitmapAndFileCallBack {
        //如果bitmap返回不是null则证明成功，否则失败
        void onResult(Bitmap bitmap, String filePath);
    }


    private static Bitmap combineBitmapsIntoOnlyOne(Context context, List<Bitmap> bitmaps, int totalWidth, int maxHeight) {
        Bitmap bitmap = null;
        int height = 0;
        if (bitmaps != null && bitmaps.size() > 0) {
            for (Bitmap map : bitmaps) {
                if (map != null) {
                    height += map.getHeight();
                }
            }
            if (height > maxHeight) {
                return null;
            }
            bitmap = Bitmap.createBitmap(totalWidth, height, Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(ContextCompat.getColor(context, R.color.color_content_bg));
            int drawHeight = 0;
            for (Bitmap map : bitmaps) {
                if (map != null) {
                    canvas.drawBitmap(map, 0, drawHeight, null);
                    drawHeight += map.getHeight();
                }
            }
        }
        return bitmap;
    }

    /**
     * 将传入的Bitmap合理压缩后输出到系统截屏目录下
     * 命名格式为：Screenshot+时间戳+启信宝报名.jpg
     * 同时通知系统重新扫描系统文件
     *
     * @param context 用于通知重新扫描文件系统，为提升性能可去掉
     */
    public static void savingBitmapIntoFile(final Context context, final Bitmap only_bitmap, final BitmapAndFileCallBack callBack) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }
        if (only_bitmap == null) {
            if (callBack != null)
                callBack.onResult(null, "");
            return;
        }
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("正在生成长图···");
        dialog.show();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String fileReturnPath = "";
                // 获取当前时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-ms", Locale.getDefault());
                String data = sdf.format(new Date());

                // 获取内存路径
                // 设置图片路径+命名规范
                // 声明输出文件
                String storagePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                String fileTitle = "Screenshot_" + data + "_biz.jpg";
                String filePath = storagePath + "/DCIM/";
                final String fileAbsolutePath = filePath + fileTitle;
                File file = new File(fileAbsolutePath);

                /**
                 * 质压与比压结合
                 * 分级压缩
                 * 输出文件
                 */
                if (only_bitmap != null) {
                    try {
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                        // 首先，对原图进行一步质量压缩,形成初步文件
                        FileOutputStream fos = new FileOutputStream(file, false);
                        only_bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);

                        // 另建一个文件other_file预备输出
                        String other_fileTitle = "Screenshot_" + data + ".jpg";
                        String other_fileAbsolutePath = filePath + other_fileTitle;
                        File other_file = new File(other_fileAbsolutePath);
                        FileOutputStream other_fos = new FileOutputStream(other_file, false);

                        // 其次，要判断质压之后的文件大小，按文件大小分级进行处理
                        long file_size = file.length() / 1024; // size of file(KB)
                        if (file_size < 0 || !(file.exists())) {
                            // 零级： 文件判空
                            try {
                                fos.flush();
                                other_fos.flush();
                                other_fos.close();
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return;
                        } else if (file_size > 0 && file_size <= 256) {
                            try {
                                fos.flush();
                                other_fos.flush();
                                other_fos.close();
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            // 一级： 直接输出
                            deleteFile(other_file);
                            // 通知刷新文件系统，显示最新截取的图文件
                            fileReturnPath = fileAbsolutePath;
                            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fileAbsolutePath)));
                        } else if (file_size > 256 && file_size <= 768) {
                            // 二级： 简单压缩:压缩为原比例的3/4，质压为50%
                            anyRatioCompressing(only_bitmap, (float) 3 / 4, (float) 3 / 4).compress(Bitmap.CompressFormat.JPEG, 40, other_fos);
                            try {
                                fos.flush();
                                other_fos.flush();
                                other_fos.close();
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            deleteFile(file);
                            // 通知刷新文件系统，显示最新截取的图文件
                            fileReturnPath = other_fileAbsolutePath;
                            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + other_fileAbsolutePath)));
                        } else if (file_size > 768 && file_size <= 1280) {
                            // 三级： 中度压缩：压缩为原比例的1/2，质压为40%
                            anyRatioCompressing(only_bitmap, (float) 1 / 2, (float) 1 / 2).compress(Bitmap.CompressFormat.JPEG, 40, other_fos);
                            try {
                                fos.flush();
                                other_fos.flush();
                                other_fos.close();
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            deleteFile(file);
                            // 通知刷新文件系统，显示最新截取的图文件
                            fileReturnPath = other_fileAbsolutePath;
                            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + other_fileAbsolutePath)));
                        } else if (file_size > 1280 && file_size <= 2048) {
                            // 四级： 大幅压缩：压缩为原比例的1/3，质压为40%
                            anyRatioCompressing(only_bitmap, (float) 1 / 3, (float) 1 / 3).compress(Bitmap.CompressFormat.JPEG, 40, other_fos);
                            try {
                                fos.flush();
                                other_fos.flush();
                                other_fos.close();
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            deleteFile(file);
                            // 通知刷新文件系统，显示最新截取的图文件
                            fileReturnPath = other_fileAbsolutePath;
                            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + other_fileAbsolutePath)));
                        } else if (file_size > 2048) {
                            // 五级： 中度压缩：压缩为原比例的1/2，质压为40%
                            anyRatioCompressing(only_bitmap, (float) 1 / 2, (float) 1 / 2).compress(Bitmap.CompressFormat.JPEG, 40, other_fos);
                            try {
                                fos.flush();
                                other_fos.flush();
                                other_fos.close();
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            deleteFile(file);
                            // 通知刷新文件系统，显示最新截取的图文件
                            fileReturnPath = other_fileAbsolutePath;
                            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + other_fileAbsolutePath)));
                        }
                        dialog.dismiss();
                        callBack.onResult(only_bitmap, fileReturnPath);

                    } catch (Exception e) {
                        e.printStackTrace();
                        dialog.dismiss();
                        callBack.onResult(only_bitmap, fileReturnPath);
                    }
                } else {
                    dialog.dismiss();
                    callBack.onResult(null, "");
                }
            }
        });
        thread.start();
    }

    /**
     * 可实现任意宽高比例压缩（宽高压比可不同）的压缩方法（主要用于微压）
     *
     * @param bitmap       源图
     * @param width_ratio  宽压比（float）（0<&&<1)
     * @param height_ratio 高压比（float）（0<&&<1)
     * @return 目标图片
     * <p>
     */
    public static Bitmap anyRatioCompressing(Bitmap bitmap, float width_ratio, float height_ratio) {
        Matrix matrix = new Matrix();
        matrix.postScale(width_ratio, height_ratio);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }

    /**
     * 删除指定文件
     *
     * @param file 要删除的文件
     *             <p>
     */
    public static boolean deleteFile(File file) {
        if (!(file.exists())) {
            return false;
        }
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkDelete(file.getAbsolutePath());
        }
        return file.delete();
    }

    /**
     *
     * @param mRecyclerView recyclerView
     * @param shotHeight 当前数据能够渲染出来的页面总长度
     * @param callBack 回调
     */
    public static void screenShotRecycleView(final RecyclerView mRecyclerView, int shotHeight, ScreenRecyclerView callBack) {
        if (mRecyclerView == null) {
            return;
        }
        final Paint paint = new Paint();
        final int oneScreenHeight = mRecyclerView.getMeasuredHeight();

        //返回到顶部
        while (mRecyclerView.canScrollVertically(-1)) {
            mRecyclerView.scrollBy(0, -oneScreenHeight);
        }
        mRecyclerView.scrollBy(0, -oneScreenHeight);
        //绘制截图的背景
        final Bitmap bigBitmap = Bitmap.createBitmap(mRecyclerView.getMeasuredWidth(), shotHeight, Bitmap.Config.ARGB_8888);
        final Canvas bigCanvas = new Canvas(bigBitmap);
        Drawable lBackground = mRecyclerView.getBackground();
        if (lBackground instanceof ColorDrawable) {
            ColorDrawable lColorDrawable = (ColorDrawable) lBackground;
            int lColor = lColorDrawable.getColor();
            bigCanvas.drawColor(lColor);
        }
        final int[] drawOffset = {0};
        final Canvas canvas = new Canvas();
        if (shotHeight <= oneScreenHeight) {
            //仅有一页
            Bitmap bitmap = Bitmap.createBitmap(mRecyclerView.getWidth(), mRecyclerView.getHeight(), Bitmap.Config.ARGB_8888);
            canvas.setBitmap(bitmap);
            mRecyclerView.draw(canvas);
            if (callBack != null)
                callBack.onRecFinished(bitmap);
        } else {
            //超过一页
            final int finalShotHeight = shotHeight;
            mRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if ((drawOffset[0] + oneScreenHeight < finalShotHeight)) {
                        //超过一屏
                        Bitmap bitmap = Bitmap.createBitmap(mRecyclerView.getWidth(), mRecyclerView.getHeight(), Bitmap.Config.ARGB_8888);
                        canvas.setBitmap(bitmap);
                        mRecyclerView.draw(canvas);
                        bigCanvas.drawBitmap(bitmap, 0, drawOffset[0], paint);
                        drawOffset[0] += oneScreenHeight;
                        mRecyclerView.scrollBy(0, oneScreenHeight);
                        try {
                            bitmap.recycle();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        mRecyclerView.postDelayed(this, 30);
                    } else {
                        //不足一屏时的处理
                        int leftHeight = finalShotHeight - drawOffset[0];
                        mRecyclerView.scrollBy(0, leftHeight);
                        int top = oneScreenHeight - (finalShotHeight - drawOffset[0]);
                        if (top > 0 && leftHeight > 0) {
                            Bitmap bitmap = Bitmap.createBitmap(mRecyclerView.getWidth(), mRecyclerView.getHeight(), Bitmap.Config.ARGB_8888);
                            canvas.setBitmap(bitmap);
                            mRecyclerView.draw(canvas);
                            //截图,只要补足的那块图
                            bitmap = Bitmap.createBitmap(bitmap, 0, top, bitmap.getWidth(), leftHeight, null, false);
                            bigCanvas.drawBitmap(bitmap, 0, drawOffset[0], paint);
                            try {
                                bitmap.recycle();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        if (callBack != null)
                            callBack.onRecFinished(bigBitmap);
                    }
                }
            }, 1000);
        }
    }

}
