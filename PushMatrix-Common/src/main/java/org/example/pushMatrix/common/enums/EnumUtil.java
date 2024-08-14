package org.example.pushMatrix.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author 泽
 * @Date 2024/8/14 21:44
 * 枚举工具类
 */
public class EnumUtil {
    private EnumUtil() {
    }

    public static <T extends PowerfulEnum> String getDescriptionByCode(Integer code, Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> Objects.equals(e.getCode(), code))
                .findFirst().map(PowerfulEnum::getDescription).orElse("");
    }

    public static <T extends PowerfulEnum> T getEnumByCode(Integer code, Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> Objects.equals(e.getCode(), code))
                .findFirst().orElse(null);
    }

    public static <T extends PowerfulEnum> List<Integer> getCodeList(Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(PowerfulEnum::getCode)
                .collect(Collectors.toList());
    }
}
