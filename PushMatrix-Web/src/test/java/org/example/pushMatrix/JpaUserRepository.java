package org.example.pushMatrix;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<SmsParam,Long> {
}
