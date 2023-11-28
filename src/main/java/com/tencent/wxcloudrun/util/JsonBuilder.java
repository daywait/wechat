package com.tencent.wxcloudrun.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.NonNull;

/**
 * @author : huangshujing
 * create at:  2022/6/8  4:57 下午
 * @description: json工具类
 */
public abstract class JsonBuilder {
    @NonNull
    @Override
    public String toString() {
        return build();
    }

    public abstract String build();

    public static ObjectBuilder forObject() {
        return new ObjectBuilder();
    }

    public static ArrayBuilder forArray() {
        return new ArrayBuilder();
    }

    public static ArrayBuilder forArray(int capacity) {
        return new ArrayBuilder(capacity);
    }

    public static ObjectBuilder fromObject(String json) {
        return new ObjectBuilder(json);
    }

    public static ArrayBuilder fromArray(String json) {
        return new ArrayBuilder(json);
    }

    public static class ObjectBuilder extends JsonBuilder {
        private final JSONObject object;

        ObjectBuilder() {
            this.object = new JSONObject();
        }

        ObjectBuilder(String json) {
            object = JSONObject.parseObject(json);
        }

        public ObjectBuilder with(String key, JSONObject element) {
            object.put(key, element);
            return this;
        }

        public ObjectBuilder with(String key, String value) {
            object.put(key, value);
            return this;
        }

        public ObjectBuilder with(String key, Number value) {
            object.put(key, value);
            return this;
        }

        public ObjectBuilder with(String key, boolean value) {
            object.put(key, value);
            return this;
        }

        @Override
        public String build() {
            return object.toString();
        }

        public JSONObject toJsonObject() {
            return object;
        }
    }

    public static class ArrayBuilder extends JsonBuilder {
        private final JSONArray array;

        ArrayBuilder(String json) {
            array = JSONArray.parseArray(json);
        }

        ArrayBuilder(int capacity) {
            this.array = new JSONArray(capacity);
        }

        ArrayBuilder() {
            this.array = new JSONArray();
        }

        public ArrayBuilder add(boolean value) {
            array.add(value);
            return this;
        }

        public ArrayBuilder add(String value) {
            array.add(value);
            return this;
        }

        public ArrayBuilder add(Number number) {
            array.add(number);
            return this;
        }

        public ArrayBuilder add(JSONObject json) {
            array.add(json);
            return this;
        }

        @Override
        public String build() {
            return array.toString();
        }

        public JSONArray toJsonArray() {
            return array;
        }
    }
}
