package org.example.server.repository;

import org.example.server.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 景区评价数据访问接口
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    /**
     * 查找指定景区的所有评价，按创建时间倒序排序
     * @param spotId 景区ID
     * @return 评价列表
     */
    @Query("SELECT r FROM Review r WHERE r.spotId = :spotId ORDER BY r.createTime DESC")
    List<Review> findBySpotId(Long spotId);
    
    @Query("SELECT r FROM Review r WHERE r.user.id = :userId ORDER BY r.createTime DESC")
    List<Review> findByUserId(Long userId);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.spotId = :spotId")
    Double getAverageRatingBySpotId(Long spotId);
} 