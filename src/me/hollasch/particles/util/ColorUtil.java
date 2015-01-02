package me.hollasch.particles.util;

import java.awt.*;

/**
 * @author Connor Hollasch
 * @since 1/1/2015
 */
public class ColorUtil {

    public static Color modifyBrightness(Color color, float darken) {
        int red = (int) (color.getRed() * darken);
        int green = (int) (color.getGreen() * darken);
        int blue = (int) (color.getBlue() * darken);

        float[] hsb = Color.RGBtoHSB(red, green, blue, new float[3]);
        return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }
}