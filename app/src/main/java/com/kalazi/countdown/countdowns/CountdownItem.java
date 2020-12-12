package com.kalazi.countdown.countdowns;

public class CountdownItem {
    private final int id;

    // TODO: Make this look uniform
    private String name;
    public int eventID;
    private int color;
    private int opacity;
    private int fontColor;
    private int fontSize;
    private String fontFamily;
    public boolean showEventName;

    ////// Constructors

    public CountdownItem(int id) {
        this.id = id;
        this.name = "";
    }

    ////// Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setOpacity(int opacity) {
        this.opacity = opacity;
    }

    public void setFontColor(int fontColor) {
        this.fontColor = fontColor;
    }

    ////// Getters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public int getOpacity() {
        return opacity;
    }

    public int getFontColor() {
        return fontColor;
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
