package com.config.utils;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class JsonMapFlattener {

    private JsonMapFlattener() {
    }

    public static Map<String, Object> flattenToStringMap(Map<String, Object> inputMap) {
        Assert.notNull(inputMap, "Input Map must not be null");
        Map<String, Object> resultMap = new LinkedHashMap<>();
        doFlatten("", inputMap.entrySet().iterator(), resultMap, (it) -> {
            return it == null ? null : it.toString();
        });
        return resultMap;
    }

    private static void doFlatten(String propertyPrefix, Iterator<Map.Entry<String, Object>> inputMap, Map<String, Object> resultMap, Function<Object, Object> valueTransformer) {
        if (StringUtils.hasText(propertyPrefix)) {
            propertyPrefix = propertyPrefix + ".";
        }

        while (inputMap.hasNext()) {
            Map.Entry<String, Object> entry = inputMap.next();
            flattenElement(propertyPrefix.concat(entry.getKey()), entry.getValue(), resultMap, valueTransformer);
        }

    }

    private static void flattenElement(String propertyPrefix, @Nullable Object source, Map<String, Object> resultMap, Function<Object, Object> valueTransformer) {
        if (source instanceof Iterable) {
            flattenCollection(propertyPrefix, (Iterable) source, resultMap, valueTransformer);
        } else if (source instanceof Map) {
            doFlatten(propertyPrefix, ((Map) source).entrySet().iterator(), resultMap, valueTransformer);
        } else {
            resultMap.put(propertyPrefix, valueTransformer.apply(source));
        }
    }

    private static void flattenCollection(String propertyPrefix, Iterable<Object> iterable, Map<String, Object> resultMap, Function<Object, Object> valueTransformer) {
        int counter = 0;
        for (Iterator<Object> var5 = iterable.iterator(); var5.hasNext(); ++counter) {
            Object element = var5.next();
            flattenElement(propertyPrefix + "[" + counter + "]", element, resultMap, valueTransformer);
        }
    }
}
