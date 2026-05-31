package com.fashion.controller;

import com.fashion.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String cart(Model model) {
        model.addAttribute("cartItems", cartService.getItems());
        model.addAttribute("total", cartService.getFormattedTotal());
        model.addAttribute("count", cartService.getItemCount());
        return "cart";
    }

    @PostMapping("/add")
    public String add(@RequestParam int productId, @RequestParam(defaultValue = "1") int quantity) {
        cartService.add(productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String update(@RequestParam int productId, @RequestParam int quantity) {
        cartService.update(productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam int productId) {
        cartService.remove(productId);
        return "redirect:/cart";
    }
}
