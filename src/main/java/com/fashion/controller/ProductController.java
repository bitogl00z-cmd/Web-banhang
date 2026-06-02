package com.fashion.controller;

import com.fashion.dto.ProductDTO;
import com.fashion.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/cluster/{id}")
    public String cluster(@PathVariable int id,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "name") String sort,
                          @RequestParam(defaultValue = "asc") String dir,
                          @RequestParam(defaultValue = "grid") String view,
                          Model model) {
        var cluster = productService.getClusterById(id);
        if (cluster.isEmpty()) return "redirect:/";
        Page<ProductDTO> products = productService.getProductsByCluster(id, page, 8, sort, dir);

        model.addAttribute("cluster", cluster.get());
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        model.addAttribute("view", view);
        model.addAttribute("count", productService.countByClusterId(id));
        return "cluster";
    }

    @GetMapping("/product/{id}")
    public String detail(@PathVariable int id, Model model) {
        ProductDTO product = productService.getProductDTO(id);
        if (product == null) return "redirect:/";
        List<ProductDTO> related = productService.getAllRelatedProducts(product.getClusterId(), id);

        model.addAttribute("product", product);
        model.addAttribute("related", related);
        return "product-detail";
    }
}
