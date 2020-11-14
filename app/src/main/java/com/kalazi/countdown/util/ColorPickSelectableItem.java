package com.kalazi.countdown.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentManager;
import com.kalazi.countdown.R;
import com.thebluealliance.spectrum.SpectrumDialog;

public class ColorPickSelectableItem extends AppCompatTextView {

    private static final String TAG = "ColorPickSelectableItem";

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int color = 0xff000000;
    private float iconPadding = 0;
    private float iconSize = 0;

    public ColorPickSelectableItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public ColorPickSelectableItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ColorPickSelectableItem(@NonNull Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        this.setClickable(true);
        this.setFocusable(true);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ColorPickSelectableItem);
            color = a.getColor(R.styleable.ColorPickSelectableItem_defaultPickColor, color);
            iconPadding = a.getDimension(R.styleable.ColorPickSelectableItem_iconPadding, getPaddingEnd());
            iconSize = a.getDimension(R.styleable.ColorPickSelectableItem_iconSize, iconSize);
            a.recycle();
        }
        paint.setColor(color);

        this.setOnClickListener(this::pickColor);
    }

    private void pickColor(View view) {
        FragmentManager fragmentManager = getFM();
        if (fragmentManager == null) {
            Log.d(TAG, "Can't get the fragment manager");
            return;
        }

        new SpectrumDialog.Builder(getContext())
                .setColors(R.array.colors_pick)
                .setSelectedColor(color)
                .setDismissOnColorSelected(true)
                .setOutlineWidth(0)
                .setOnColorSelectedListener((positiveResult, color) -> {
                    if (positiveResult) {
                        this.setColor(color);
                    }
                }).build().show(fragmentManager, "pick_color");
    }

    private FragmentManager getFM() {
        if (getContext() instanceof AppCompatActivity) {
            return ((AppCompatActivity) getContext()).getSupportFragmentManager();
        } else {
            return null;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radius = iconSize / 2;
        canvas.drawCircle(getWidth() - radius - iconPadding, getHeight() / 2.0f, radius, paint);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        paint.setColor(color);
        this.invalidate();
    }
}
