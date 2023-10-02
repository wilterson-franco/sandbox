package com.wilterson;

import com.wilterson.entity.Cart;
import com.wilterson.entity.Item;
import com.wilterson.repository.CartRepository;
import java.math.BigDecimal;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringJpaTuningApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJpaTuningApplication.class, args);
    }

    //    @Bean
    public CommandLineRunner commandLineRunner(CartRepository cartRepository) {
        return (args) -> {

            Set<Item> itemList1 = Set.of(
                    Item.builder().itemName("basketball").amount(new BigDecimal("40.15")).build(),
                    Item.builder().itemName("t-shirt").amount(new BigDecimal("8.99")).build(),
                    Item.builder().itemName("sneakers").amount(new BigDecimal("37.50")).build(),
                    Item.builder().itemName("socks").amount(new BigDecimal("5.25")).build());

            Set<Item> itemList2 = Set.of(
                    Item.builder().itemName("glasses").amount(new BigDecimal("19.65")).build(),
                    Item.builder().itemName("shorts").amount(new BigDecimal("15.00")).build(),
                    Item.builder().itemName("hat").amount(new BigDecimal("13.19")).build());

            Set<Cart> carts = Set.of(
                    Cart.builder().items(itemList1).build(),
                    Cart.builder().items(itemList2).build());

            cartRepository.saveAll(carts);
        };
    }

    private static BigDecimal getItemsTotalAmount(Set<Item> itemList1) {
        return itemList1.stream().map(Item::getAmount).reduce(new BigDecimal(0), BigDecimal::add);
    }
}
