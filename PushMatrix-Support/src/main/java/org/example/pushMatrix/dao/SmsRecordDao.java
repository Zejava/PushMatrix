package org.example.pushMatrix.dao;

import org.example.pushMatrix.domain.SmsRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author 泽
 * @Date 2024/8/2 22:55
 */
@Repository
public interface SmsRecordDao extends CrudRepository<SmsRecord, Long> {

}
