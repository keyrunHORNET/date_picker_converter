package com.hornet.dateconverter;

import android.content.Context;
import android.graphics.Typeface;

import androidx.collection.SimpleArrayMap;

/**
 * Created by Hornet on 5/3/2016.
 */
public class TypefaceHelper {
    private static final SimpleArrayMap<String, Typeface> cache = new SimpleArrayMap<>();

    public static Typeface get(Context c, String name) {
        synchronized (cache) {
            if (!cache.containsKey(name)) {
                Typeface t = Typeface.createFromAsset(
                        c.getAssets(), String.format("fonts/%s.ttf", name));
                cache.put(name, t);
                return t;
            }
            return cache.get(name);
        }
    }
}
