package org.example.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.server.common.Result;
import org.example.server.dto.ReviewDTO;
import org.example.server.entity.Review;
import org.example.server.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 景区评价控制器
 */
@RestController
@RequestMapping("/api/reviews")
@CrossOrigin // 允许跨域请求
@Tag(name = "景区评价", description = "景区评价相关接口")
@Validated
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;

    /**
     * 添加评价
     */
    @PostMapping
    @Operation(summary = "添加评价", description = "添加新的景区评价")
    public Result<ReviewDTO> createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        logger.debug("收到创建评价请求: {}", reviewDTO);
        try {
            ReviewDTO created = reviewService.createReview(reviewDTO);
            logger.info("评价创建成功: reviewId={}", created.getId());
            return Result.success(created);
        } catch (IllegalArgumentException e) {
            logger.warn("创建评价参数错误: {}", e.getMessage());
            return Result.error(e.getMessage());
        } catch (Exception e) {
            logger.error("创建评价失败", e);
            return Result.error("添加评价失败：" + e.getMessage());
        }
    }

    /**
     * 获取指定景区的评价列表
     */
    @GetMapping("/spot/{spotId}")
    @Operation(summary = "获取景区评价", description = "获取指定景区的所有评价")
    public Result<List<ReviewDTO>> getReviewsBySpotId(@PathVariable Long spotId) {
        logger.debug("开始获取景区评价列表: spotId={}", spotId);
        try {
            if (spotId == null) {
                logger.warn("获取评价列表失败：景点ID为空");
                return Result.error("景点ID不能为空");
            }
            
            logger.debug("调用reviewService.getReviewsBySpotId开始");
            List<ReviewDTO> reviews = reviewService.getReviewsBySpotId(spotId);
            logger.debug("调用reviewService.getReviewsBySpotId结束，获取到{}条评价", reviews.size());
            
            // 打印每条评价的详细信息
            reviews.forEach(review -> {
                logger.debug("评价详情: id={}, userId={}, username={}, rating={}, content={}, createTime={}", 
                    review.getId(), review.getUserId(), review.getUsername(), 
                    review.getRating(), review.getContent(), review.getCreateTime());
            });
            
            logger.info("成功获取景点{}的评价列表，共{}条评价", spotId, reviews.size());
            return Result.success(reviews);
        } catch (Exception e) {
            logger.error("获取景区评价列表失败: spotId={}, error={}", spotId, e.getMessage(), e);
            return Result.error("获取评价列表失败：" + e.getMessage());
        }
    }

    /**
     * 获取指定用户的评价列表
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "获取用户评价", description = "获取指定用户的所有评价")
    public Result<List<ReviewDTO>> getReviewsByUserId(@PathVariable Long userId) {
        try {
            List<ReviewDTO> reviews = reviewService.getReviewsByUserId(userId);
            return Result.success(reviews);
        } catch (Exception e) {
            return Result.error("获取用户评价失败：" + e.getMessage());
        }
    }

    /**
     * 获取指定景区的评价统计信息
     */
    @GetMapping("/stats/{spotId}")
    @Operation(summary = "获取评价统计", description = "获取指定景区的评价统计信息")
    public Result<Map<String, Object>> getSpotReviewStatistics(@PathVariable Long spotId) {
        logger.debug("获取景区评价统计: spotId={}", spotId);
        try {
            if (spotId == null) {
                logger.warn("获取评价统计失败：景点ID为空");
                return Result.error("景点ID不能为空");
            }
            
            Map<String, Object> stats = reviewService.getSpotReviewStatistics(spotId);
            logger.debug("成功获取评价统计: {}", stats);
            return Result.success(stats);
        } catch (Exception e) {
            logger.error("获取景区评价统计失败: spotId={}", spotId, e);
            return Result.error("获取评价统计失败：" + e.getMessage());
        }
    }

    /**
     * 删除评价（后台管理功能）
     */
    @DeleteMapping("/{reviewId}")
    @Operation(summary = "删除评价", description = "删除指定ID的评价（后台管理功能）")
    public Result<Boolean> deleteReview(
            @Parameter(description = "评价ID", required = true)
            @PathVariable Long reviewId) {
        try {
            boolean success = reviewService.deleteReview(reviewId);
            return success ? Result.success(true) : Result.error("评价不存在");
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("删除评价失败：" + e.getMessage());
        }
    }
} 