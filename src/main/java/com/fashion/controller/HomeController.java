package com.fashion.controller;

import com.fashion.model.Cluster;
import com.fashion.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Cluster> clusters = productService.getAllClusters();
        model.addAttribute("clusters", clusters);
        return "index";
    }
}
