package com.bruno.trivia.entities;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter @NoArgsConstructor
public class DeliveryAddress {

    private String street;

    private String number;

    @Column(length = 50)
    private String complement;

    @Column(length = 300)
    private String referencePoint;

}
