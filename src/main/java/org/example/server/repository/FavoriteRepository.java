package org.example.server.repository;

import org.example.server.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收藏数据访问接口
 */
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    /**
     * 根据用户ID获取所有收藏
     *
     * @param userId 用户ID
     * @return 收藏列表
     */
    @Query("SELECT f FROM Favorite f WHERE f.user.id = :userId")
    List<Favorite> findByUserId(Long userId);

    /**
     * 检查用户是否已收藏景点
     *
     * @param userId 用户ID
     * @param spotId 景点ID
     * @return 是否已收藏
     */
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Favorite f WHERE f.user.id = :userId AND f.spotId = :spotId")
    boolean existsByUserIdAndSpotId(Long userId, Long spotId);

    /**
     * 根据用户ID和景点ID删除收藏
     *
     * @param userId 用户ID
     * @param spotId 景点ID
     * @return 是否删除成功
     */
    @Modifying
    @Query("DELETE FROM Favorite f WHERE f.user.id = :userId AND f.spotId = :spotId")
    void deleteByUserIdAndSpotId(Long userId, Long spotId);
} 