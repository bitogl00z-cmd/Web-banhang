# Fashion E-commerce Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build a fashion e-commerce website with 6 K-means style clusters using Spring Boot + Thymeleaf + Tailwind CSS

**Architecture:** Spring Boot MVC monolith with Thymeleaf server-side rendering. MySQL database with pre-clustered products (6 fashion styles). K-means algorithm implemented in Java for analysis visualization.

**Tech Stack:** Java 17, Spring Boot 3.x, Spring Data JPA, MySQL 8.0, Thymeleaf, Tailwind CSS (CDN), Chart.js

---

### Task 1: Project Scaffolding

**Files:**
- Create: `pom.xml`
- Create: `src/main/java/com/fashion/FashionApplication.java`
- Create: `src/main/resources/application.properties`

- [ ] **Step 1: Create pom.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.4</version>
    </parent>
    <groupId>com.fashion</groupId>
    <artifactId>fashion-recommendation</artifactId>
    <version>1.0.0</version>
    <name>fashion-recommendation</name>

    <properties>
        <java.version>17</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

- [ ] **Step 2: Create FashionApplication.java**

```java
package com.fashion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FashionApplication {
    public static void main(String[] args) {
        SpringApplication.run(FashionApplication.class, args);
    }
}
```

- [ ] **Step 3: Create application.properties**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fashion_recommendation?useUnicode=true&characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

server.port=8080
```

---

### Task 2: Entity Models

**Files:**
- Create: `src/main/java/com/fashion/model/Cluster.java`
- Create: `src/main/java/com/fashion/model/Product.java`
- Create: `src/main/java/com/fashion/model/ProductFeature.java`

- [ ] **Step 1: Create Cluster.java**

```java
package com.fashion.model;

import jakarta.persistence.*;

@Entity
@Table(name = "clusters")
public class Cluster {
    @Id
    @Column(name = "cluster_id")
    private Integer id;

    @Column(name = "cluster_name", nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
```

- [ ] **Step 2: Create Product.java**

```java
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
```

- [ ] **Step 3: Create ProductFeature.java**

```java
package com.fashion.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product_features")
public class ProductFeature {
    @Id
    @Column(name = "product_id")
    private Integer productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    private Boolean cateAo;
    private Boolean cateQuan;
    private Boolean cateVayDam;
    private Boolean catePhuKien;
    private Boolean matCotton;
    private Boolean matKaki;
    private Boolean matJean;
    private Boolean matThun;
    private Boolean matLenNi;
    private Boolean colorTrang;
    private Boolean colorDen;
    private Boolean colorMauNoi;
    private Boolean styleCasual;
    private Boolean styleStreetwear;
    private Boolean styleMinimalist;
    private Boolean styleKorean;
    private Boolean styleFormal;
    private Boolean styleSportswear;

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Boolean getCateAo() { return cateAo; }
    public void setCateAo(Boolean cateAo) { this.cateAo = cateAo; }
    public Boolean getCateQuan() { return cateQuan; }
    public void setCateQuan(Boolean cateQuan) { this.cateQuan = cateQuan; }
    public Boolean getCateVayDam() { return cateVayDam; }
    public void setCateVayDam(Boolean cateVayDam) { this.cateVayDam = cateVayDam; }
    public Boolean getCatePhuKien() { return catePhuKien; }
    public void setCatePhuKien(Boolean catePhuKien) { this.catePhuKien = catePhuKien; }
    public Boolean getMatCotton() { return matCotton; }
    public void setMatCotton(Boolean matCotton) { this.matCotton = matCotton; }
    public Boolean getMatKaki() { return matKaki; }
    public void setMatKaki(Boolean matKaki) { this.matKaki = matKaki; }
    public Boolean getMatJean() { return matJean; }
    public void setMatJean(Boolean matJean) { this.matJean = matJean; }
    public Boolean getMatThun() { return matThun; }
    public void setMatThun(Boolean matThun) { this.matThun = matThun; }
    public Boolean getMatLenNi() { return matLenNi; }
    public void setMatLenNi(Boolean matLenNi) { this.matLenNi = matLenNi; }
    public Boolean getColorTrang() { return colorTrang; }
    public void setColorTrang(Boolean colorTrang) { this.colorTrang = colorTrang; }
    public Boolean getColorDen() { return colorDen; }
    public void setColorDen(Boolean colorDen) { this.colorDen = colorDen; }
    public Boolean getColorMauNoi() { return colorMauNoi; }
    public void setColorMauNoi(Boolean colorMauNoi) { this.colorMauNoi = colorMauNoi; }
    public Boolean getStyleCasual() { return styleCasual; }
    public void setStyleCasual(Boolean styleCasual) { this.styleCasual = styleCasual; }
    public Boolean getStyleStreetwear() { return styleStreetwear; }
    public void setStyleStreetwear(Boolean styleStreetwear) { this.styleStreetwear = styleStreetwear; }
    public Boolean getStyleMinimalist() { return styleMinimalist; }
    public void setStyleMinimalist(Boolean styleMinimalist) { this.styleMinimalist = styleMinimalist; }
    public Boolean getStyleKorean() { return styleKorean; }
    public void setStyleKorean(Boolean styleKorean) { this.styleKorean = styleKorean; }
    public Boolean getStyleFormal() { return styleFormal; }
    public void setStyleFormal(Boolean styleFormal) { this.styleFormal = styleFormal; }
    public Boolean getStyleSportswear() { return styleSportswear; }
    public void setStyleSportswear(Boolean styleSportswear) { this.styleSportswear = styleSportswear; }

    public double[] toFeatureVector() {
        return new double[]{
            boolToDouble(cateAo), boolToDouble(cateQuan), boolToDouble(cateVayDam), boolToDouble(catePhuKien),
            boolToDouble(matCotton), boolToDouble(matKaki), boolToDouble(matJean), boolToDouble(matThun), boolToDouble(matLenNi),
            boolToDouble(colorTrang), boolToDouble(colorDen), boolToDouble(colorMauNoi),
            boolToDouble(styleCasual), boolToDouble(styleStreetwear), boolToDouble(styleMinimalist), boolToDouble(styleKorean), boolToDouble(styleFormal), boolToDouble(styleSportswear)
        };
    }

    private double boolToDouble(Boolean b) { return Boolean.TRUE.equals(b) ? 1.0 : 0.0; }

    public String getFeatureNames() {
        return "Áo,Quần,Váy/Đầm,Phụ kiện,Cotton,Kaki,Jean,Thun,Len/Nỉ,Trắng,Đen,Màu nổi,Casual,Streetwear,Minimalist,Korean,Formal,Sportswear";
    }
}
```

---

### Task 3: Repositories

**Files:**
- Create: `src/main/java/com/fashion/repository/ClusterRepository.java`
- Create: `src/main/java/com/fashion/repository/ProductRepository.java`
- Create: `src/main/java/com/fashion/repository/ProductFeatureRepository.java`

- [ ] **Step 1: Create ClusterRepository.java**

```java
package com.fashion.repository;

import com.fashion.model.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusterRepository extends JpaRepository<Cluster, Integer> {
}
```

- [ ] **Step 2: Create ProductRepository.java**

```java
package com.fashion.repository;

import com.fashion.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByClusterId(Integer clusterId, Pageable pageable);
    long countByClusterId(Integer clusterId);
    List<Product> findTop4ByClusterIdAndIdNot(Integer clusterId, Integer excludeId);
    List<Product> findByNameContainingIgnoreCase(String keyword);
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
}
```

- [ ] **Step 3: Create ProductFeatureRepository.java**

```java
package com.fashion.repository;

import com.fashion.model.ProductFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductFeatureRepository extends JpaRepository<ProductFeature, Integer> {
    List<ProductFeature> findAll();

    @Query("SELECT pf FROM ProductFeature pf JOIN FETCH pf.product p JOIN FETCH p.cluster")
    List<ProductFeature> findAllWithProductAndCluster();
}
```

---

### Task 4: DTOs

**Files:**
- Create: `src/main/java/com/fashion/dto/ProductDTO.java`
- Create: `src/main/java/com/fashion/dto/CartItemDTO.java`
- Create: `src/main/java/com/fashion/dto/KMeansResultDTO.java`

- [ ] **Step 1: Create ProductDTO.java**

```java
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
```

- [ ] **Step 2: Create CartItemDTO.java**

```java
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
    public String getFormattedSubtotal() { return String.format("%,d", getSubtotal()); }
}
```

- [ ] **Step 3: Create KMeansResultDTO.java**

```java
package com.fashion.dto;

