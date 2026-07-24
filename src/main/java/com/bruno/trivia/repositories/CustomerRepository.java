package com.bruno.trivia.repositories;
import com.bruno.trivia.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {


    Optional<Customer> findByPhone(String phone);

    Optional<Customer> findByName(String name);
}
