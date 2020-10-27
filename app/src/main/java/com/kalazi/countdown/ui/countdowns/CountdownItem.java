package com.kalazi.countdown.ui.countdowns;

public class CountdownItem {
    private final int id;

    private String name;
    private int eventId;
    private int color;
    private int opacity;
    private int fontColor;
    private int fontSize;
    private String fontFamily;

    public CountdownItem(int id) {
        this.id = id;
        this.name = "";
    }

    public CountdownItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // setters

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

    // getters

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

    // overrides

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountdownItem that = (CountdownItem) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
