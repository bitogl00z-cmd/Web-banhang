package com.fashion.repository;

import com.fashion.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByClusterId(Integer clusterId, Pageable pageable);
    long countByClusterId(Integer clusterId);
    List<Product> findTop4ByClusterIdAndIdNot(Integer clusterId, Integer excludeId);
    List<Product> findByNameContainingIgnoreCase(String keyword);
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM products " +
                   "WHERE cluster_id = (SELECT cluster_id FROM products WHERE product_id = :productId) " +
                   "AND product_id <> :productId " +
                   "ORDER BY RAND() " +
                   "LIMIT 4", nativeQuery = true)
    List<Product> findRecommendedProducts(@Param("productId") Integer productId);
}
