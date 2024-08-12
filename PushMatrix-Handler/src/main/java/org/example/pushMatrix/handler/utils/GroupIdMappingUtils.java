package org.example.pushMatrix.handler.utils;

import org.example.pushMatrix.common.domain.TaskInfo;
import org.example.pushMatrix.common.enums.ChannelType;
import org.example.pushMatrix.common.enums.MessageType;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 泽
 * @Date 2024/8/7 15:48
 */
public class GroupIdMappingUtils {
    /**
     * 获取所有的groupIds
     * (不同的渠道以及不同的消息类型拥有自己的groupId)
     */
    public static List<String> getAllGroupIds() {
        List<String> groupIds = new ArrayList<>();
        for (ChannelType channelType : ChannelType.values()) {
            for (MessageType messageType : MessageType.values()) {
                groupIds.add(channelType.getCode_en() + "." + messageType.getCode_en());
            }
        }
        return groupIds;
    }


    /**
     * 根据TaskInfo获取当前消息的groupId
     * @param taskInfo
     * @return
     */
    public static String getGroupIdByTaskInfo(TaskInfo taskInfo) {
        String channelCodeEn = ChannelType.getEnumByCode(taskInfo.getSendChannel()).getCode_en();
        String msgCodeEn = MessageType.getEnumByCode(taskInfo.getMsgType()).getCode_en();
        return channelCodeEn + "." + msgCodeEn;
    }
}
