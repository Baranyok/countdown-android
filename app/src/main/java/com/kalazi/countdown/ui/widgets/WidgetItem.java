package com.kalazi.countdown.ui.widgets;

public class WidgetItem {
    private final int id;
    private String widget_name;

    public WidgetItem(int id, String widget_name) {
        this.id = id;
        this.widget_name = widget_name;
    }

    public void setWidgetName(String widget_name) {
        this.widget_name = widget_name;
    }

    public String getWidgetName() {
        return widget_name;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WidgetItem that = (WidgetItem) o;

        return widget_name.equals(that.widget_name);
    }

    @Override
    public int hashCode() {
        return widget_name.hashCode();
    }
}
