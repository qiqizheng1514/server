package org.example.server.entity;

import jakarta.persistence.*;
import java.util.Date;

/**
 * 收藏实体类
 */
@Entity
@Table(name = "favorites")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "spot_id", nullable = false)
    private Long spotId;

    @Column(name = "spot_name", nullable = false)
    private String spotName;

    @Column(name = "spot_image", nullable = false)
    private String spotImage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @PrePersist
    protected void onCreate() {
        createTime = new Date();
    }

    // Getters and Setters
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", spotId=" + spotId +
                ", spotName='" + spotName + '\'' +
                ", spotImage='" + spotImage + '\'' +
                ", createTime=" + createTime +
                '}';
    }
} 