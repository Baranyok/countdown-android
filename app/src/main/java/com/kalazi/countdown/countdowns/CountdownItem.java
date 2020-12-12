package com.kalazi.countdown.countdowns;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "countdown_table")
public class CountdownItem {
    @PrimaryKey(autoGenerate = true)
    public Integer id;

    @NonNull
    @ColumnInfo(name = "title")
    public String title = "";

    @ColumnInfo(name = "event_id")
    public int eventID;

    @ColumnInfo(name = "color")
    public int color;

    @ColumnInfo(name = "font_color")
    public int fontColor;

    @ColumnInfo(name = "show_event")
    public boolean showEventName; // TODO: refactor this

    ////// Overrides

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountdownItem that = (CountdownItem) o;

        return this.hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {
        return (title + eventID + color + fontColor + showEventName).hashCode();
    }
}
