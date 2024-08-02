package org.example.pushMatrix.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author 泽
 * @Date 2024/8/2 20:31
 *
 */
@Getter
@ToString
@AllArgsConstructor
public enum RespStatusEnum {
    /**
     * 操作结果枚举
     */
    SUCCESS("001","操作成功"),
    FAIL("002","操作失败"),
    /**
     * 客户端响应错误结果
     */
    CLIENT_BAD_PARAMETERS("A001", "客户端参数错误"),
    /**
     * 系统
     */
    SERVICE_ERROR("B001", "服务执行异常"),
    RESOURCE_NOT_FOUND("B0404", "资源不存在"),
    ;
    /**
     * 响应状态
     */
    private final String code;
    /**
     * 响应编码
     */
    private final String msg;
}
