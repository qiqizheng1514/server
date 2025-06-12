package org.example.server.controller;

import org.example.server.common.Result;
import org.example.server.dto.FavoriteDTO;
import org.example.server.dto.FavoriteRequest;
import org.example.server.service.FavoriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收藏控制器
 */
@RestController
@RequestMapping("/api/favorites")
@CrossOrigin
public class FavoriteController {
    private static final Logger logger = LoggerFactory.getLogger(FavoriteController.class);

    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
        logger.info("FavoriteController初始化完成");
    }

    /**
     * 添加收藏
     */
    @PostMapping
    public Result<FavoriteDTO> addFavorite(@RequestBody FavoriteRequest request) {
        try {
            FavoriteDTO favorite = favoriteService.addFavorite(request.getUserId(), request.getSpotId());
            return Result.success(favorite);
        } catch (Exception e) {
            logger.error("添加收藏失败", e);
            return Result.error("添加收藏失败：" + e.getMessage());
        }
    }

    /**
     * 取消收藏
     */
    @DeleteMapping("/{userId}/{spotId}")
    public Result<Void> removeFavorite(
            @PathVariable Long userId,
            @PathVariable Long spotId) {
        try {
            favoriteService.removeFavorite(userId, spotId);
            return Result.success(null);
        } catch (Exception e) {
            logger.error("取消收藏失败", e);
            return Result.error("取消收藏失败：" + e.getMessage());
        }
    }

    /**
     * 获取用户的收藏列表
     */
    @GetMapping("/user/{userId}")
    public Result<List<FavoriteDTO>> getUserFavorites(@PathVariable Long userId) {
        try {
            List<FavoriteDTO> favorites = favoriteService.getUserFavorites(userId);
            return Result.success(favorites);
        } catch (Exception e) {
            logger.error("获取用户收藏列表失败", e);
            return Result.error("获取收藏列表失败：" + e.getMessage());
        }
    }

    /**
     * 检查是否已收藏
     */
    @GetMapping("/check/{userId}/{spotId}")
    public Result<Boolean> checkFavorite(
            @PathVariable Long userId,
            @PathVariable Long spotId) {
        try {
            boolean isFavorite = favoriteService.isFavorite(userId, spotId);
            return Result.success(isFavorite);
        } catch (Exception e) {
            logger.error("检查收藏状态失败", e);
            return Result.error("检查收藏状态失败：" + e.getMessage());
        }
    }
} 