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
