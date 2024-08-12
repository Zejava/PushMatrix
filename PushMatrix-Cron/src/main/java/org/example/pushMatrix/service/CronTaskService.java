package org.example.pushMatrix.service;

import org.example.pushMatrix.entity.XxlJobGroup;
import org.example.pushMatrix.entity.XxlJobInfo;
import org.example.pushMatrix.vo.BasicResultVO;

/**
 * @Author 泽
 * @Date 2024/8/12 18:08
 *  定时任务服务
 */
public interface CronTaskService {
    /**
     * 新增/修改 定时任务
     *
     * @return 新增时返回任务Id，修改时无返回
     */
    BasicResultVO saveCronTask(XxlJobInfo xxlJobInfo);

    /**
     * 删除定时任务
     *
     * @param taskId
     */
    BasicResultVO deleteCronTask(Integer taskId);

    /**
     * 启动定时任务
     *
     * @param taskId
     */
    BasicResultVO startCronTask(Integer taskId);


    /**
     * 暂停定时任务
     *
     * @param taskId
     */
    BasicResultVO stopCronTask(Integer taskId);


    /**
     * 得到执行器Id
     *
     * @return
     */
    BasicResultVO getGroupId(String appName, String title);

    /**
     * 创建执行器
     */
    BasicResultVO createGroup(XxlJobGroup xxlJobGroup);
}
