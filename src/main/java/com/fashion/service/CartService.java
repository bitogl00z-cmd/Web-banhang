package com.fashion.service;

import com.fashion.dto.CartItemDTO;
import com.fashion.model.Product;
import com.fashion.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;
import java.util.stream.Collectors;

@Service
@SessionScope
public class CartService {

    private final Map<Integer, Integer> items = new LinkedHashMap<>();
    private final ProductRepository productRepository;

    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void add(int productId, int quantity) {
        items.merge(productId, quantity, Integer::sum);
    }

    public void update(int productId, int quantity) {
        if (quantity <= 0) {
            items.remove(productId);
        } else {
            items.put(productId, quantity);
        }
    }

    public void remove(int productId) {
        items.remove(productId);
    }

    public List<CartItemDTO> getItems() {
        return items.entrySet().stream().map(entry -> {
            Product p = productRepository.findById(entry.getKey()).orElse(null);
            if (p == null) return null;
            CartItemDTO dto = new CartItemDTO();
            dto.setProductId(p.getId());
            dto.setProductName(p.getName());
            dto.setPrice(p.getPrice());
            dto.setFormattedPrice(String.format("%,d\u0111", p.getPrice()));
            dto.setImageUrl(p.getImageUrl());
            dto.setQuantity(entry.getValue());
            return dto;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public int getTotal() {
        return getItems().stream().mapToInt(CartItemDTO::getSubtotal).sum();
    }

    public String getFormattedTotal() {
        return String.format("%,d\u0111", getTotal());
    }

    public int getItemCount() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }

    public void clear() {
        items.clear();
    }
}
