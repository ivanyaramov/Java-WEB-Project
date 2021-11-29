package com.example.project.model.service;

import com.example.project.model.entity.StarsEnum;
import com.example.project.model.entity.Town;

import java.math.BigDecimal;

public class HotelServiceModel {
    private Long id;
    private String name;
    private Integer stars;
    private BigDecimal pricePerNightAdult;
    private BigDecimal pricePerNightChild;
    private String imageUrl;
    private Town town;

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

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public BigDecimal getPricePerNightAdult() {
        return pricePerNightAdult;
    }

    public void setPricePerNightAdult(BigDecimal pricePerNightAdult) {
        this.pricePerNightAdult = pricePerNightAdult;
    }

    public BigDecimal getPricePerNightChild() {
        return pricePerNightChild;
    }

    public void setPricePerNightChild(BigDecimal pricePerNightChild) {
        this.pricePerNightChild = pricePerNightChild;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }
}
