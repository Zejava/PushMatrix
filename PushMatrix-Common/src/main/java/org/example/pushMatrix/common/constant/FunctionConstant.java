package org.example.pushMatrix.common.constant;

/**
 * @Author 泽
 * @Date 2024/8/14 21:30
 * 项目中需要经常用到的常量
 */
public class FunctionConstant {

    public static final String PERIOD = ".";
    public static final String COMMA = ",";
    public static final String COLON = ":";
    public static final String SEMICOLON = ";";
    public static final String POUND = "#";
    public static final String SLASH = "/";
    public static final String BACKSLASH = "\\";
    public static final String EMPTY_STRING = "";
    public static final String RADICAL = "|";
    public static final String QM_STRING = "?";
    public static final String EQUAL_STRING = "=";
    public static final String AND_STRING = "&";
    public static final String ONE = "1";
    public static final String ZERO = "0";
    public static final String MINUS_ONE = "-1";
    public static final String YES = "Y";
    public static final String NO = "N";
    public static final char QM = '?';

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
