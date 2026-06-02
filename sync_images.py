"""Sync image_url in DB to match actual files in static folder."""
import pymysql
import os

DB = dict(host="localhost", user="root", password="123456",
          database="fashion_recommendation", charset="utf8mb4")
IMG_DIR = r"C:\Users\Thinkpad\Web-banhang\src\main\resources\static\images\products"

# Map product_id -> existing file extension
file_map = {}
for f in os.listdir(IMG_DIR):
    pid_str, ext = os.path.splitext(f)[0], os.path.splitext(f)[1].lstrip(".")
    if pid_str.isdigit():
        file_map[int(pid_str)] = ext

print(f"Found {len(file_map)} image files in folder")
for pid in sorted(file_map.keys())[:5]:
    print(f"  product {pid}: {file_map[pid]}")
print("  ...")

conn = pymysql.connect(**DB)
with conn.cursor() as cur:
    cur.execute("SELECT product_id FROM products ORDER BY product_id")
    pids = [r[0] for r in cur.fetchall()]

    for pid in pids:
        if pid in file_map:
            ext = file_map[pid]
            url = f"/images/products/{pid}.{ext}"
            cur.execute("UPDATE products SET image_url = %s WHERE product_id = %s", (url, pid))
        else:
            cur.execute("UPDATE products SET image_url = NULL WHERE product_id = %s", (pid,))
            print(f"  WARN: no image for product {pid}")
conn.commit()
print(f"Updated {len(pids)} products in DB")
conn.close()