import java.util.List;
import java.util.Map;

public class KMeansResultDTO {
    private List<ClusterInfo> clusters;
    private Map<Integer, double[]> centroids;
    private List<ScatterPoint> scatterData;
    private Map<Integer, Map<String, Double>> featureDistribution;

    public List<ClusterInfo> getClusters() { return clusters; }
    public void setClusters(List<ClusterInfo> clusters) { this.clusters = clusters; }
    public Map<Integer, double[]> getCentroids() { return centroids; }
    public void setCentroids(Map<Integer, double[]> centroids) { this.centroids = centroids; }
    public List<ScatterPoint> getScatterData() { return scatterData; }
    public void setScatterData(List<ScatterPoint> scatterData) { this.scatterData = scatterData; }
    public Map<Integer, Map<String, Double>> getFeatureDistribution() { return featureDistribution; }
    public void setFeatureDistribution(Map<Integer, Map<String, Double>> featureDistribution) { this.featureDistribution = featureDistribution; }

    public static class ClusterInfo {
        private int id;
        private String name;
        private long productCount;

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public long getProductCount() { return productCount; }
        public void setProductCount(long productCount) { this.productCount = productCount; }
    }

    public static class ScatterPoint {
        private double x;
        private double y;
        private int clusterId;
        private String productName;

        public double getX() { return x; }
        public void setX(double x) { this.x = x; }
        public double getY() { return y; }
        public void setY(double y) { this.y = y; }
        public int getClusterId() { return clusterId; }
        public void setClusterId(int clusterId) { this.clusterId = clusterId; }
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
    }
}
```

---

### Task 5: ProductService

**Files:**
- Create: `src/main/java/com/fashion/service/ProductService.java`

- [ ] **Step 1: Create ProductService.java**

```java
package com.fashion.service;

