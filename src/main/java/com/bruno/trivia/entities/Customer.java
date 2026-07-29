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
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "customer_tb")
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE customer_tb SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false)
    private String phone;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean deleted;

    public Customer(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return phone != null && Objects.equals(phone, customer.getPhone());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
