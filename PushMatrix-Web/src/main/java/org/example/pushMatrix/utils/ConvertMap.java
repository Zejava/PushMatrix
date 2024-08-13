package org.example.pushMatrix.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author 泽
 * @Date 2024/8/13 2:57
 * list/object扁平化转map的工具类
 */
public class ConvertMap {
    /**
     * 将List对象转换成Map(无嵌套)
     *
     * @param param
     * @param fieldName 需要 reduce 的属性名
     * @return
     */
    public static <T> List<Map<String, Object>> flatList(List<T> param, List<String> fieldName) {

        List<Map<String, Object>> result = new ArrayList<>();
        //对每一个t对象中的字段和fieldname进行校验处理
        for (T t : param) {
            Map<String, Object> map = flatSingle(t, fieldName);
            result.add(map);
        }
        return result;

    }

    /**
     * 将指定的对象中的字段值扁平化，并选择性地将嵌套字段（当字段名在fieldName列表中时）转换为键值对形式，同时保留原字段值。
     *
     * <p>此方法首先获取传入对象的所有字段，然后遍历这些字段。如果字段名在fieldName列表中，则尝试将该字段的值（无论是字符串还是其他类型）
     * 转换为JSON对象，并将JSON对象中的每个键值对添加到结果Map中。同时，无论字段名是否在fieldName列表中，都会将该字段的原始值以字段名为键
     * 添加到结果Map中。</p>
     * @param obj 要扁平化的对象。
     * @param fieldName 包含需要特别处理的字段名的列表。这些字段的值将被视为JSON字符串（如果已经是字符串）或转换为JSON字符串（如果不是），
     *                  然后解析为JSON对象，其键值对被添加到结果Map中。
     * @return 一个包含扁平化字段值的Map，其中嵌套字段（如果字段名在fieldName列表中）的值被转换为键值对，并且所有字段的原始值也被保留。
     */
    public static Map<String, Object> flatSingle(Object obj, List<String> fieldName) {
        Map<String, Object> result = MapUtil.newHashMap(32);
        Field[] fields = ReflectUtil.getFields(obj.getClass());
        for (Field field : fields) {
            // 如果字段名在fieldName列表中，则进行特殊处理
            if (fieldName.contains(field.getName())) {
                JSONObject jsonObject;
                Object value = ReflectUtil.getFieldValue(obj, field);
                // 如果值是字符串，则直接解析为JSON对象
                if (value instanceof String) {
                    jsonObject = JSON.parseObject((String) value);
                } else {
                    // 否则，将值转换为JSON字符串，然后解析为JSON对象
                    jsonObject = JSONObject.parseObject(JSON.toJSONString(value));
                }
                // 将嵌套字段的键值对添加到结果Map中
                for (String key : jsonObject.keySet()) {
                    result.put(key, jsonObject.getString(key));
                }
            }
            // 无论是否特殊处理，都将字段的原始值添加到结果Map中
            result.put(field.getName(), ReflectUtil.getFieldValue(obj, field));
        }
        return result;
    }
}
