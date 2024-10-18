package com.example.demo.repository;

import com.example.demo.model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "cart_items")
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
    List<CartItems> findByCartId(Long cartId);

    void deleteByCartId(Long cartId);
    CartItems findByCartIdAndProductIdAndSizeId(Long cartId, Long productId, Long sizeId);
}
