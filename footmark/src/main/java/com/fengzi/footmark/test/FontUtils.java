package com.fengzi.footmark.test;

import android.content.Context;
import android.graphics.Typeface;

import java.io.File;
import java.util.HashMap;

/**
 * @author lijunjie on 2018/5/9 0009.
 * @description
 */

public class FontUtils {
    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeFace(Context context, String fontName) {
        Typeface typeface = fontCache.get(fontName);
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontName);
            } catch (Exception e) {
                return null;
            }
            fontCache.put(fontName, typeface);
        }
        return typeface;
    }

    public static Typeface getTypeFace(String path) {
        String fileName = "";
        if (path.contains(File.separator)) {
            int index = path.lastIndexOf(File.separator);
            fileName = path.substring(index + 1);
        } else {
            fileName = path;
        }
        Typeface typeface = fontCache.get(fileName);
        if (typeface == null) {
            try {
                typeface = Typeface.createFromFile(path);
            } catch (Exception e) {
                return null;
            }
            fontCache.put(fileName, typeface);
        }
        return typeface;
    }

    public static Typeface getTypeFace(File path) {
        Typeface typeface = fontCache.get(path.getName());
        if (typeface == null) {
            try {
                typeface = Typeface.createFromFile(path);
            } catch (Exception e) {
                return null;
            }
            fontCache.put(path.getName(), typeface);
        }
        return typeface;
    }
}
