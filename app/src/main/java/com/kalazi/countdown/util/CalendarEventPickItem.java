package com.kalazi.countdown.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.kalazi.countdown.R;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

public class CalendarEventPickItem extends LinearLayout {

    private String title = "Title";
    private String eventName = "None";
    private int eventID;

    private TextView titleView;
    private TextView eventView;

    private ColorStateList titleTextColor = null;
    private ColorStateList eventTextColor = null;

    ////// Inherited Constructor calls (for compatibility)

    public CalendarEventPickItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CalendarEventPickItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CalendarEventPickItem(@NonNull Context context) {
        super(context);
        init(null);
    }

    ////// Constructor callback

    private void init(AttributeSet attrs) {
        this.setClickable(true);
        this.setFocusable(true);
        this.setOrientation(VERTICAL);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarEventPickItem);
            title = a.getString(R.styleable.CalendarEventPickItem_title);
            titleTextColor = a.getColorStateList(R.styleable.CalendarEventPickItem_titleTextColor);
            eventTextColor = a.getColorStateList(R.styleable.CalendarEventPickItem_android_textColor);
            a.recycle();
        }

        createTitleView();
        createEventView();
    }

    ////// Private utility methods

    private void createTitleView() {
        titleView = new TextView(getContext());
        titleView.setText(title);
        if (titleTextColor != null) {
            titleView.setTextColor(titleTextColor);
        }
        this.addView(titleView);
    }

    private void createEventView() {
        eventView = new TextView(getContext());
        eventView.setText(eventName);
        eventView.setTextSize(COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_micro));
        if (eventTextColor != null) {
            eventView.setTextColor(eventTextColor);
        }
        this.addView(eventView);
    }
}
