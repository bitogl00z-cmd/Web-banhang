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

    public String getGradient() {
        return switch (id) {
            case 0 -> "from-green-400 to-blue-500";
            case 1 -> "from-yellow-400 to-red-500";
            case 2 -> "from-blue-600 to-indigo-800";
            case 3 -> "from-pink-300 to-purple-500";
            case 4 -> "from-gray-100 to-gray-400";
            case 5 -> "from-teal-300 to-green-400";
            default -> "from-gray-300 to-gray-500";
        };
    }

    public String getIcon() {
        return switch (id) {
            case 0 -> "🏃";
            case 1 -> "👟";
            case 2 -> "👔";
            case 3 -> "👗";
            case 4 -> "🌸";
            case 5 -> "🛍️";
            default -> "📦";
        };
    }
}
