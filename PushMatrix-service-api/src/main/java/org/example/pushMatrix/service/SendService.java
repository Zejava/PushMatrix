package org.example.pushMatrix.service;

import org.example.pushMatrix.domain.BatchSendRequest;
import org.example.pushMatrix.domain.SendRequest;
import org.example.pushMatrix.domain.SendResponse;


/**
 * @Author 泽
 * @Date 2024/8/3 20:50
 * 发送的接口
 */
public interface SendService {
    /**
     * 单文案发送接口
     * @param sendRequest
     * @return
     */

    SendResponse send(SendRequest sendRequest);


    /**
     * 多文案发送接口
     * @param batchSendRequest
     * @return
     */
    SendResponse batchSend(BatchSendRequest batchSendRequest);
}
