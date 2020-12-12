package com.kalazi.countdown.util;

import android.graphics.Color;

public class ColorConverter {

    /**
     * Converts color + alpha into a compound color value
     *
     * @param color   Color to which the alpha will be added 0x00nnnnnn
     * @param opacity number from 0 - 100
     * @return compound color 0xnnnnnnnn
     */
    public static int combineColorOpacity(int color, int opacity) {
        opacity = (int) (opacity * 2.55);
        if (opacity < 0 || opacity > 255) {
            return color;
        }

        return Color.argb(opacity, Color.red(color), Color.green(color), Color.blue(color));
    }

    /**
     * Removes the alpha value from a color
     *
     * @param color compound color from which the alpha will be removed 0xnnnnnnnn
     * @return color 0x00nnnnnn
     */
    public static int removeColorAlpha(int color) {
        return color | 0xff000000;
    }

    /**
     * Converts the color to opacity
     *
     * @param color compound color which will be converted to opacity
     * @return color 0x00nnnnnn
     */
    public static int colorToOpacity(int color) {
        return (int) (Color.alpha(color) / 2.55);
    }
}
