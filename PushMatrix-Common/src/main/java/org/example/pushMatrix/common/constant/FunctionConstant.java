package org.example.pushMatrix.common.constant;

/**
 * @Author 泽
 * @Date 2024/8/14 21:30
 * 项目中需要经常用到的常量
 */
public class FunctionConstant {

    /**
     * boolean转换
     */
    public static final Integer TRUE = 1;
    public static final Integer FALSE = 0;
    /**
     * 加密算法
     */
    public static final String HMAC_SHA256_ENCRYPTION_ALGO = "HmacSHA256";
    /**
     * 编码格式
     */
    public static final String CHARSET_UTF_8 = "UTF-8";

    /**
     * HTTP 请求方法
     */
    public static final String REQUEST_METHOD_GET = "GET";
    public static final String REQUEST_METHOD_POST = "POST";
    /**
     * JSON默认值
     */
    public static final String EMPTY_JSON_OBJECT = "{}";
    public static final String EMPTY_VALUE_JSON_ARRAY = "[]";
    /**
     * 日期相关
     */
    public static final String CRON_FORMAT = "ss mm HH dd MM ? yyyy-yyyy";
    public static final Long ONE_DAY_SECOND = 86400L;


    private FunctionConstant() {
    }
}
