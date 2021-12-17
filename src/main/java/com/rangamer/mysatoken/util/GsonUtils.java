package com.rangamer.mysatoken.util;


import com.fasterxml.jackson.databind.ser.std.CalendarSerializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class GsonUtils {
    private static final GsonBuilder COMMONGSONBUILDER = createCommonBuilder();

    public GsonUtils() {
    }

    public static GsonBuilder createCommonBuilder() {
        GsonBuilder gsonBuilder = (new GsonBuilder()).registerTypeHierarchyAdapter(Date.class, new DateSerializer())
                .registerTypeHierarchyAdapter(Calendar.class, new CalendarSerializer())
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
            public boolean shouldSkipField(FieldAttributes fieldAttr) {
                return fieldAttr.getDeclaredClass().equals(SimpleDateFormat.class);
            }

            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).serializeNulls().disableInnerClassSerialization();
        return gsonBuilder;
    }

    public static Gson fetchCommonGson() {
        return COMMONGSONBUILDER.create();
    }
}
