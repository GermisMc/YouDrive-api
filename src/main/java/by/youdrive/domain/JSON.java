package by.youdrive.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class JSON<T> {

    private T object;

    private JSON(T object) {
        this.object = object;
    }

    @Contract("null -> null; !null -> !null")
    public static JSON fromObject(Object object) {
        if (object == null) {
            return null;
        }
        return new JSON(object);
    }

    @Nullable
    public static <T> JSON<T> fromString(String object, Class<T> tClass) {
        try {
            return object == null ? null : new JSON<T>(new ObjectMapper().readValue(object, tClass));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public T getObject() {
        return object;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS).writeValueAsString(
                    object == null ? new Object() : object
            );
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
