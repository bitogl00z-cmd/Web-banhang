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
}
