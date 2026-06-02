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
            case 0 -> "from-slate-700 to-slate-900";
            case 1 -> "from-rose-400 to-pink-600";
            case 2 -> "from-amber-500 to-orange-700";
            case 3 -> "from-sky-400 to-blue-600";
            case 4 -> "from-stone-300 to-stone-500";
            default -> "from-gray-300 to-gray-500";
        };
    }

    public String getIcon() {
        return switch (id) {
            case 0 -> "👔";
            case 1 -> "👗";
            case 2 -> "🎤";
            case 3 -> "🏃";
            case 4 -> "🌿";
            default -> "📦";
        };
    }
}
