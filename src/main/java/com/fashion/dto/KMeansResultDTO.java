package com.fashion.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public String getChartJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"points\":[");
        if (scatterData != null) {
            for (int i = 0; i < scatterData.size(); i++) {
                if (i > 0) sb.append(",");
                ScatterPoint p = scatterData.get(i);
                sb.append("{\"clusterId\":").append(p.getClusterId());
                sb.append(",\"clusterName\":\"").append(esc(p.getClusterName())).append("\"");
                sb.append(",\"x\":").append(p.getX());
                sb.append(",\"y\":").append(p.getY()).append("}");
            }
        }
        sb.append("],");

        sb.append("\"features\":{");
        if (featureDistribution != null) {
            boolean firstC = true;
            for (Map.Entry<Integer, Map<String, Double>> e : featureDistribution.entrySet()) {
                if (!firstC) sb.append(",");
                firstC = false;
                sb.append("\"").append(e.getKey()).append("\":{");
                boolean firstF = true;
                for (Map.Entry<String, Double> fe : e.getValue().entrySet()) {
                    if (!firstF) sb.append(",");
                    firstF = false;
                    sb.append("\"").append(esc(fe.getKey())).append("\":").append(fe.getValue());
                }
                sb.append("}");
            }
        }
        sb.append("},");

        if (clusters != null) {
            sb.append("\"clusterNames\":{");
            for (int i = 0; i < clusters.size(); i++) {
                if (i > 0) sb.append(",");
                sb.append("\"").append(clusters.get(i).getId()).append("\":\"").append(esc(clusters.get(i).getName())).append("\"");
            }
            sb.append("}");
        } else {
            sb.append("\"clusterNames\":{}");
        }
        sb.append(",\"featureNames\":[");
        String[] fn = {"Áo","Quần","Váy/Đầm","Phụ kiện","Cotton","Kaki","Jean","Thun","Len/Nỉ","Trắng","Đen","Màu nổi","Casual","Streetwear","Minimalist","Korean","Formal","Sportswear"};
        for (int i = 0; i < fn.length; i++) {
            if (i > 0) sb.append(",");
            sb.append("\"").append(fn[i]).append("\"");
        }
        sb.append("]}");

        return sb.toString();
    }

    private String esc(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }

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
        private String clusterName;
        private String productName;

        public double getX() { return x; }
        public void setX(double x) { this.x = x; }
        public double getY() { return y; }
        public void setY(double y) { this.y = y; }
        public int getClusterId() { return clusterId; }
        public void setClusterId(int clusterId) { this.clusterId = clusterId; }
        public String getClusterName() { return clusterName; }
        public void setClusterName(String clusterName) { this.clusterName = clusterName; }
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
    }
}
