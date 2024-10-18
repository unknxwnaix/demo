package com.example.demo.controller;

import com.example.demo.model.CartItems;
import com.example.demo.model.Carts;
import com.example.demo.model.Orders;
import com.example.demo.model.Products;
import com.example.demo.model.Sizes;
import com.example.demo.repository.CartItemsRepository;
import com.example.demo.repository.CartsRepository;
import com.example.demo.repository.OrdersRepository;
import com.example.demo.repository.ProductsRepository;
import com.example.demo.repository.SizesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ShopController {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private SizesRepository sizesRepository;

    @Autowired
    private CartsRepository cartsRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<Products> products = productsRepository.findAll();
        List<Sizes> sizes = sizesRepository.findAll();
        String username = userDetails.getUsername();
        model.addAttribute("products", products);
        model.addAttribute("sizes", sizes);
        model.addAttribute("username", username);
        return "home";
    }

    @GetMapping("/cart")
    public String cart(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Carts cart = cartsRepository.findByUsername(username)
                .orElseGet(() -> createCartForUser(username));

        List<CartItems> cartItems = cartItemsRepository.findByCartId(cart.getId());
        List<Products> products = productsRepository.findAllById(cartItems.stream()
                .map(CartItems::getProductId)
                .toList());
        List<Sizes> sizes = sizesRepository.findAllById(cartItems.stream()
                .map(CartItems::getSizeId)
                .toList());

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("products", products);
        model.addAttribute("sizes", sizes);
        model.addAttribute("username", username);
        return "cart";
    }

    @PostMapping("/cart/add")
    @Transactional
    public String addToCart(@RequestParam Long productId, @RequestParam Long sizeId, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Carts cart = cartsRepository.findByUsername(username)
                .orElseGet(() -> createCartForUser(username)); // Создание корзины, если не найдена

        CartItems existingItem = cartItemsRepository.findByCartIdAndProductIdAndSizeId(cart.getId(), productId, sizeId);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            cartItemsRepository.save(existingItem);
        } else {
            CartItems cartItem = new CartItems();
            cartItem.setCartId(cart.getId());
            cartItem.setProductId(productId);
            cartItem.setSizeId(sizeId);
            cartItem.setQuantity(1);
            cartItemsRepository.save(cartItem);
        }

        return "redirect:/";
    }

    @PostMapping("/cart/update")
    @Transactional
    public String updateCartItem(@RequestParam Long itemId, @RequestParam Integer quantity) {
        if (quantity < 1) {
            cartItemsRepository.deleteById(itemId);
        } else {
            CartItems cartItem = cartItemsRepository.findById(itemId)
                    .orElseThrow(() -> new RuntimeException("Товар не найден в корзине"));
            cartItem.setQuantity(quantity);
            cartItemsRepository.save(cartItem);
        }
        return "redirect:/cart";
    }

    @PostMapping("/order")
    @Transactional
    public String createOrder(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Carts cart = cartsRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Корзина не найдена для пользователя: " + username));

        Orders order = new Orders();
        order.setUsername(username);
        order.setCart(cart);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("Created");
        ordersRepository.save(order);

        cartItemsRepository.deleteByCartId(cart.getId());

        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String viewOrders(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        List<Orders> orders = ordersRepository.findByUsername(username);
        model.addAttribute("orders", orders);
        return "orders";
    }

    private Carts createCartForUser(String username) {
        Carts newCart = new Carts();
        newCart.setUsername(username);
        newCart.setCreatedAt(LocalDateTime.now());
        newCart.setStatus("active");
        return cartsRepository.save(newCart);
    }
}