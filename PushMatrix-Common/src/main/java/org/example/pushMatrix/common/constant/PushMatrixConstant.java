package org.example.pushMatrix.common.constant;

/**
 * @Author 泽
 * @Date 2024/8/3 17:03
 * 用来设置项目中的基础常量信息
 */
public class PushMatrixConstant {
    /**
     * businessId默认的长度
     * 生成的逻辑：com.java3y.austin.support.utils.TaskInfoUtils#generateBusinessId(java.lang.Long, java.lang.Integer)
     */
    public static final Integer BUSINESS_ID_LENGTH = 16;
    /**
     * 接口限制 最多的人数
     */
    public static final Integer BATCH_RECEIVER_SIZE = 100;
    /**
     * 链路追踪缓存的key标识
     */
    public static final String CACHE_KEY_PREFIX = "Austin";
    public static final String MESSAGE_ID = "MessageId";

    /**
     * 消息模板常量；
     * 如果新建模板/账号时，没传入则用该常量
     */
    public static final String DEFAULT_CREATOR = "ze";
    public static final String DEFAULT_UPDATOR = "ze";
    public static final String DEFAULT_TEAM = "ze公众号";
    public static final String DEFAULT_AUDITOR = "ze";
    /**
     * boolean转换
     */
    public final static Integer TRUE = 1;
    public final static Integer FALSE = 0;
    /**
     * 时间的格式
     */

    public final static String yyyyMMDD = "yyyyMMdd";

    /**
     * cron时间格式
     */
    public final static String CRON_FORMAT = "ss mm HH dd MM ? yyyy-yyyy";
    /**
     * apollo默认的值 todo
     */
    public final static String APOLLO_DEFAULT_VALUE_JSON_OBJECT = "{}";//json对象
    public final static String APOLLO_DEFAULT_VALUE_JSON_ARRAY = "[]";//json数组

}
