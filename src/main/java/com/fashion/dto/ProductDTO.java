package com.fashion.dto;

public class ProductDTO {
    private Integer id;
    private String name;
    private Integer price;
    private String formattedPrice;
    private String genderLabel;
    private String imageUrl;
    private String clusterName;
    private Integer clusterId;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    public String getFormattedPrice() { return formattedPrice; }
    public void setFormattedPrice(String formattedPrice) { this.formattedPrice = formattedPrice; }
    public String getGenderLabel() { return genderLabel; }
    public void setGenderLabel(String genderLabel) { this.genderLabel = genderLabel; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getClusterName() { return clusterName; }
    public void setClusterName(String clusterName) { this.clusterName = clusterName; }
    public Integer getClusterId() { return clusterId; }
    public void setClusterId(Integer clusterId) { this.clusterId = clusterId; }
}
