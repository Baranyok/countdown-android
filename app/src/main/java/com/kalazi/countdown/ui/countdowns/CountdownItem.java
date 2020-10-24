package com.kalazi.countdown.ui.countdowns;

public class CountdownItem {
    private final int id;
    private String countdown_name;

    public CountdownItem(int id, String countdown_name) {
        this.id = id;
        this.countdown_name = countdown_name;
    }

    public void setCountdownName(String countdown_name) {
        this.countdown_name = countdown_name;
    }

    public String getCountdownName() {
        return countdown_name;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountdownItem that = (CountdownItem) o;

        return countdown_name.equals(that.countdown_name);
    }

    @Override
    public int hashCode() {
        return countdown_name.hashCode();
    }
}
