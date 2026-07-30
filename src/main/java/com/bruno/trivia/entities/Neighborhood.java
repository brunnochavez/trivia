package com.bruno.trivia.entities;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "neighborhood_tb")
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE neighborhood_tb SET deleted = true WHERE id = ? ")
@SQLRestriction("deleted = false")
public class Neighborhood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal deliveryFee;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean deleted;

    public Neighborhood(String name, BigDecimal deliveryFee) {
        this.name = name;
        this.deliveryFee = deliveryFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Neighborhood)) return false;
        Neighborhood neighborhood = (Neighborhood) o;
        return name != null && Objects.equals(name, neighborhood.getName());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
