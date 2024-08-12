package org.example.pushMatrix.support.utils;

import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.PropertyPlaceholderHelper;

import java.text.MessageFormat;
import java.util.Map;

/**
 * @Author 泽
 * @Date 2024/8/4 8:17
 * 这里就是用来处理占位符内容替换的工具类
 */
public class ContentHolderUtil {
    // 占位符前缀
    private static final String PLACE_HOLDER_PREFIX = "{$";
    // 占位符后缀
    private static final String PLACE_HOLDER_ENDFIX = "}";
    //在表达式求值过程中提供上下文环境
    private static final StandardEvaluationContext evalutionContext;
    //解析模板字符串中的占位符

    private static PropertyPlaceholderHelper propertyPlaceholderHelper = new PropertyPlaceholderHelper(
            PLACE_HOLDER_PREFIX, PLACE_HOLDER_ENDFIX);

    static {
        evalutionContext = new StandardEvaluationContext();
        //添加了一个MapAccessor属性访问器，以便能够访问Map类型的数据
        evalutionContext.addPropertyAccessor(new MapAccessor());
    }

    public static String replacePlaceHolder(final String template, final Map<String, String> paramMap) {
        String replacedPushContent = propertyPlaceholderHelper.replacePlaceholders(template,
                new CustomPlaceholderResolver(paramMap));
        return replacedPushContent;
    }
//根据占位符名称查找map对应的映射内容并进行替换
    private static class CustomPlaceholderResolver implements PropertyPlaceholderHelper.PlaceholderResolver {
        private Map<String, String> paramMap;

        public CustomPlaceholderResolver(Map<String, String> paramMap) {
            super();
            this.paramMap = paramMap;
        }

        @Override
        public String resolvePlaceholder(String placeholderName) {
            String value = paramMap.get(placeholderName);
            if (null == value) {
                String errorStr = MessageFormat.format("template:{} require param:{},but not exist! paramMap:{}",
                        placeholderName, paramMap.toString());
                throw new IllegalArgumentException(errorStr);
            }
            return value;
        }
    }

}
