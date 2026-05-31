package com.fashion.controller;

import com.fashion.dto.ProductDTO;
import com.fashion.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SearchController {

    private final ProductService productService;

    public SearchController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/search")
    public String search(@RequestParam String q, Model model) {
        List<ProductDTO> results = productService.searchProducts(q);
        model.addAttribute("keyword", q);
        model.addAttribute("results", results);
        model.addAttribute("count", results.size());
        return "search";
    }
}
