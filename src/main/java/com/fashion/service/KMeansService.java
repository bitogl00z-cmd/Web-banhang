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
            double x = vec[0] * 1.0 + vec[2] * 0.5;
            double y = 0;
            for (int i = 3; i < 18; i++) y += vec[i] * (i % 3 == 0 ? 0.5 : 0.3);
            x += (Math.random() - 0.5) * 0.1;
            y += (Math.random() - 0.5) * 0.1;

            KMeansResultDTO.ScatterPoint sp = new KMeansResultDTO.ScatterPoint();
            sp.setX(Math.round(x * 100) / 100.0);
            sp.setY(Math.round(y * 100) / 100.0);
            sp.setClusterId(cid);
            sp.setClusterName(pf.getProduct().getCluster().getName());
            sp.setProductName(pf.getProduct().getName());
            points.add(sp);
        }
        return points;
    }

    private Map<Integer, Map<String, Double>> computeFeatureDistribution(List<ProductFeature> features) {
        String[] featureNames = {"\u00c1o","Qu\u1ea7n","V\u00e1y/\u0110\u1ea7m","Ph\u1ee5 ki\u1ec7n","Cotton","Kaki","Jean","Thun","Len/N\u1ec9","Tr\u1eafng","\u0110en","M\u00e0u n\u1ed5i","Casual","Streetwear","Minimalist","Korean","Formal","Sportswear"};
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
