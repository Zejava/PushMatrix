package org.example.pushMatrix.config;

import org.example.pushMatrix.pending.Task;
import org.example.pushMatrix.receiver.Receiver;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


/**
 * @Author 泽
 * @Date 2024/8/7 15:31
 * Handler模块的配置信息 创建多例的接收者bean和发送任务的bean，实现消费数据隔离
 */
@Configuration
public class PrototypeBeanConfig {
    /**
     * 定义多例的Receiver
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)//设置bean的作用域注解，每次请求都要生成一个消费者实例
    public Receiver receiver() {
        return new Receiver();
    }
    /**
     * 定义多例的Task
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Task Prototypetask() {
        return new Task();
    }
}
