package org.example.pushMatrix.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 泽
 * @Date 2024/8/3 17:10
 * 邮箱内容模型
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailContentModel extends ContentModel{
    /**
     * 邮件的标题
     */
    private String title;

    /**
     * 内容(可写入HTML)
     */
    private String content;
}