import com.fashion.dto.ProductDTO;
import com.fashion.model.Cluster;
import com.fashion.model.Product;
import com.fashion.repository.ClusterRepository;
import com.fashion.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ClusterRepository clusterRepository;

    public ProductService(ProductRepository productRepository, ClusterRepository clusterRepository) {
        this.productRepository = productRepository;
        this.clusterRepository = clusterRepository;
    }

    public List<Cluster> getAllClusters() {
        return clusterRepository.findAll();
    }

    public Optional<Cluster> getClusterById(int id) {
        return clusterRepository.findById(id);
    }

    public Page<ProductDTO> getProductsByCluster(int clusterId, int page, int size, String sortBy, String sortDir) {
        Sort sort;
        if ("price".equals(sortBy)) {
            sort = "desc".equals(sortDir) ? Sort.by("price").descending() : Sort.by("price").ascending();
        } else {
            sort = Sort.by("name").ascending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findByClusterId(clusterId, pageable).map(this::toDTO);
    }

    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    public ProductDTO getProductDTO(int id) {
        return productRepository.findById(id).map(this::toDTO).orElse(null);
    }

    public List<ProductDTO> getRelatedProducts(int clusterId, int excludeId) {
        return productRepository.findTop4ByClusterIdAndIdNot(clusterId, excludeId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    public List<ProductDTO> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    public Page<ProductDTO> searchProductsPaged(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByNameContainingIgnoreCase(keyword, pageable).map(this::toDTO);
    }

    public long countByClusterId(int clusterId) {
        return productRepository.countByClusterId(clusterId);
    }

    private ProductDTO toDTO(Product p) {
        ProductDTO dto = new ProductDTO();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setPrice(p.getPrice());
        dto.setFormattedPrice(String.format("%,d₫", p.getPrice()));
        dto.setGenderLabel(p.getGenderLabel());
        dto.setImageUrl(p.getImageUrl());
        if (p.getCluster() != null) {
            dto.setClusterName(p.getCluster().getName());
            dto.setClusterId(p.getCluster().getId());
        }
        return dto;
    }
}
```

---

### Task 6: KMeansService

**Files:**
- Create: `src/main/java/com/fashion/service/KMeansService.java`

- [ ] **Step 1: Create KMeansService.java**

```java
package com.fashion.service;

import com.fashion.dto.KMeansResultDTO;
import com.fashion.model.ProductFeature;
import com.fashion.repository.ClusterRepository;
import com.fashion.repository.ProductFeatureRepository;
import com.fashion.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class KMeansService {

    private final ProductFeatureRepository featureRepository;
    private final ClusterRepository clusterRepository;
    private final ProductRepository productRepository;

    public KMeansService(ProductFeatureRepository featureRepository,
                         ClusterRepository clusterRepository,
                         ProductRepository productRepository) {
        this.featureRepository = featureRepository;
        this.clusterRepository = clusterRepository;
        this.productRepository = productRepository;
    }

    public KMeansResultDTO analyze() {
        List<ProductFeature> allFeatures = featureRepository.findAllWithProductAndCluster();

        List<KMeansResultDTO.ClusterInfo> clusterInfos = clusterRepository.findAll().stream().map(c -> {
            KMeansResultDTO.ClusterInfo ci = new KMeansResultDTO.ClusterInfo();
            ci.setId(c.getId());
            ci.setName(c.getName());
            ci.setProductCount(productRepository.countByClusterId(c.getId()));
            return ci;
        }).collect(Collectors.toList());

        Map<Integer, double[]> centroids = computeCentroids(allFeatures);
        List<KMeansResultDTO.ScatterPoint> scatterData = computeScatter(allFeatures, centroids);
        Map<Integer, Map<String, Double>> featureDist = computeFeatureDistribution(allFeatures);

        KMeansResultDTO result = new KMeansResultDTO();
        result.setClusters(clusterInfos);
        result.setCentroids(centroids);
        result.setScatterData(scatterData);
        result.setFeatureDistribution(featureDist);
        return result;
    }

    private Map<Integer, double[]> computeCentroids(List<ProductFeature> features) {
        Map<Integer, List<double[]>> grouped = new HashMap<>();
        for (ProductFeature pf : features) {
            int cid = pf.getProduct().getCluster().getId();
            grouped.computeIfAbsent(cid, k -> new ArrayList<>()).add(pf.toFeatureVector());
        }
        Map<Integer, double[]> centroids = new HashMap<>();
        for (Map.Entry<Integer, List<double[]>> entry : grouped.entrySet()) {
            List<double[]> vectors = entry.getValue();
            double[] centroid = new double[18];
            for (double[] v : vectors) {
                for (int i = 0; i < 18; i++) centroid[i] += v[i];
            }
            for (int i = 0; i < 18; i++) centroid[i] /= vectors.size();
            centroids.put(entry.getKey(), centroid);
        }
        return centroids;
    }

    private List<KMeansResultDTO.ScatterPoint> computeScatter(List<ProductFeature> features,
                                                                Map<Integer, double[]> centroids) {
        List<KMeansResultDTO.ScatterPoint> points = new ArrayList<>();
        for (ProductFeature pf : features) {
            double[] vec = pf.toFeatureVector();
            int cid = pf.getProduct().getCluster().getId();
            double[] centroid = centroids.get(cid);

            // PCA-like reduction: use feature dims 0-1 (cateAo, cateQuan) as rough 2D projection
            double x = vec[0] * 1.0 + vec[2] * 0.5;
            double y = 0;
            for (int i = 3; i < 18; i++) y += vec[i] * (i % 3 == 0 ? 0.5 : 0.3);

            // Add jitter to avoid overlap
            x += (Math.random() - 0.5) * 0.1;
            y += (Math.random() - 0.5) * 0.1;

            KMeansResultDTO.ScatterPoint sp = new KMeansResultDTO.ScatterPoint();
            sp.setX(Math.round(x * 100) / 100.0);
            sp.setY(Math.round(y * 100) / 100.0);
            sp.setClusterId(cid);
            sp.setProductName(pf.getProduct().getName());
            points.add(sp);
        }
        return points;
    }

    private Map<Integer, Map<String, Double>> computeFeatureDistribution(List<ProductFeature> features) {
        String[] featureNames = {"Áo","Quần","Váy/Đầm","Phụ kiện","Cotton","Kaki","Jean","Thun","Len/Nỉ","Trắng","Đen","Màu nổi","Casual","Streetwear","Minimalist","Korean","Formal","Sportswear"};
        Map<Integer, Map<String, Double>> result = new LinkedHashMap<>();
        Map<Integer, List<ProductFeature>> grouped = features.stream()
                .collect(Collectors.groupingBy(pf -> pf.getProduct().getCluster().getId()));

        for (Map.Entry<Integer, List<ProductFeature>> entry : grouped.entrySet()) {
            Map<String, Double> dist = new LinkedHashMap<>();
            List<ProductFeature> list = entry.getValue();
            double[] sums = new double[18];
            for (ProductFeature pf : list) {
                double[] vec = pf.toFeatureVector();
                for (int i = 0; i < 18; i++) sums[i] += vec[i];
            }
            for (int i = 0; i < 18; i++) {
                dist.put(featureNames[i], Math.round(sums[i] / list.size() * 100) / 100.0);
            }
            result.put(entry.getKey(), dist);
        }
        return result;
    }
}
```

---

### Task 7: CartService

**Files:**
- Create: `src/main/java/com/fashion/service/CartService.java`

- [ ] **Step 1: Create CartService.java**

```java
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
            dto.setFormattedPrice(String.format("%,d₫", p.getPrice()));
            dto.setImageUrl(p.getImageUrl());
            dto.setQuantity(entry.getValue());
            return dto;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public int getTotal() {
        return getItems().stream().mapToInt(CartItemDTO::getSubtotal).sum();
    }

    public String getFormattedTotal() {
        return String.format("%,d₫", getTotal());
    }

    public int getItemCount() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }

    public void clear() {
        items.clear();
    }
}
```

---

### Task 8: Controllers

**Files:**
- Create: `src/main/java/com/fashion/controller/HomeController.java`
- Create: `src/main/java/com/fashion/controller/ProductController.java`
- Create: `src/main/java/com/fashion/controller/CartController.java`
- Create: `src/main/java/com/fashion/controller/CheckoutController.java`
- Create: `src/main/java/com/fashion/controller/SearchController.java`
- Create: `src/main/java/com/fashion/controller/KMeansController.java`

- [ ] **Step 1: Create HomeController.java**

```java
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
```

- [ ] **Step 2: Create ProductController.java**

```java
package com.fashion.controller;

