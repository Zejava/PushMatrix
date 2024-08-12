package org.example.pushMatrix.common.enums;

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
    CONTEXT_IS_NULL("A001","流程上下文为空" ),
    BUSINESS_CODE_IS_NULL("A002","业务代码为空" ),
    PROCESS_TEMPLATE_IS_NULL("A003","流程模板为空" ),
    PROCESS_LIST_IS_NULL("A004","模板列表为空" ),
    TEMPLATE_NOT_FOUND("A005","未找到模板" );
    /**
     * 响应状态
     */
    private final String code;
    /**
     * 响应编码
     */
    private final String msg;
}
