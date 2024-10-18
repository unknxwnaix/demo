package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "productList";
    }

    @GetMapping("/new")
    public String createProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "productForm";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        List<Product> products = productRepository.findByNameContaining(keyword);
        model.addAttribute("products", products);
        return "productList";
    }

    @PostMapping
    public String createProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable int id, Model model) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return "redirect:/products";
        }
        model.addAttribute("product", product.get());
        return "productEdit";
    }

    @PostMapping("/{id}")
    public String updateProduct(@PathVariable int id, @ModelAttribute Product product) {
        if (!productRepository.existsById(id)) {
            return "redirect:/products";
        }
        product.setId(id);
        productRepository.save(product);
        return "redirect:/products";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable int id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }
        return "redirect:/products";
    }

    @GetMapping("/{id}")
    public String viewProductDetails(@PathVariable int id, Model model) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
        }
        return "productDetails";
    }
}
