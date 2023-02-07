package com.frwk.marketplace.customer.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.frwk.marketplace.shoppingCart.model.shoppingCart.ShoppingCart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "NAME", length = 255)
    private String name;

    @JoinColumn(name = "ID_SHOPPING_CART")
    @OneToMany(fetch = FetchType.EAGER)
    private List<ShoppingCart> shoppingCart;

    @NotNull
    @Column(name = "IDENTIFICATION_CODE", length = 25)
    private String identificationCode;

    @NotNull
    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @NotNull
    @Column(name = "EMAIL")
    private String email;

}
