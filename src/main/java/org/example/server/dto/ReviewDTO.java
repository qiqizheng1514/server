package org.example.server.dto;

import org.example.server.entity.Review;
import java.util.Date;

/**
 * 景区评价数据传输对象
 */
public class ReviewDTO {
    private Long id;
    private Long spotId;
    private Long userId;
    private String username;
    private String avatar;
    private Integer rating;
    private String title;
    private String content;
    private Date createTime;
    private Date updateTime;

    public ReviewDTO() {
    }

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.spotId = review.getSpotId();
        this.userId = review.getUser().getId();
        this.username = review.getUser().getUsername();
        this.avatar = review.getUser().getAvatar();
        this.rating = review.getRating();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.createTime = review.getCreateTime();
        this.updateTime = review.getUpdateTime();
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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