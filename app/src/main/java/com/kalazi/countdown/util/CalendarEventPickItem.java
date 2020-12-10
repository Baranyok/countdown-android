package com.kalazi.countdown.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.kalazi.countdown.R;
import com.kalazi.countdown.calendar.CalendarManager;
import com.kalazi.countdown.events.EventItem;
import com.kalazi.countdown.events.EventListFragment;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

public class CalendarEventPickItem extends LinearLayout {

    private static final String TAG = "CalendarEventPickItem";

    private final MutableLiveData<EventItem> event = new MutableLiveData<>();

    private String title = "Select event";

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

    ////// public data observer registration -> we need the parent fragment lifecycle owner for the observer

    public void registerDataObservers(LifecycleOwner lifecycleOwner) {
        if (lifecycleOwner == null) {
            return;
        }

        event.observe(lifecycleOwner, eventItem -> {
            if (eventItem != null) {
                eventView.setText(eventItem.title);
            }
        });
    }

    ////// Public setters & getters

    public int getEventID() {
        if (event.getValue() != null) {
            return event.getValue().id;
        }
        return -1; // TODO: Make a constant
    }

    public void setEventFromID(int eventID) {
        if (eventID == -1 || getActivity() == null) {
            return;
        }

        try {
            EventItem eventItem = CalendarManager.loadEventFromID(eventID, getActivity());
            if (eventItem != null) {
                event.postValue(eventItem);
            }
        } catch (SecurityException ignored) {

        }

    }

    ////// Private constructor callback

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

        this.setOnClickListener(this::selectEvent);
    }

    ////// Private callback

    private void selectEvent(View view) {
        FragmentManager fragmentManager = getFM();
        if (fragmentManager == null) {
            Log.d(TAG, "Can't get the fragment manager");
            return;
        }

        EventListFragment.newInstance(event).show(fragmentManager, "EVENT_LIST");
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
        eventView.setText("None");
        eventView.setTextSize(COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_micro));
        if (eventTextColor != null) {
            eventView.setTextColor(eventTextColor);
        }
        this.addView(eventView);
    }

    ////// Private utility methods

    private FragmentManager getFM() {
        AppCompatActivity activity = getActivity();

        if (activity != null) {
            return activity.getSupportFragmentManager();
        } else {
            return null;
        }
    }

    private AppCompatActivity getActivity() {
        if (getContext() instanceof AppCompatActivity) {
            return ((AppCompatActivity) getContext());
        } else {
            return null;
        }
    }
}