import com.fashion.dto.ProductDTO;
import com.fashion.model.Product;
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
        List<ProductDTO> related = productService.getRelatedProducts(product.getClusterId(), id);

        model.addAttribute("product", product);
        model.addAttribute("related", related);
        return "product-detail";
    }
}
```

- [ ] **Step 3: Create CartController.java**

```java
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
        model.addAttribute("items", cartService.getItems());
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
```

- [ ] **Step 4: Create CheckoutController.java**

```java
package com.fashion.controller;

import com.fashion.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    private final CartService cartService;

    public CheckoutController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String checkout(Model model) {
        if (cartService.getItemCount() == 0) return "redirect:/cart";
        model.addAttribute("items", cartService.getItems());
        model.addAttribute("total", cartService.getFormattedTotal());
        return "checkout";
    }

    @PostMapping
    public String placeOrder(@RequestParam String fullName,
                             @RequestParam String phone,
                             @RequestParam String address) {
        cartService.clear();
        return "redirect:/?order=success";
    }
}
```

- [ ] **Step 5: Create SearchController.java**

```java
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
```

- [ ] **Step 6: Create KMeansController.java**

```java
package com.fashion.controller;

import com.fashion.dto.KMeansResultDTO;
import com.fashion.service.KMeansService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kmeans")
public class KMeansController {

    private final KMeansService kMeansService;

    public KMeansController(KMeansService kMeansService) {
        this.kMeansService = kMeansService;
    }

    @GetMapping
    public String analysis(Model model) {
        KMeansResultDTO result = kMeansService.analyze();
        model.addAttribute("result", result);
        return "kmeans";
    }
}
```

---

### Task 9: Thymeleaf Templates — Layout & Index

**Files:**
- Create: `src/main/resources/templates/layout.html`
- Create: `src/main/resources/templates/index.html`

- [ ] **Step 1: Create layout.html (Thymeleaf fragments)**

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="vi">
<head th:fragment="head(title)">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title th:text="${title}">Fashion</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/css/style.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body class="font-['Inter'] bg-gray-50">
<div th:fragment="navbar">
    <nav class="bg-white shadow-sm sticky top-0 z-50">
        <div class="max-w-7xl mx-auto px-4">
            <div class="flex items-center justify-between h-16">
                <a href="/" class="text-2xl font-bold text-gray-900">Fashion<span class="text-pink-500">Style</span></a>
                <form action="/search" method="get" class="flex-1 max-w-md mx-4">
                    <input type="text" name="q" placeholder="Tìm sản phẩm..." class="w-full px-4 py-2 border border-gray-300 rounded-full text-sm focus:outline-none focus:ring-2 focus:ring-pink-400">
                </form>
                <div class="flex items-center gap-4">
                    <a href="/cart" class="relative text-gray-700 hover:text-pink-500">
                        <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 100 4 2 2 0 000-4z"/></svg>
                        <span th:if="${session.cartService != null}" class="absolute -top-2 -right-2 bg-pink-500 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center" th:text="${session.cartService.itemCount}">0</span>
                    </a>
                </div>
            </div>
        </div>
    </nav>
</div>
<div th:fragment="footer">
    <footer class="bg-gray-900 text-gray-400 py-8 mt-12">
        <div class="max-w-7xl mx-auto px-4 text-center">
            <p class="text-white font-semibold text-lg">FashionStyle</p>
            <p class="text-sm mt-2">Thời trang cho mọi phong cách — © 2026</p>
        </div>
    </footer>
</div>
</body>
</html>
```

