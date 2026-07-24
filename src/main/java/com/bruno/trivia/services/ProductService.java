package com.bruno.trivia.services;
import com.bruno.trivia.dtos.ProductResponseDTO;
import com.bruno.trivia.entities.Product;
import com.bruno.trivia.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductResponseDTO findById(Long id){
        Product product = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Produto não encontrado!!")
        );
        return toResponseDto(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findAll(Pageable pageable){
        Page<ProductResponseDTO> listProducts = productRepository.findAllWithIngredients(pageable)
                .map(p -> toResponseDto(p));

        return listProducts;
    }

    private ProductResponseDTO toResponseDto(Product product){
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getBarcode(),
                product.getCostPrice(),
                product.getSalePrice(),
                product.getStockQuantity(),
                product.getIngredients(),
                product.isActive(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
