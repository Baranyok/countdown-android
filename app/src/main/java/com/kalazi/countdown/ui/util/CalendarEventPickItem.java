package com.kalazi.countdown.ui.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.kalazi.countdown.R;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

public class CalendarEventPickItem extends LinearLayout {

    private String title = null;
    private String eventName = "None";
    private int eventID;

    private TextView titleView;
    private TextView selectedEventView;

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

    private void init(AttributeSet attrs) {
        this.setClickable(true);
        this.setFocusable(true);
        this.setOrientation(VERTICAL);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarEventPickItem);
            title = a.getString(R.styleable.CalendarEventPickItem_title);
            a.recycle();
        }

        createTitleView();
        createEventView();
    }

    private void createTitleView() {
        titleView = new TextView(getContext());
        titleView.setText(title);
        this.addView(titleView);
    }

    private void createEventView() {
        selectedEventView = new TextView(getContext());
        selectedEventView.setText(eventName);
        selectedEventView.setTextSize(COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_micro));
        this.addView(selectedEventView);
    }
}
