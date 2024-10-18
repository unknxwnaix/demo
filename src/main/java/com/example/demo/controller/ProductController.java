package com.example.demo.controller;

import com.example.demo.dao.ProductDAO;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductDAO productDao;

    @Autowired
    public ProductController(ProductDAO productDao) {
        this.productDao = productDao;
    }

    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productDao.findAll();
        model.addAttribute("products", products);
        return "productList";
    }

    @GetMapping("/new")
    public String createProductForm(Model model) {
        return "productForm";
    }

    @PostMapping
    public String createProduct(@ModelAttribute Product product) {
        productDao.save(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable int id, Model model) {
        Product product = productDao.findById(id);
        if (product == null) {
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        return "productEdit";
    }

    @PostMapping("/{id}")
    public String updateProduct(@PathVariable int id, @ModelAttribute Product product) {
        productDao.update(id, product);
        return "redirect:/products";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable int id) {
        productDao.delete(id);
        return "redirect:/products";
    }

    @GetMapping("/{id}")
    public String viewProductDetails(@PathVariable int id, Model model) {
        Product product = productDao.findById(id);
        model.addAttribute("product", product);
        return "productDetails";
    }
}
