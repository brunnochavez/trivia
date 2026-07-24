package com.bruno.trivia.entities;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "product_tb")
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE product_tb SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, unique = true, length = 20)
    private String barcode;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal costPrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salePrice;

    @Column(nullable = false)
    private Integer stockQuantity;

    @ElementCollection
    private List<String> ingredients;

    private boolean active;

    @Version
    private Long version;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean deleted;

    @OneToMany(mappedBy = "product")
    private Set<OrderItem> orderItems = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return barcode != null && Objects.equals(barcode, product.getBarcode());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
