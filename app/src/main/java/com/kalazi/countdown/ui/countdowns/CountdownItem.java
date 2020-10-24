package com.kalazi.countdown.ui.countdowns;

public class CountdownItem {
    private final int id;

    private String name;
    private int eventId;
    private float opacity;
    private int fontSize;
    private String fontFamily;
    private String fontColor;
    private String color;

    public CountdownItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

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
