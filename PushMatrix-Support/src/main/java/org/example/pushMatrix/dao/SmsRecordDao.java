package org.example.pushMatrix.dao;

import org.example.pushMatrix.domain.SmsRecord;
import org.springframework.data.repository.CrudRepository;

/**
 * @Author 泽
 * @Date 2024/8/2 22:55
 */
public interface SmsRecordDao extends CrudRepository<SmsRecord, Long> {
}
