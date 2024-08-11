package org.example.pushMatrix;


import jakarta.annotation.Resource;
import org.example.pushMatrix.domain.SmsParam;
import org.springframework.stereotype.Service;


@Service
public class JpaServiceImpl implements JpaService{
@Resource
private JpaUserRepository jpaUserRepository;
    @Override
    public SmsParam insert(SmsParam smsParam) {


        return jpaUserRepository.save(smsParam);
    }
}
