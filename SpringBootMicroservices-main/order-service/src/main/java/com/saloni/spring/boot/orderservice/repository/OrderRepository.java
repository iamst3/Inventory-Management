package com.saloni.spring.boot.orderservice.repository;


import com.saloni.spring.boot.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}