package org.example.pushMatrix;

import org.example.pushMatrix.pojo.entity.SmsParam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<SmsParam,Long> {
}
