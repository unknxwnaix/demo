package com.example.demo.controller;

import com.example.demo.model.Brands;
import com.example.demo.model.Orders;
import com.example.demo.model.Products;
import com.example.demo.model.Sizes;
import com.example.demo.model.Types;
import com.example.demo.repository.BrandsRepository;
import com.example.demo.repository.ProductsRepository;
import com.example.demo.repository.OrdersRepository;
import com.example.demo.repository.SizesRepository;
import com.example.demo.repository.TypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ManagerController {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private BrandsRepository brandsRepository;

    @Autowired
    private TypesRepository typesRepository;

    @Autowired
    private SizesRepository sizesRepository;

    @GetMapping("/manager/orders")
    public String viewAllOrders(Model model) {
        List<Orders> orders = ordersRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        model.addAttribute("orders", orders);
        return "manager_orders";
    }

    @GetMapping("/manager/products")
    public String viewAllProducts(Model model) {
        List<Products> products = productsRepository.findAll();
        List<Brands> brands = brandsRepository.findAll();
        List<Types> types = typesRepository.findAll();
        List<Sizes> sizes = sizesRepository.findAll();

        model.addAttribute("products", products);
        model.addAttribute("brands", brands);
        model.addAttribute("types", types);
        model.addAttribute("sizes", sizes);
        return "manager_products";
    }

    @PostMapping("/manager/products/add")
    public String addProduct(@RequestParam String name, @RequestParam String description,
                             @RequestParam Double price, @RequestParam String imageUrl,
                             @RequestParam Long typeId, @RequestParam Long brandId, @RequestParam Long sizeId,
                             Model model) {
        try {
            Products newProduct = new Products();
            newProduct.setName(name);
            newProduct.setDescription(description);
            newProduct.setPrice(price);
            newProduct.setImageUrl(imageUrl);

            // Получаем объекты бренда, типа и размера из репозиториев
            Brands brand = brandsRepository.findById(brandId).orElseThrow(() -> new RuntimeException("Brand not found"));
            Types type = typesRepository.findById(typeId).orElseThrow(() -> new RuntimeException("Type not found"));
            Sizes size = sizesRepository.findById(sizeId).orElseThrow(() -> new RuntimeException("Size not found"));

            // Устанавливаем объекты в новый продукт
            newProduct.setBrand(brand);
            newProduct.setType(type);
            newProduct.setSize(size);

            productsRepository.save(newProduct);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ошибка при добавлении продукта: " + e.getMessage());
        }
        return "redirect:/manager/products";
    }

    @PostMapping("/manager/products/delete")
    public String deleteProduct(@RequestParam Long productId) {
        productsRepository.deleteById(productId);
        return "redirect:/manager/products";
    }

    @PostMapping("/manager/orders/updateStatus")
    public String updateOrderStatus(@RequestParam Long orderId, @RequestParam String status) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        ordersRepository.save(order);
        return "redirect:/manager/orders";
    }
}