package com.boiler_plate.mobile.util;

import android.graphics.Typeface;

/**
 * Fonts helper class to centralize the use of fonts
 *
 * @author      Daniel Coellar
 * @created 	2016-01-27
 */
public class Font {
    public static Typeface lighFont() { return Typeface.create("sans-serif-light",Typeface.NORMAL); }

    public static Typeface regularFont() {
        return Typeface.create("sans-serif",Typeface.NORMAL);
    }

    public static Typeface boldFont() {
        return Typeface.create("sans-serif",Typeface.BOLD);
    }

    public static Typeface bolderFont() {
        return Typeface.create("sans-serif-condensed",Typeface.BOLD);
    }
}
