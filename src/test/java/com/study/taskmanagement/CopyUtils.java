package com.study.taskmanagement;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@UtilityClass
public class CopyUtils {

    @SneakyThrows
    public static <T> T copyOf(T original, Class<T> type) {
        final T copy = type.newInstance();
        List<Field> typeFields = new LinkedList<>(Arrays.asList(type.getDeclaredFields()));
        Class<? super T> superClass = type.getSuperclass();
        while (superClass != null) {
            typeFields.addAll(Arrays.asList(superClass.getDeclaredFields()));
            superClass = superClass.getSuperclass();
        }
        for (Field field :
                typeFields) {
            field.setAccessible(true);
            field.set(copy, field.get(original));
        }
        return copy;
    }

}
