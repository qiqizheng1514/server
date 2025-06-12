package org.example.server.dto;

import org.example.server.entity.Favorite;

import java.util.Date;

/**
 * 收藏数据传输对象
 */
public class FavoriteDTO {
    private Long id;
    private Long spotId;
    private Long userId;
    private String username;
    private String avatar;
    private String spotName;
    private String spotImage;
    private Date createTime;

    public FavoriteDTO() {
    }

    public FavoriteDTO(Favorite favorite) {
        this.id = favorite.getId();
        this.spotId = favorite.getSpotId();
        this.userId = favorite.getUser().getId();
        this.username = favorite.getUser().getUsername();
        this.avatar = favorite.getUser().getAvatar();
        this.spotName = favorite.getSpotName();
        this.spotImage = favorite.getSpotImage();
        this.createTime = favorite.getCreateTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpotId() {
        return spotId;
    }

    public void setSpotId(Long spotId) {
        this.spotId = spotId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 将实体转换为DTO
     */
    public static FavoriteDTO fromFavorite(Favorite favorite) {
        if (favorite == null) {
            return null;
        }
        
        FavoriteDTO dto = new FavoriteDTO(favorite);
        return dto;
    }
} 