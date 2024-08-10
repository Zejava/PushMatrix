package org.example.pushMatrix.deduplication;

import org.example.pushMatrix.deduplication.build.Builder;
import org.example.pushMatrix.deduplication.service.DeduplicationService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 泽
 * @Date 2024/8/10 17:41
 * 去重功能的支架，根据传入相应的去重参数和类型，映射到合适的处理器和构建器去处理去重
 */
@Service
public class DeduplicationHolder {
    //用两个map映射来实现传入code映射为处理器和构建器
    private Map<Integer, Builder> builderHolder = new HashMap<>(4);
    private Map<Integer, DeduplicationService> serviceHolder = new HashMap<>(4);

    public Builder selectBuilder(Integer key) {
        return builderHolder.get(key);
    }

    public DeduplicationService selectService(Integer key) {
        return serviceHolder.get(key);
    }

    public void putBuilder(Integer key, Builder builder) {
        builderHolder.put(key, builder);
    }

    public void putService(Integer key, DeduplicationService service) {
        serviceHolder.put(key, service);
    }
}
