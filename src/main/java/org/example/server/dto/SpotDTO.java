package org.example.server.dto;

import org.example.server.entity.Spot;
import java.math.BigDecimal;
import java.util.Date;

public class SpotDTO {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String openingHours;
    private BigDecimal ticketPrice;
    private BigDecimal rating;
    private String imageUrl;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Date createTime;
    private Date updateTime;

    public SpotDTO() {
    }

    public SpotDTO(Spot spot) {
        this.id = spot.getId();
        this.name = spot.getName();
        this.description = spot.getDescription();
        this.address = spot.getAddress();
        this.openingHours = spot.getOpeningHours();
        this.ticketPrice = spot.getTicketPrice();
        this.rating = spot.getRating();
        this.imageUrl = spot.getImageUrl();
        this.latitude = spot.getLatitude();
        this.longitude = spot.getLongitude();
        this.createTime = spot.getCreateTime();
        this.updateTime = spot.getUpdateTime();
    }

    // Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
} 