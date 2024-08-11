package org.example.pushMatrix;

import org.example.pushMatrix.domain.SmsParam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<SmsParam,Long> {
}
