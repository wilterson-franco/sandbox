package com.wilterson.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends BaseEntity {

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<Item> items;

    @Column(nullable = false)
    private BigDecimal total;

    public void reparent() {

        if (CollectionUtils.isEmpty(items)) {
            return;
        }

        items.forEach(item -> {
            item.setCart(this);
            item.reparent();
        });
    }
}