- [ ] **Step 2: Create index.html**

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="vi">
<head th:replace="~{layout :: head('FashionStyle - Thời trang cho mọi phong cách')}"></head>
<body>
<div th:replace="~{layout :: navbar}"></div>

<!-- Hero -->
<section class="relative bg-gradient-to-r from-purple-600 via-pink-500 to-orange-400 text-white">
    <div class="max-w-7xl mx-auto px-4 py-24 text-center">
        <h1 class="text-5xl font-bold mb-4">Thời trang cho mọi phong cách</h1>
        <p class="text-xl mb-8 opacity-90">Khám phá bộ sưu tập thời trang được phân loại thông minh theo 6 phong cách</p>
        <a href="#clusters" class="inline-block bg-white text-pink-600 font-semibold px-8 py-3 rounded-full hover:bg-gray-100 transition">Khám phá ngay</a>
    </div>
</section>

<!-- Cluster Cards -->
<section id="clusters" class="max-w-7xl mx-auto px-4 py-16">
    <h2 class="text-3xl font-bold text-center mb-12">Danh mục phong cách</h2>
    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        <div th:each="c : ${clusters}" class="bg-white rounded-2xl shadow-md hover:shadow-xl transition overflow-hidden group cursor-pointer" onclick="window.location='/cluster/'+${c.id}">
            <div class="h-40 flex items-center justify-center text-6xl" th:classappend="'bg-gradient-to-br ' + (${c.id} == 0 ? 'from-green-400 to-blue-500' : ${c.id} == 1 ? 'from-yellow-400 to-red-500' : ${c.id} == 2 ? 'from-blue-600 to-indigo-800' : ${c.id} == 3 ? 'from-pink-300 to-purple-500' : ${c.id} == 4 ? 'from-gray-100 to-gray-400' : 'from-teal-300 to-green-400')">
                <span th:text="${c.id} == 0 ? '🏃' : ${c.id} == 1 ? '👟' : ${c.id} == 2 ? '👔' : ${c.id} == 3 ? '👗' : ${c.id} == 4 ? '🌸' : '🛍️'"></span>
            </div>
            <div class="p-5">
                <h3 class="font-bold text-lg" th:text="${c.name}">Name</h3>
                <p class="text-gray-500 text-sm mt-1" th:text="${c.description}">Description</p>
                <span class="mt-3 inline-block text-pink-500 font-medium text-sm">Xem thêm →</span>
            </div>
        </div>
    </div>
</section>

<div th:replace="~{layout :: footer}"></div>
</body>
</html>
```

---

### Task 10: Cluster & Product Detail Templates

**Files:**
- Create: `src/main/resources/templates/cluster.html`
- Create: `src/main/resources/templates/product-detail.html`

- [ ] **Step 1: Create cluster.html**

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="vi">
<head th:replace="~{layout :: head(${cluster.name} + ' - FashionStyle')}"></head>
<body>
<div th:replace="~{layout :: navbar}"></div>

<div class="max-w-7xl mx-auto px-4 py-8">
    <div class="mb-8">
        <a href="/" class="text-gray-500 hover:text-pink-500 text-sm">← Trang chủ</a>
        <h1 class="text-3xl font-bold mt-2" th:text="${cluster.name}"></h1>
        <p class="text-gray-500 mt-1" th:text="${cluster.description}"></p>
        <p class="text-sm text-gray-400 mt-1" th:text="'(' + ${count} + ' sản phẩm)'"></p>
    </div>

    <!-- Toolbar -->
    <div class="flex items-center justify-between mb-6">
        <div class="flex gap-2">
            <a th:href="@{/cluster/${cluster.id}(page=${currentPage}, sort=${sort}, dir=${dir}, view='grid')}" class="px-3 py-2 border rounded-lg" th:classappend="${view == 'grid'} ? 'bg-pink-500 text-white border-pink-500' : ''">📐 Grid</a>
            <a th:href="@{/cluster/${cluster.id}(page=${currentPage}, sort=${sort}, dir=${dir}, view='list')}" class="px-3 py-2 border rounded-lg" th:classappend="${view == 'list'} ? 'bg-pink-500 text-white border-pink-500' : ''">📄 List</a>
        </div>
        <div class="flex gap-2">
            <a th:href="@{/cluster/${cluster.id}(page=${currentPage}, sort='name', dir='asc', view=${view})}" class="px-3 py-2 border rounded-lg text-sm hover:bg-gray-100">Tên A-Z</a>
            <a th:href="@{/cluster/${cluster.id}(page=${currentPage}, sort='price', dir='asc', view=${view})}" class="px-3 py-2 border rounded-lg text-sm hover:bg-gray-100">Giá ↑</a>
            <a th:href="@{/cluster/${cluster.id}(page=${currentPage}, sort='price', dir='desc', view=${view})}" class="px-3 py-2 border rounded-lg text-sm hover:bg-gray-100">Giá ↓</a>
        </div>
    </div>

    <!-- Grid View -->
    <div th:if="${view == 'grid'}" class="grid grid-cols-2 md:grid-cols-4 gap-4">
        <div th:each="p : ${products}" class="bg-white rounded-xl shadow-sm hover:shadow-lg transition overflow-hidden" onclick="window.location='/product/'+${p.id}">
            <div class="h-48 bg-gray-100 flex items-center justify-center text-gray-400 text-sm" th:text="${p.name}">img</div>
            <div class="p-3">
                <span class="text-xs px-2 py-1 rounded-full bg-gray-100 text-gray-600" th:text="${p.genderLabel}"></span>
                <h3 class="font-semibold mt-1 text-sm" th:text="${p.name}">Name</h3>
                <p class="text-pink-600 font-bold mt-1" th:text="${p.formattedPrice}">Price</p>
            </div>
        </div>
    </div>

    <!-- List View -->
    <div th:if="${view == 'list'}" class="space-y-4">
        <div th:each="p : ${products}" class="bg-white rounded-xl shadow-sm hover:shadow-lg transition flex overflow-hidden cursor-pointer" onclick="window.location='/product/'+${p.id}">
            <div class="w-32 h-32 bg-gray-100 flex items-center justify-center text-gray-400 text-sm flex-shrink-0">img</div>
            <div class="p-4 flex-1 flex flex-col justify-between">
                <div>
                    <span class="text-xs px-2 py-1 rounded-full bg-gray-100 text-gray-600" th:text="${p.genderLabel}"></span>
                    <h3 class="font-semibold mt-1" th:text="${p.name}">Name</h3>
                    <p class="text-gray-500 text-sm mt-1" th:text="${p.clusterName}">Cluster</p>
                </div>
                <p class="text-pink-600 font-bold text-lg" th:text="${p.formattedPrice}">Price</p>
            </div>
        </div>
    </div>

    <!-- Pagination -->
    <div th:if="${totalPages > 1}" class="flex justify-center gap-2 mt-8">
        <a th:each="i : ${#numbers.sequence(0, totalPages-1)}"
           th:href="@{/cluster/${cluster.id}(page=${i}, sort=${sort}, dir=${dir}, view=${view})}"
           class="px-4 py-2 border rounded-lg text-sm"
           th:classappend="${i == currentPage} ? 'bg-pink-500 text-white border-pink-500' : 'hover:bg-gray-100'"
           th:text="${i + 1}">1</a>
    </div>
</div>

<div th:replace="~{layout :: footer}"></div>
</body>
</html>
```

