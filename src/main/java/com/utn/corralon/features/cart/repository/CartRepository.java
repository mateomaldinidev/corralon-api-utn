package com.utn.corralon.features.cart.repository;

import com.utn.corralon.features.cart.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
}
