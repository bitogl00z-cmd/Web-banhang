package com.fashion.controller;

import com.fashion.model.Order;
import com.fashion.model.OrderItem;
import com.fashion.repository.OrderRepository;
import com.fashion.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    private final CartService cartService;
    private final OrderRepository orderRepository;

    public CheckoutController(CartService cartService, OrderRepository orderRepository) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public String checkout(Model model) {
        if (cartService.getItemCount() == 0) return "redirect:/cart";
        model.addAttribute("cartItems", cartService.getItems());
        model.addAttribute("total", cartService.getFormattedTotal());
        return "checkout";
    }

    @PostMapping
    public String placeOrder(@RequestParam String fullName,
                             @RequestParam String phone,
                             @RequestParam String address,
                             @RequestParam(required = false) String note) {
        var cartItems = cartService.getItems();
        if (cartItems.isEmpty()) return "redirect:/cart";

        Order order = new Order();
        order.setFullName(fullName);
        order.setPhone(phone);
        order.setAddress(address);
        order.setNote(note);
        order.setTotal(cartService.getTotal());

        for (var item : cartItems) {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProductId(item.getProductId());
            oi.setProductName(item.getProductName());
            oi.setPrice(item.getPrice());
            oi.setQuantity(item.getQuantity());
            order.getItems().add(oi);
        }

        orderRepository.save(order);
        cartService.clear();
        return "redirect:/?order=success";
    }
}
