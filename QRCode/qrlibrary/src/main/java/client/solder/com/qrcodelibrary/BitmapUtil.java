package client.solder.com.qrcodelibrary;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by snail on 2018/8/20.
 */

public class BitmapUtil {

    /**
     * Resize the bitmap
     *
     * @param bitmap 图片引用
     * @param width 宽度
     * @param height 高度
     * @return 缩放之后的图片引用
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }
}
