package org.example.server.dto;

/**
 * 收藏请求数据传输对象
 */
public class FavoriteRequest {
    private Long userId;
    private Long spotId;
    private String spotName;
    private String spotImage;

    public FavoriteRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSpotId() {
        return spotId;
    }

    public void setSpotId(Long spotId) {
        this.spotId = spotId;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getSpotImage() {
        return spotImage;
    }

    public void setSpotImage(String spotImage) {
        this.spotImage = spotImage;
    }

    @Override
    public String toString() {
        return "FavoriteRequest{" +
                "userId=" + userId +
                ", spotId=" + spotId +
                ", spotName='" + spotName + '\'' +
                ", spotImage='" + spotImage + '\'' +
                '}';
    }
} 