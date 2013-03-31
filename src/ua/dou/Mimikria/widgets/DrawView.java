package ua.dou.Mimikria.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * User: David
 * Date: 31.03.13
 * Time: 8:58
 */
public class DrawView extends View {
    private byte colors[] = new byte[0];

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        for (int i = 0; i < colors.length; i++) {
            paint.setColor(colors[i]);
            canvas.drawPoint(0, i, paint);
        }

    }

    public void setFft(byte[] colors) {
        this.colors = colors;
        invalidate();
    }
}
