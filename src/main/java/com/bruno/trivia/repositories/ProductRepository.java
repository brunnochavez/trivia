package com.bruno.trivia.repositories;
import com.bruno.trivia.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByBarcode(String barcode);

    @EntityGraph(attributePaths = "ingredients")
    Page<Product> findAllWithIngredients(Pageable pageable);
}