- [ ] **Step 2: Create product-detail.html**

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="vi">
<head th:replace="~{layout :: head(${product.name} + ' - FashionStyle')}"></head>
<body>
<div th:replace="~{layout :: navbar}"></div>

<div class="max-w-7xl mx-auto px-4 py-8">
    <a href="/cluster/1" class="text-gray-500 hover:text-pink-500 text-sm">← Quay lại</a>

    <div class="flex flex-col md:flex-row gap-8 mt-6">
        <div class="md:w-1/2">
            <div class="bg-gray-100 rounded-2xl h-96 flex items-center justify-center text-gray-400 text-lg">
                [Hình ảnh: <span th:text="${product.name}"></span>]
            </div>
        </div>
        <div class="md:w-1/2">
            <span class="text-xs px-2 py-1 rounded-full bg-pink-100 text-pink-600" th:text="${product.genderLabel}"></span>
            <span class="text-xs px-2 py-1 rounded-full bg-gray-100 text-gray-600 ml-2" th:text="${product.clusterName}"></span>
            <h1 class="text-3xl font-bold mt-3" th:text="${product.name}">Name</h1>
            <p class="text-3xl text-pink-600 font-bold mt-4" th:text="${product.formattedPrice}">Price</p>
            <form action="/cart/add" method="post" class="mt-8">
                <input type="hidden" name="productId" th:value="${product.id}">
                <label class="block text-sm text-gray-600 mb-1">Số lượng</label>
                <div class="flex items-center gap-4">
                    <input type="number" name="quantity" value="1" min="1" class="w-20 px-3 py-2 border rounded-lg text-center">
                    <button type="submit" class="flex-1 bg-pink-500 text-white font-semibold py-3 rounded-xl hover:bg-pink-600 transition">Thêm vào giỏ hàng</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Related Products -->
    <div th:if="${#lists.size(related) > 0}" class="mt-16">
        <h2 class="text-2xl font-bold mb-6">Sản phẩm cùng phong cách</h2>
        <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
            <div th:each="r : ${related}" class="bg-white rounded-xl shadow-sm hover:shadow-lg transition overflow-hidden cursor-pointer" onclick="window.location='/product/'+${r.id}">
                <div class="h-40 bg-gray-100 flex items-center justify-center text-gray-400 text-sm">img</div>
                <div class="p-3">
                    <h3 class="font-semibold text-sm" th:text="${r.name}">Name</h3>
                    <p class="text-pink-600 font-bold text-sm mt-1" th:text="${r.formattedPrice}">Price</p>
                </div>
            </div>
        </div>
    </div>
</div>

