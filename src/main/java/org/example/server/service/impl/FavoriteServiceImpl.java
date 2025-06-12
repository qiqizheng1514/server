package org.example.server.service.impl;

import org.example.server.dto.FavoriteDTO;
import org.example.server.entity.Favorite;
import org.example.server.entity.Spot;
import org.example.server.entity.User;
import org.example.server.repository.FavoriteRepository;
import org.example.server.repository.SpotRepository;
import org.example.server.repository.UserRepository;
import org.example.server.service.FavoriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 收藏服务实现类
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {
    private static final Logger logger = LoggerFactory.getLogger(FavoriteServiceImpl.class);

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpotRepository spotRepository;

    @Override
    @Transactional
    public FavoriteDTO addFavorite(Long userId, Long spotId) {
        logger.debug("开始添加收藏: userId={}, spotId={}", userId, spotId);

        // 参数验证
        if (userId == null) {
            logger.error("添加收藏失败：用户ID为空");
            throw new IllegalArgumentException("用户ID不能为空");
        }
        if (spotId == null) {
            logger.error("添加收藏失败：景点ID为空");
            throw new IllegalArgumentException("景点ID不能为空");
        }

        // 检查是否已经收藏
        if (favoriteRepository.existsByUserIdAndSpotId(userId, spotId)) {
            logger.warn("添加收藏失败：已经收藏过该景点 userId={}, spotId={}", userId, spotId);
            throw new IllegalArgumentException("已经收藏过该景点");
        }

        try {
            // 验证用户是否存在
            User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("添加收藏失败：用户不存在 userId={}", userId);
                    return new IllegalArgumentException("用户不存在");
                });

            // 验证景点是否存在
            Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> {
                    logger.error("添加收藏失败：景点不存在 spotId={}", spotId);
                    return new IllegalArgumentException("景点不存在");
                });

            // 创建收藏记录
            Favorite favorite = new Favorite();
            favorite.setUser(user);
            favorite.setSpotId(spotId);
            favorite.setSpotName(spot.getName());
            favorite.setSpotImage(spot.getImageUrl());

            favorite = favoriteRepository.save(favorite);
            logger.info("收藏添加成功: favoriteId={}, userId={}, spotId={}", 
                favorite.getId(), userId, spotId);

            return new FavoriteDTO(favorite);
        } catch (IllegalArgumentException e) {
            logger.error("添加收藏参数错误: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("添加收藏失败: userId={}, spotId={}", userId, spotId, e);
            throw new RuntimeException("添加收藏失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void removeFavorite(Long userId, Long spotId) {
        logger.debug("开始取消收藏: userId={}, spotId={}", userId, spotId);

        // 参数验证
        if (userId == null) {
            logger.error("取消收藏失败：用户ID为空");
            throw new IllegalArgumentException("用户ID不能为空");
        }
        if (spotId == null) {
            logger.error("取消收藏失败：景点ID为空");
            throw new IllegalArgumentException("景点ID不能为空");
        }

        try {
            if (!favoriteRepository.existsByUserIdAndSpotId(userId, spotId)) {
                logger.warn("取消收藏失败：收藏记录不存在 userId={}, spotId={}", userId, spotId);
                throw new IllegalArgumentException("收藏记录不存在");
            }
            favoriteRepository.deleteByUserIdAndSpotId(userId, spotId);
            logger.info("成功取消收藏: userId={}, spotId={}", userId, spotId);
        } catch (IllegalArgumentException e) {
            logger.error("取消收藏参数错误: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("取消收藏失败: userId={}, spotId={}", userId, spotId, e);
            throw new RuntimeException("取消收藏失败：" + e.getMessage());
        }
    }

    @Override
    public List<FavoriteDTO> getUserFavorites(Long userId) {
        logger.debug("开始获取用户收藏列表: userId={}", userId);

        if (userId == null) {
            logger.error("获取收藏列表失败：用户ID为空");
            throw new IllegalArgumentException("用户ID不能为空");
        }

        try {
            List<Favorite> favorites = favoriteRepository.findByUserId(userId);
            List<FavoriteDTO> dtos = favorites.stream()
                    .map(FavoriteDTO::new)
                    .collect(Collectors.toList());
            logger.info("成功获取用户收藏列表: userId={}, count={}", userId, dtos.size());
            return dtos;
        } catch (Exception e) {
            logger.error("获取用户收藏列表失败: userId={}", userId, e);
            throw new RuntimeException("获取收藏列表失败：" + e.getMessage());
        }
    }

    @Override
    public boolean isFavorite(Long userId, Long spotId) {
        logger.debug("检查收藏状态: userId={}, spotId={}", userId, spotId);

        if (userId == null || spotId == null) {
            return false;
        }

        try {
            boolean isFavorite = favoriteRepository.existsByUserIdAndSpotId(userId, spotId);
            logger.debug("收藏状态检查结果: userId={}, spotId={}, isFavorite={}", 
                userId, spotId, isFavorite);
            return isFavorite;
        } catch (Exception e) {
            logger.error("检查收藏状态失败: userId={}, spotId={}", userId, spotId, e);
            return false;
        }
    }
} 