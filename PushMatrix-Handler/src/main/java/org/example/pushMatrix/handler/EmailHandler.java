package org.example.pushMatrix.handler;
import org.example.pushMatrix.domain.TaskInfo;
import org.example.pushMatrix.enums.ChannelType;
import org.springframework.stereotype.Component;

import javax.net.ssl.HandshakeCompletedEvent;

/**
 * @Author 泽
 * @Date 2024/8/7 20:33
 * 邮件发送的处理
 */
@Component
public class EmailHandler extends Handler {
    public EmailHandler(){
        channelCode = ChannelType.EMAIL.getCode();
    }
    @Override
    public void handler(TaskInfo taskInfo) {

    }
}
