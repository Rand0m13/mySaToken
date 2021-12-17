package com.rangamer.mysatoken.base;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ListResult<T> implements Serializable {

    private static final long serialVersionUID = 6133263729712690973L;
    //private static GsonBuilder gsonBuilder = GsonUtils.createCommonBuilder();
    private boolean success;
    private String code;
    private String message;
    private List<T> data = new ArrayList();
    private long total;

    public ListResult() {
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        if (data != null) {
            this.data = data;
        }

    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

//    public String toString() {
//        return gsonBuilder.create().toJson(this);
//    }
//
//    public ListResult<T> appendJsonIncludes(String... propNames) {
//        return this;
//    }
//
//    public ListResult<T> appendJsonExcludes(final String... propNames) {
//        gsonBuilder.addSerializationExclusionStrategy(new ExclusionStrategy() {
//            public boolean shouldSkipField(FieldAttributes attr) {
//                String[] var2 = propNames;
//                int var3 = var2.length;
//
//                for(int var4 = 0; var4 < var3; ++var4) {
//                    String propName = var2[var4];
//                    if (propName.equals(attr)) {
//                        return true;
//                    }
//                }
//
//                return false;
//            }
//
//            public boolean shouldSkipClass(Class<?> clazz) {
//                return false;
//            }
//        });
//        return this;
//    }
//
//    public ListResult<T> appendJsonExcludes(ExclusionStrategy strategy) {
//        gsonBuilder.addSerializationExclusionStrategy(strategy);
//        return this;
//    }
//
//    public ListResult<T> appendJsonTransformers(JsonSerializer<?> transformer, Class<?>... classes) {
//        Class[] var3 = classes;
//        int var4 = classes.length;
//
//        for(int var5 = 0; var5 < var4; ++var5) {
//            Class<?> clazz = var3[var5];
//            gsonBuilder.registerTypeHierarchyAdapter(clazz, transformer);
//        }
//
//        return this;
//    }
}
