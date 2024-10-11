package com.example.lab5.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product")  // Bảng 'product' trong cơ sở dữ liệu
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;
    private String category;
    private Double price;
    private String imageFilename;

    // Constructor mặc định
    public Product() {
    }

    // Constructor với tham số
    public Product(String name, String brand, String category, Double price, String imageFilename) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.imageFilename = imageFilename;
    }

    // Getters và Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }
}
