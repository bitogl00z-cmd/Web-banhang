package com.fashion.dto;

public class CartItemDTO {
    private Integer productId;
    private String productName;
    private Integer price;
    private String formattedPrice;
    private String imageUrl;
    private int quantity;

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    public String getFormattedPrice() { return formattedPrice; }
    public void setFormattedPrice(String formattedPrice) { this.formattedPrice = formattedPrice; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getSubtotal() { return price * quantity; }
    public String getFormattedSubtotal() { return String.format("%,d\u0111", getSubtotal()); }
}