<div th:replace="~{layout :: footer}"></div>
</body>
</html>
```

---

### Task 11: Cart & Checkout Templates

**Files:**
- Create: `src/main/resources/templates/cart.html`
- Create: `src/main/resources/templates/checkout.html`

- [ ] **Step 1: Create cart.html**

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="vi">
<head th:replace="~{layout :: head('Giỏ hàng - FashionStyle')}"></head>
<body>
<div th:replace="~{layout :: navbar}"></div>

<div class="max-w-4xl mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-8">Giỏ hàng</h1>

    <div th:if="${#lists.size(items) == 0}" class="text-center py-16 text-gray-500">
        <p class="text-6xl mb-4">🛒</p>
        <p class="text-lg">Giỏ hàng trống</p>
        <a href="/" class="inline-block mt-4 text-pink-500 hover:underline">Mua sắm ngay</a>
    </div>

    <div th:if="${#lists.size(items) > 0}">
        <div th:each="item : ${items}" class="bg-white rounded-xl shadow-sm p-4 mb-4 flex items-center gap-4">
            <div class="w-20 h-20 bg-gray-100 rounded-lg flex-shrink-0 flex items-center justify-center text-gray-400 text-xs">img</div>
            <div class="flex-1">
                <h3 class="font-semibold" th:text="${item.productName}">Name</h3>
                <p class="text-pink-600 font-bold" th:text="${item.formattedPrice}">Price</p>
            </div>
            <form action="/cart/update" method="post" class="flex items-center gap-2">
                <input type="hidden" name="productId" th:value="${item.productId}">
                <input type="number" name="quantity" th:value="${item.quantity}" min="1" class="w-16 px-2 py-1 border rounded text-center text-sm" onchange="this.form.submit()">
            </form>
            <p class="font-semibold w-24 text-right" th:text="${item.formattedSubtotal} + '₫'">Subtotal</p>
            <form action="/cart/remove" method="post">
                <input type="hidden" name="productId" th:value="${item.productId}">
                <button type="submit" class="text-red-400 hover:text-red-600 text-sm">✕</button>
            </form>
        </div>

        <div class="bg-white rounded-xl shadow-sm p-6 mt-6">
            <div class="flex justify-between items-center">
                <span class="text-lg font-semibold">Tổng cộng:</span>
                <span class="text-2xl font-bold text-pink-600" th:text="${total}">0₫</span>
            </div>
            <a href="/checkout" class="block text-center bg-pink-500 text-white font-semibold py-3 rounded-xl hover:bg-pink-600 transition mt-6">Tiến hành thanh toán</a>
        </div>
    </div>
</div>

<div th:replace="~{layout :: footer}"></div>
</body>
</html>
```

- [ ] **Step 2: Create checkout.html**

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="vi">
<head th:replace="~{layout :: head('Thanh toán - FashionStyle')}"></head>
<body>
<div th:replace="~{layout :: navbar}"></div>

<div class="max-w-4xl mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-8">Thanh toán</h1>

    <form action="/checkout" method="post" class="grid grid-cols-1 md:grid-cols-2 gap-8">
        <div class="bg-white rounded-xl shadow-sm p-6">
            <h2 class="font-semibold text-lg mb-4">Thông tin giao hàng</h2>
            <div class="space-y-4">
                <div>
                    <label class="block text-sm text-gray-600 mb-1">Họ tên</label>
                    <input type="text" name="fullName" required class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-pink-400 focus:outline-none">
                </div>
                <div>
                    <label class="block text-sm text-gray-600 mb-1">Số điện thoại</label>
                    <input type="tel" name="phone" required class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-pink-400 focus:outline-none">
                </div>
                <div>
                    <label class="block text-sm text-gray-600 mb-1">Địa chỉ</label>
                    <textarea name="address" required rows="3" class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-pink-400 focus:outline-none"></textarea>
                </div>
            </div>
        </div>

        <div class="bg-white rounded-xl shadow-sm p-6">
            <h2 class="font-semibold text-lg mb-4">Đơn hàng</h2>
            <div th:each="item : ${items}" class="flex justify-between py-2 border-b text-sm">
                <span th:text="${item.productName} + ' x' + ${item.quantity}">Name</span>
                <span th:text="${item.formattedSubtotal} + '₫'">Price</span>
            </div>
            <div class="flex justify-between py-3 font-bold text-lg mt-4">
                <span>Tổng cộng:</span>
                <span class="text-pink-600" th:text="${total}">0₫</span>
            </div>
            <button type="submit" class="w-full bg-pink-500 text-white font-semibold py-3 rounded-xl hover:bg-pink-600 transition mt-6">Đặt hàng</button>
        </div>
    </form>
</div>

<div th:replace="~{layout :: footer}"></div>
</body>
</html>
```

---

### Task 12: Search & KMeans Templates

**Files:**
- Create: `src/main/resources/templates/search.html`
- Create: `src/main/resources/templates/kmeans.html`

- [ ] **Step 1: Create search.html**

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="vi">
<head th:replace="~{layout :: head('Tìm kiếm: ' + ${keyword} + ' - FashionStyle')}"></head>
<body>
<div th:replace="~{layout :: navbar}"></div>

<div class="max-w-7xl mx-auto px-4 py-8">
    <h1 class="text-2xl font-bold">Kết quả tìm kiếm: "<span th:text="${keyword}"></span>"</h1>
    <p class="text-gray-500 mt-1" th:text="'Tìm thấy ' + ${count} + ' sản phẩm'"></p>

    <div th:if="${count == 0}" class="text-center py-16 text-gray-500">
        <p class="text-6xl mb-4">🔍</p>
        <p>Không tìm thấy sản phẩm nào</p>
    </div>

    <div th:if="${count > 0}" class="grid grid-cols-2 md:grid-cols-4 gap-4 mt-6">
        <div th:each="p : ${results}" class="bg-white rounded-xl shadow-sm hover:shadow-lg transition overflow-hidden cursor-pointer" onclick="window.location='/product/'+${p.id}">
            <div class="h-40 bg-gray-100 flex items-center justify-center text-gray-400 text-sm">img</div>
            <div class="p-3">
                <span class="text-xs px-2 py-1 rounded-full bg-gray-100 text-gray-600" th:text="${p.genderLabel}"></span>
                <h3 class="font-semibold mt-1 text-sm" th:text="${p.name}">Name</h3>
                <p class="text-pink-600 font-bold mt-1" th:text="${p.formattedPrice}">Price</p>
            </div>
        </div>
    </div>
</div>

<div th:replace="~{layout :: footer}"></div>
</body>
</html>
```

- [ ] **Step 2: Create kmeans.html**

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="vi">
<head th:replace="~{layout :: head('Phân tích K-means - FashionStyle')}"></head>
<body>
<div th:replace="~{layout :: navbar}"></div>

