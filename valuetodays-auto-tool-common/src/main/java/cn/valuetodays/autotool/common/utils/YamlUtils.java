package cn.valuetodays.autotool.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Objects;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-05-26 12:55
 */
@Slf4j
public class YamlUtils {

    public static <T> T toObject(String yamlString, Class<T> clazz) {
        YAMLFactory jf = new YAMLFactory();
        ObjectMapper mapper = new ObjectMapper(jf);
        mapper.findAndRegisterModules();
        try {
            return mapper.readValue(yamlString, clazz);
        } catch (JsonProcessingException e) {
            log.error("error toObject", e);
        }
        return null;
    }

    /**
     *
     * @param yamlString yaml字符串
     * @param prefix 前缀，可以是 null 或 "spring" 或 “spring.datasource” 或 多个.
     *               功能类似 @ConfigurationProperties(prefix = "xxx")中的prefix
     * @param clazz 类对象
     */
    public static <T> T toObject(String yamlString, String prefix, Class<T> clazz) {
        if (StringUtils.isBlank(prefix)) {
            return toObject(yamlString, clazz);
        }
        Map map = YamlUtils.toObject(yamlString, Map.class);
        Map mapWithoutPrefix = map;
        String prefixToUse = StringUtils.trimToNull(prefix);
        String[] keys = StringUtils.split(prefixToUse, ".");
        if (Objects.nonNull(keys)) {
            for (String key : keys) {
                mapWithoutPrefix = (Map) mapWithoutPrefix.get(key);
            }
        }
        return toObject(toString(mapWithoutPrefix), clazz);
    }

    public static String toString(Object object) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        mapper.findAndRegisterModules();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        StringWriter stringWriter = new StringWriter();
        try {
            mapper.writeValue(stringWriter, object);
            return stringWriter.toString();
        } catch (IOException e) {
            log.error("error toString", e);
        }
        return null;
    }

    public static String json2Yaml(String jsonStr) throws JsonProcessingException {
        JsonNode jsonNode = new ObjectMapper().readTree(jsonStr);
        return new YAMLMapper().writeValueAsString(jsonNode);
    }

    /**
     *
     * @param classpath classpath下的文件路径
     * @param prefix 前缀，功能类似 @ConfigurationProperties(prefix = "xxx")中的prefix
     * @param clazz 类
     */
    public static <T> T classpathFileToObject(String classpath, String prefix, Class<T> clazz) {
        String fileAsString = ClassPathResourceUtils.getFileAsString(classpath);
        return toObject(fileAsString, prefix, clazz);
    }



}
