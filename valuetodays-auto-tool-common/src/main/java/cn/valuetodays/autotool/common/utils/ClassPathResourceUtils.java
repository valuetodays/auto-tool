package cn.valuetodays.autotool.common.utils;

import cn.valuetodays.autotool.common.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-05-27 12:09
 */
@Slf4j
public final class ClassPathResourceUtils {
    private ClassPathResourceUtils() {
    }

    public static InputStream getInputStream(String classpathResource) {
        ClassPathResource classPathResource = new ClassPathResource(classpathResource);
        InputStream inputStream = null;
        try {
            inputStream = classPathResource.getInputStream();
        } catch (IOException e) {
            log.error("error when getFile()", e);
        }

        if (Objects.isNull(inputStream)) {
            throw new CommonException(new FileNotFoundException());
        }

        return inputStream;
    }

    public static String getFileAsString(String classpathResource) {
        return getFileAsString(classpathResource, StandardCharsets.UTF_8);
    }

    public static String getFileAsString(String classpathResource, Charset charset) {
        InputStream inputStream = null;
        try {
            inputStream = getInputStream(classpathResource);
            return IOUtils.toString(inputStream, charset.name());
        } catch (Exception e) {
            throw new CommonException(e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
