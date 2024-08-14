package org.example.pushMatrix.support.dao;

import org.example.pushMatrix.support.domain.MessageTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author 泽
 * @Date 2024/8/2 22:54
 */
@Repository
public interface MessageTemplateDao extends CrudRepository<MessageTemplate, Long>, JpaSpecificationExecutor<MessageTemplate> {
    /**
     * 查询 列表（分页)
     * @param deleted  0：未删除 1：删除
     * @param pageable 分页对象
     * @return
     */
    List<MessageTemplate> findAllByIsDeletedEquals(Integer deleted, Pageable pageable);


    /**
     * 统计未删除的条数
     * @param deleted
     * @return
     */
    Long countByIsDeletedEquals(Integer deleted);
}
