package com.bruno.trivia.repositories;
import com.bruno.trivia.entities.Neighborhood;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NeighborhoodRepository extends JpaRepository<Neighborhood, Long> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
}
