package org.example.pushMatrix.kafkatest;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author 泽
 * @Date 2024/8/3 10:53
 */
@Data
@Accessors(chain = true)
public class User {
    private String username;
    private String userid;
    private String state;
}
