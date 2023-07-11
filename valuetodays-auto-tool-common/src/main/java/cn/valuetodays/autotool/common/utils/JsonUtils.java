package cn.valuetodays.autotool.common.utils;

import cn.valuetodays.autotool.common.exception.CommonException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * JSON工具类.
 *
 * 用于对象与JSON字符串之间的转化
 *
 * @author valuetodays
 */
public final class JsonUtils {

    private JsonUtils() {
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE);
        timeModule.addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
        MAPPER.registerModule(timeModule);
        MAPPER.setSerializationInclusion(Include.NON_NULL);
        MAPPER.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MAPPER.registerModule(new Jdk8Module());
    }

    public static String toJson(Object obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        String json;
        try {
            json = MAPPER.writeValueAsString(obj);
        } catch (IOException e) {
            throw new CommonException(e);
        }
        return json;
    }

    public static String toPrettyJson(Object obj) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new CommonException(e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || json.trim().length() == 0) {
            return null;
        }
        try {
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new CommonException(e);
        }
    }

    public static <T> T fromJson(String json, JavaType type) {
        if (json == null || json.trim().length() == 0) {
            return null;
        }
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new CommonException(e);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> type) {
        if (json == null || json.trim().length() == 0) {
            return null;
        }
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new CommonException(e);
        }
    }

    public static Map<String, Object> fromJson(String json) {
        return fromJson(json, new TypeReference<Map<String, Object>>() {
        });
    }

    /**
     * @param json            json 字符串
     * @param collectionClass 集合类，如List
     * @param type            泛型的类
     * @param <T>             泛型类型
     * @return 对应的泛型集合
     */
    @SuppressWarnings("rawtypes")
    public static <T> Collection<T> parseJsonAsCollection(String json,
                                                          Class<? extends Collection> collectionClass,
                                                          Class<T> type) {
        if (json == null || json.trim().length() == 0) {
            return toEmptyCollection(type);
        }
        try {
            JavaType collectionType = getCollectionType(collectionClass, type);
            return MAPPER.readValue(json, collectionType);
        } catch (IOException e) {
            throw new CommonException(e);
        }
    }

    @SuppressWarnings({"unchecked"})
    private static <T> Collection<T> toEmptyCollection(Class<T> type) {
        if (Map.class.isAssignableFrom(type)) {
            return (Collection<T>) Collections.emptyMap();
        } else if (Set.class.isAssignableFrom(type)) {
            return (Collection<T>) Collections.emptySet();
        } else if (List.class.isAssignableFrom(type)) {
            return (Collection<T>) Collections.emptyList();
        }
        return (Collection<T>) Collections.emptyList();
    }

    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

}
