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

    public List<ProductDTO> getRecommendedProducts(int productId) {
        return productRepository.findRecommendedProducts(productId).stream()
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
        dto.setFormattedPrice(String.format("%,d\u0111", p.getPrice()));
        dto.setGenderLabel(p.getGenderLabel());
        dto.setImageUrl(p.getImageUrl());
        if (p.getCluster() != null) {
            dto.setClusterName(p.getCluster().getName());
            dto.setClusterId(p.getCluster().getId());
        }
        return dto;
    }
}
