package org.example.server.service;

import org.example.server.dto.FavoriteDTO;
import java.util.List;

/**
 * 收藏服务接口
 */
public interface FavoriteService {
    /**
     * 添加收藏
     *
     * @param userId 用户ID
     * @param spotId 景点ID
     * @return 添加后的收藏信息
     */
    FavoriteDTO addFavorite(Long userId, Long spotId);

    /**
     * 取消收藏
     *
     * @param userId 用户ID
     * @param spotId 景点ID
     */
    void removeFavorite(Long userId, Long spotId);

    /**
     * 获取用户的所有收藏
     *
     * @param userId 用户ID
     * @return 收藏列表
     */
    List<FavoriteDTO> getUserFavorites(Long userId);

    /**
     * 检查用户是否已收藏景点
     *
     * @param userId 用户ID
     * @param spotId 景点ID
     * @return 是否已收藏
     */
    boolean isFavorite(Long userId, Long spotId);
} 