package com.bruno.trivia.services;
import com.bruno.trivia.dtos.ProductRequestDTO;
import com.bruno.trivia.dtos.ProductResponseDTO;
import com.bruno.trivia.entities.Product;
import com.bruno.trivia.repositories.ProductRepository;
import jakarta.persistence.EntityExistsException;
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
        Page<ProductResponseDTO> listProducts = productRepository.findAll(pageable)
                .map(p -> toResponseDto(p));

        return listProducts;
    }

    @Transactional
    public ProductResponseDTO insert(ProductRequestDTO dto){


        if(dto.barcode() != null && productRepository.existsByBarcode(dto.barcode())){
            throw new EntityExistsException("Já existe um produto com este mesmo código de barras!");
        }

        Product product = toProduct(dto);
        product = productRepository.save(product);
        return toResponseDto(product);
    }

    @Transactional
    public ProductResponseDTO update(Long id, ProductRequestDTO dto){

        Product product = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Produto não encontrado!")
        );

        if(dto.barcode() != null && productRepository.existsByBarcodeAndIdNot(dto.barcode(), product.getId())){
            throw new EntityExistsException("Já existe um produto cadastrado com o mesmo código de barras!");
        }

        dtoToEntity(dto, product);
        product = productRepository.save(product);
        return toResponseDto(product);
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

    private Product toProduct(ProductRequestDTO dto){
        return new Product(
                dto.name(),
                dto.barcode(),
                dto.costPrice(),
                dto.salePrice(),
                dto.stockQuantity(),
                dto.ingredients(),
                dto.active()
        );
    }

    private void dtoToEntity(ProductRequestDTO dto, Product product){
        product.setName(dto.name());
        product.setBarcode(dto.barcode());
        product.setCostPrice(dto.costPrice());
        product.setSalePrice(dto.salePrice());
        product.setStockQuantity(dto.stockQuantity());
        product.getIngredients().clear();
        product.getIngredients().addAll(dto.ingredients());
        product.setActive(dto.active());
    }
}
