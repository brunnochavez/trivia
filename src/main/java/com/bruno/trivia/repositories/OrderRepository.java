package com.bruno.trivia.repositories;
import com.bruno.trivia.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
