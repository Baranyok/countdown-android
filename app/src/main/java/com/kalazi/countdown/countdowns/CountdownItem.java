package com.kalazi.countdown.countdowns;

public class CountdownItem {
    public int id;

    // TODO: Make this look uniform
    public String title;
    public int eventID;
    public int color;
    public int opacity;
    public int fontColor;
    public int fontSize;
    public String fontFamily;
    public boolean showEventName;

    ////// Constructors

    public CountdownItem(int id) {
        this.id = id;
        this.title = "";
    }

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
        return id;
    }
}
