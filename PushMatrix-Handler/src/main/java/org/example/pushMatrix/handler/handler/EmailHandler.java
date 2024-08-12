package org.example.pushMatrix.handler.handler;
import org.example.pushMatrix.common.domain.TaskInfo;
import org.example.pushMatrix.common.enums.ChannelType;
import org.springframework.stereotype.Component;

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
