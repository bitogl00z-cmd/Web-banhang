package com.fashion.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "product_id")
    private Integer id;
    @Column(name = "product_name", nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer price;
    @Column(name = "gender_nam")
    private Boolean genderNam;
    @Column(name = "gender_nu")
    private Boolean genderNu;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cluster_id")
    private Cluster cluster;
    @Column(name = "image_url")
    private String imageUrl;
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ProductFeature features;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    public Boolean getGenderNam() { return genderNam; }
    public void setGenderNam(Boolean genderNam) { this.genderNam = genderNam; }
    public Boolean getGenderNu() { return genderNu; }
    public void setGenderNu(Boolean genderNu) { this.genderNu = genderNu; }
    public Cluster getCluster() { return cluster; }
    public void setCluster(Cluster cluster) { this.cluster = cluster; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public ProductFeature getFeatures() { return features; }
    public void setFeatures(ProductFeature features) { this.features = features; }

    public String getGenderLabel() {
        if (Boolean.TRUE.equals(genderNam) && Boolean.TRUE.equals(genderNu)) return "Unisex";
        if (Boolean.TRUE.equals(genderNam)) return "Nam";
        if (Boolean.TRUE.equals(genderNu)) return "Nữ";
        return "Unisex";
    }
}
