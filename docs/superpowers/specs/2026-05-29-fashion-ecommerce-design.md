# Fashion E-commerce với K-means Clustering

## Stack
- **Backend:** Java 17+, Spring Boot, Spring Data JPA
- **Frontend:** Thymeleaf + Tailwind CSS + Chart.js
- **Database:** MySQL 8.0
- **Build:** Maven

## Database (fashion_recommendation)
- `clusters` — 6 cụm phong cách (Sportswear, Streetwear, Formal Nam, Formal Nữ, Minimalist & Korean, Casual Nữ)
- `products` — 30 sản phẩm, mỗi cụm 5 sản phẩm
- `product_features` — feature vectors binary (cate_*, mat_*, color_*, style_*)

## Kiến trúc (Spring Boot MVC)

```
com.fashion/
  controller/
    HomeController       — trang chủ
    ProductController    — sản phẩm theo cụm, chi tiết
    CartController       — giỏ hàng (session)
    CheckoutController   — thanh toán
    SearchController     — tìm kiếm
    KMeansController     — phân tích K-means
  service/
    ProductService       — CRUD sản phẩm
    KMeansService        — thuật toán K-means + phân tích cụm
    CartService          — quản lý giỏ hàng
  repository/
    ProductRepository    — JPA repository
    ClusterRepository
    ProductFeatureRepository
  model/
    Product, Cluster, ProductFeature — entities
  dto/
    ProductDTO, ClusterDTO, CartItemDTO
```

## Trang & Chức năng

### 1. Trang Chủ (`/`)
- Hero section full-width: ảnh nền + "Thời trang cho mọi phong cách"
- 6 card cluster: icon + tên + mô tả + số lượng sp → click vào `/cluster/{id}`
- Footer: thông tin liên hệ

### 2. Sản Phẩm Theo Cụm (`/cluster/{id}`)
- Header: tên + mô tả cụm
- Toolbar: grid/list toggle, sort (giá tăng/giảm, tên A-Z)
- Grid: 4 cột card (ảnh, tên, giá, badge giới tính)
- List: 1 cột card ngang
- Phân trang: 8 sản phẩm/trang

### 3. Chi Tiết Sản Phẩm (`/product/{id}`)
- Ảnh lớn + thông tin + "Thêm vào giỏ hàng"
- Carousel "Sản phẩm cùng phong cách" — 4 sp cùng cluster

### 4. Giỏ Hàng (`/cart`)
- Session-based, CRUD: thêm/xoá/sửa số lượng
- Tổng tiền + nút "Thanh toán"

### 5. Thanh toán (`/checkout`)
- Form: Họ tên, SĐT, Địa chỉ
- Tóm tắt đơn hàng + tổng tiền
- "Đặt hàng" → reset giỏ + về trang chủ

### 6. K-means Analysis (`/kmeans`)
- Giải thích thuật toán K-means
- Scatter plot (Chart.js): 2D visualization các cụm
- Bảng feature vectors của từng sản phẩm
- Thống kê: số sp/cụm, features nổi bật mỗi cụm
- Demo: chọn 1 feature → phân bố theo cluster

### 7. Tìm kiếm (`/search?q=...`)
- Tìm theo tên sản phẩm
- Hiển thị grid/list như trang cluster

## K-means Implementation
- Feature vectors (18 features boolean) từ `product_features`
- Số cụm K=6
- Khoảng cách Euclidean
- Khởi tạo centroids: dùng cluster_id có sẵn từ database
- Output: nhãn cụm + khoảng cách tới centroid (cho biểu đồ)

## UI
- **CSS Framework:** Tailwind CSS (CDN hoặc build)
- **Chart:** Chart.js cho scatter plot
- **Responsive:** mobile-first
- **Màu sắc:** theme hiện đại — trắng + đen + accent màu pastel theo từng cluster
- **Font:** Google Fonts (Roboto hoặc Inter)
