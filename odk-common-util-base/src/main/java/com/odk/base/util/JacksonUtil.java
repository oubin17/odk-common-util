package com.odk.base.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.odk.base.domain.BaseDomain;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JacksonUtil
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/3/14
 */
public class JacksonUtil {

    public static void main(String[] args) {
        BaseDomain baseDomain = new BaseDomain();
        baseDomain.setCreateTime(LocalDateTime.now());
        System.out.println(JacksonUtil.toJsonString(baseDomain));
        String str = "{\"createTime\":\"2025-03-14T09:16:03\",\"updateTime\":null}";
        System.out.println(JacksonUtil.parseObject(str, BaseDomain.class).getCreateTime());
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        // 基础配置
        //在反序列化时忽略未知字段，避免出错
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //防止空对象序列化时抛出异常
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // 日期时间处理
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 修改后配置（自定义无 T 格式）
        JavaTimeModule timeModule = new JavaTimeModule();

        timeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        timeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
//        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        MAPPER.registerModule(timeModule);


        // 支持 Java8 时间类型
        MAPPER.registerModule(new JavaTimeModule());

        // 空值处理配置
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 空集合序列化为 [] 而不是 null
        MAPPER.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, true);

        // 命名策略（根据项目规范二选一）
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE); // 驼峰
//        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE); // 下划线


        // 枚举使用 toString() 值序列化
        MAPPER.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        MAPPER.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

        // 启用缓存提升性能
        MAPPER.enable(MapperFeature.USE_STD_BEAN_NAMING);
        MAPPER.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true); // 允许单引号


    }

    private JacksonUtil() {
    }

    //------------------- 序列化方法 -------------------//
    public static String toJsonString(Object obj) {
        try {
            return obj == null ? null : MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JacksonException(e);
        }
    }

    public static String toPrettyJson(Object obj) {
        try {
            return obj == null ? null : MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JacksonException(e);
        }
    }

    /**
     * 处理简单类型（非泛型）
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return isEmpty(json) ? null : MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new JacksonException(e);
        }
    }

    /**
     * 处理泛型类型反序列化
     * <p>
     * 反序列化泛型集合
     * String jsonArray = "[{\"name\":\"Bob\"}, {\"name\":\"Charlie\"}]";
     * List<User> users = JacksonUtil.parseObject(jsonArray, new TypeReference<List<User>>() {});
     * <p>
     * 反序列化嵌套泛型
     * String jsonMap = "{\"data\": {\"age\":25}}";
     * Map<String, User> map = JacksonUtil.parseObject(jsonMap, new TypeReference<Map<String, User>>() {});
     * <p>
     * 处理嵌套泛型
     * String complexJson = "{\"data\": [{\"name\":\"Charlie\"}]}";
     * Result<Page<User>> result = JacksonUtil.parseObject(complexJson, new TypeReference<Result<Page<User>>>() {});
     *
     * @param json          json字符串
     * @param typeReference 泛型类型
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String json, TypeReference<T> typeReference) {
        try {
            return isEmpty(json) ? null : MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new JacksonException(e);
        }
    }

    /**
     * 简单类型集合反序列化，专门针对List<T>的快捷方，不支持嵌套的集合
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        try {
            return isEmpty(json) ? Collections.emptyList() : MAPPER.readValue(json, MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            throw new JacksonException(e);
        }
    }


    //如果对时间格式有特殊要求，允许传入一个时间格式字符串

    /**
     * // 解析带T的ISO格式
     * String json1 = "{\"createTime\":\"2025-03-14T09:16:03\"}";
     * BaseDomain obj1 = JacksonUtil.parseObject(json1, BaseDomain.class, "yyyy-MM-dd'T'HH:mm:ss");
     *
     * // 解析普通格式
     * String json2 = "{\"createTime\":\"2025-03-14 09:16:03\"}";
     * BaseDomain obj2 = JacksonUtil.parseObject(json2, BaseDomain.class, "yyyy-MM-dd HH:mm:ss");
     *
     * // 解析带毫秒格式
     * String json3 = "{\"createTime\":\"2025-03-14 09:16:03.123\"}";
     * BaseDomain obj3 = JacksonUtil.parseObject(json3, BaseDomain.class, "yyyy-MM-dd HH:mm:ss.SSS");
     *
     * @param json
     * @param clazz
     * @param datePattern
     * @return
     * @param <T>
     */
    //------------------- 新增方法 -------------------//
    public static <T> T parseObject(String json, Class<T> clazz, String datePattern) {
        return parseWithDateFormat(json, clazz, datePattern);
    }

    public static <T> T parseObject(String json, TypeReference<T> typeReference, String datePattern) {
        return parseWithDateFormat(json, typeReference, datePattern);
    }

    public static <T> List<T> parseArray(String json, Class<T> clazz, String datePattern) {
        return parseWithDateFormat(json, clazz, datePattern);
    }

    //------------------- 通用解析方法 -------------------//
    private static <T> T parseWithDateFormat(String json, Object valueType, String datePattern) {
        try {
            if (isEmpty(json)) return null;

            // 创建 ObjectMapper 副本避免污染全局配置
            ObjectMapper tempMapper = getObjectMapper();

            // 配置自定义日期格式

            DateTimeFormatter formatter = getFormatter(datePattern);
            JavaTimeModule tempModule = new JavaTimeModule();
            tempModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
            tempMapper.registerModule(tempModule);

            // 根据类型执行解析
            if (valueType instanceof Class) {
                return tempMapper.readValue(json, (Class<T>) valueType);
            } else if (valueType instanceof TypeReference) {
                return tempMapper.readValue(json, (TypeReference<T>) valueType);
            }
            return null;
        } catch (JsonProcessingException e) {
            throw new JacksonException(e);
        }
    }

    private static final ConcurrentHashMap<String, DateTimeFormatter> FORMATTER_CACHE =
            new ConcurrentHashMap<>();

    private static DateTimeFormatter getFormatter(String pattern) {
        return FORMATTER_CACHE.computeIfAbsent(pattern, DateTimeFormatter::ofPattern);
    }




    //------------------- 其他工具方法 -------------------//
    public static boolean isJsonValid(String json) {
        try {
            MAPPER.readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static ObjectMapper getObjectMapper() {
        return MAPPER.copy();
    }

    private static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static class JacksonException extends RuntimeException {
        public JacksonException(Throwable cause) {
            super("Jackson processing failed", cause);
        }
    }
}
