package com.utn.corralon.features.payment.repository;

import com.utn.corralon.features.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