<div class="max-w-7xl mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-2">Phân tích K-means Clustering</h1>
    <p class="text-gray-500 mb-8">Phân cụm sản phẩm thời trang theo 18 đặc trưng (feature vectors)</p>

    <!-- Explanation -->
    <div class="bg-white rounded-xl shadow-sm p-6 mb-8">
        <h2 class="font-semibold text-lg mb-3">Thuật toán K-means</h2>
        <p class="text-gray-600 text-sm leading-relaxed">
            K-means là thuật toán phân cụm không giám sát, nhóm các sản phẩm có đặc trưng tương tự nhau vào K=6 cụm.
            Mỗi sản phẩm được biểu diễn bởi vector 18 chiều (danh mục, chất liệu, màu sắc, phong cách).
            Khoảng cách Euclidean được dùng để đo độ tương đồng giữa các sản phẩm.
        </p>
    </div>

    <!-- Stats -->
    <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-4 mb-8">
        <div th:each="c : ${result.clusters}" class="bg-white rounded-xl shadow-sm p-4 text-center">
            <div class="text-2xl font-bold" th:text="${c.productCount}"></div>
            <div class="text-xs text-gray-500 mt-1" th:text="${c.name}"></div>
        </div>
    </div>

    <!-- Scatter Chart -->
    <div class="bg-white rounded-xl shadow-sm p-6 mb-8">
        <h2 class="font-semibold text-lg mb-4">Biểu đồ phân bố cụm</h2>
        <canvas id="scatterChart" height="120"></canvas>
    </div>

    <!-- Feature Distribution Table -->
    <div class="bg-white rounded-xl shadow-sm p-6 mb-8 overflow-x-auto">
        <h2 class="font-semibold text-lg mb-4">Phân bố đặc trưng theo cụm</h2>
        <table class="w-full text-sm">
            <thead>
                <tr class="border-b">
                    <th class="py-2 text-left">Đặc trưng</th>
                    <th th:each="c : ${result.clusters}" class="py-2 px-2 text-center" th:text="${c.id}">C</th>
                </tr>
            </thead>
            <tbody>
                <tr th:each="featEntry : ${result.featureDistribution.entrySet().iterator().next().value.entrySet()}">
                    <td class="py-2 font-medium" th:text="${featEntry.key}">Feature</td>
                    <td th:each="c : ${result.clusters}" class="py-2 px-2 text-center">
                        <div class="w-full bg-gray-100 rounded-full h-2">
                            <div class="bg-pink-500 h-2 rounded-full" th:style="'width: ' + ${result.featureDistribution.get(c.id).get(featEntry.key) * 100} + '%'"></div>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    const data = [[${result.scatterData}]];
    const colors = ['#22c55e', '#eab308', '#3b82f6', '#ec4899', '#9ca3af', '#14b8a6'];
    const clusterNames = [[${result.clusters}]].reduce((acc, c) => { acc[c.id] = c.name; return acc; }, {});

    const datasets = [];
    const grouped = {};
    data.forEach(p => {
        if (!grouped[p.clusterId]) grouped[p.clusterId] = [];
        grouped[p.clusterId].push({ x: p.x, y: p.y, name: p.productName });
    });

    Object.keys(grouped).forEach(cid => {
        datasets.push({
            label: clusterNames[cid] || 'Cluster ' + cid,
            data: grouped[cid],
            backgroundColor: colors[cid] || '#888',
            pointRadius: 6
        });
    });

    new Chart(document.getElementById('scatterChart'), {
        type: 'scatter',
        data: { datasets: datasets },
        options: {
            responsive: true,
            plugins: {
                tooltip: { callbacks: { label: ctx => ctx.raw.name } }
            },
            scales: {
                x: { title: { display: true, text: 'PC1 (Category)' } },
                y: { title: { display: true, text: 'PC2 (Material/Style)' } }
            }
        }
    });
});
</script>

<div th:replace="~{layout :: footer}"></div>
</body>
</html>
```

---

### Task 13: Static Resources

**Files:**
- Create: `src/main/resources/static/css/style.css`
- Create: `src/main/resources/static/js/main.js`

- [ ] **Step 1: Create style.css**

```css
body { font-family: 'Inter', sans-serif; }
html { scroll-behavior: smooth; }
.transition { transition: all 0.2s ease; }
```

- [ ] **Step 2: Create main.js**

```javascript
// Search form - trim whitespace on submit
document.querySelectorAll('form[action="/search"]').forEach(form => {
    form.addEventListener('submit', function(e) {
        const input = this.querySelector('input[name="q"]');
        if (input && !input.value.trim()) e.preventDefault();
    });
});
```

---

### Task 14: Verify & Run

- [ ] **Step 1: Build project**

Run: `mvn clean compile -f pom.xml`
Expected: BUILD SUCCESS

- [ ] **Step 2: Verify database connection**

Run: `mvn spring-boot:run -f pom.xml`
Expected: Server starts on port 8080 without errors

- [ ] **Step 3: Test home page**

Run: `curl http://localhost:8080/`
Expected: HTML with 6 cluster cards

- [ ] **Step 4: Test cluster page**

Run: `curl http://localhost:8080/cluster/0`
Expected: HTML with 5 sportswear products

- [ ] **Step 5: Test product detail**

Run: `curl http://localhost:8080/product/1`
Expected: HTML with product info + related products

- [ ] **Step 6: Test search**

Run: `curl "http://localhost:8080/search?q=áo"`
Expected: HTML with matching products

- [ ] **Step 7: Test K-means page**

Run: `curl http://localhost:8080/kmeans`
Expected: HTML with Chart.js canvas + tables

- [ ] **Step 8: Test cart flow**

Run: `curl -X POST http://localhost:8080/cart/add -d "productId=1&quantity=2"`
Expected: Redirect to /cart with item
