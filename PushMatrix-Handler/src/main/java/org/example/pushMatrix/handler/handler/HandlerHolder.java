package org.example.pushMatrix.handler.handler;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 泽
 * @Date 2024/8/7 15:38
 * channel->Handler的映射关系 不同的渠道转向不同的渠道处理，实现消费隔离
 */
@Component
public class HandlerHolder {
    private Map<Integer, Handler> handlers = new HashMap<Integer, Handler>(32);//map实现渠道code指向handler

    public void putHandler(Integer channelCode, Handler handler) {
        handlers.put(channelCode, handler);
    }//存入渠道和handler关系

    public Handler route(Integer channelCode) {
        return handlers.get(channelCode);
    }//取出相应渠道code对应的handler
}
