package org.example.pushMatrix.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 泽
 * @Date 2024/8/10 17:26
 * 记录去重类型的枚举
 */
@Getter
@ToString
@AllArgsConstructor
public enum DeduplicationType {
    CONTENT(10, "N分钟相同内容去重"),
    FREQUENCY(20, "一天内N次相同渠道去重"),
    ;
    private Integer code;
    private String description;
    /**
     * 获取去重渠道的code列表
     * @return
     */
    public static List<Integer> getDeduplicationList() {
        ArrayList<Integer> result = new ArrayList<>();
        for (DeduplicationType value : DeduplicationType.values()) {
            result.add(value.getCode());
        }
        return result;
    }
}
