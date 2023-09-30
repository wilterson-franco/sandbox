package com.wilterson.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@Setter
@Getter
public class Cart extends BaseEntity {

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<Item> items;

    @Column(nullable = false)
    private BigDecimal total;

    public void reparent() {
        items.forEach(item -> {
            item.setCart(this);
            item.reparent();
        });
    }
}
