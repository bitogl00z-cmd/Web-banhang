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
