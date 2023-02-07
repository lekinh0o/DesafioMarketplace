package com.frwk.marketplace.shoppingCart.model.shoppingCart;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.frwk.marketplace.customer.model.Customer;
import com.frwk.marketplace.shoppingCart.model.enums.StatusCart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ShoppingCart")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {

    @Id
    @Column(name = "ID", nullable = false, columnDefinition = "BINARY(16) NOT NULL")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "ID_CUSTOMER", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
    private List<ShoppingCartItens> itens;

    @Column(name = "STATUS")
    @Enumerated(EnumType.ORDINAL)
    private StatusCart status;
}
