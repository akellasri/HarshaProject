package com.harsha.project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class ClockView extends View {

    private int height, width = 0;
    private int fontSize = 0;
    private int radius = 0;
    private Paint paint;
    private Rect rect = new Rect();

    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initClock() {
        height = getHeight();
        width = getWidth();
        fontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13,
                getResources().getDisplayMetrics());
        int min = Math.min(height, width);
        radius = (min / 2) - 50;
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initClock();
        drawNumeral(canvas);
    }

    private void drawNumeral(Canvas canvas) {
        paint.setTextSize(fontSize);
        for (int i = 0; i < 26; i++) {
            paint.getTextBounds(Character.toString((char) (i + 65)), 0, 1, rect);
            double angle = Math.PI / 13 * (i - 6);
            int x = (int) (width / 2 + Math.cos(angle) * radius - rect.width() / 2);
            int y = (int) (height / 2 + Math.sin(angle) * radius + rect.height() / 2);
            canvas.drawText(Character.toString((char) (i + 65)), x, y, paint);
        }
    }


}
