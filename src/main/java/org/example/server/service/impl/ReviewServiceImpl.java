package org.example.server.service.impl;

import org.example.server.dto.ReviewDTO;
import org.example.server.entity.Review;
import org.example.server.entity.User;
import org.example.server.repository.ReviewRepository;
import org.example.server.repository.UserRepository;
import org.example.server.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 景区评价服务实现类
 */
@Service
public class ReviewServiceImpl implements ReviewService {
    private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

    // 评价数据访问接口
    private final ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
        logger.info("ReviewServiceImpl初始化完成");
    }

    @Override
    @Transactional
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        logger.debug("开始创建评价: {}", reviewDTO);
        
        // 参数验证
        if (reviewDTO == null) {
            logger.error("创建评价失败：评价数据为空");
            throw new IllegalArgumentException("评价数据不能为空");
        }
        
        if (reviewDTO.getSpotId() == null) {
            logger.error("创建评价失败：景点ID为空");
            throw new IllegalArgumentException("景点ID不能为空");
        }
        
        if (reviewDTO.getUserId() == null) {
            logger.error("创建评价失败：用户ID为空");
            throw new IllegalArgumentException("用户ID不能为空");
        }
        
        if (reviewDTO.getRating() == null || reviewDTO.getRating() < 1 || reviewDTO.getRating() > 5) {
            logger.error("创建评价失败：评分无效 rating={}", reviewDTO.getRating());
            throw new IllegalArgumentException("评分必须在1-5之间");
        }
        
        if (reviewDTO.getContent() == null || reviewDTO.getContent().trim().isEmpty()) {
            logger.error("创建评价失败：评价内容为空");
            throw new IllegalArgumentException("评价内容不能为空");
        }
        
        try {
            // 查找用户
            User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> {
                    logger.error("创建评价失败：用户不存在 userId={}", reviewDTO.getUserId());
                    return new IllegalArgumentException("用户不存在");
                });
            
            // 创建评价
            Review review = new Review();
            review.setSpotId(reviewDTO.getSpotId());
            review.setUser(user);
            review.setRating(reviewDTO.getRating());
            review.setContent(reviewDTO.getContent().trim());
            if (reviewDTO.getTitle() != null) {
                review.setTitle(reviewDTO.getTitle().trim());
            }
            
            // 保存评价
            review = reviewRepository.save(review);
            logger.info("评价创建成功: reviewId={}, userId={}, spotId={}, rating={}", 
                review.getId(), review.getUser().getId(), review.getSpotId(), review.getRating());
            
            return new ReviewDTO(review);
        } catch (IllegalArgumentException e) {
            logger.error("创建评价参数错误: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("创建评价时发生错误: {}", e.getMessage(), e);
            throw new RuntimeException("创建评价失败：" + e.getMessage());
        }
    }

    @Override
    public List<ReviewDTO> getReviewsBySpotId(Long spotId) {
        logger.debug("开始获取景点评价列表: spotId={}", spotId);
        List<Review> reviews = reviewRepository.findBySpotId(spotId);
        logger.debug("从数据库获取到{}条评价记录", reviews.size());
        
        List<ReviewDTO> dtos = reviews.stream()
                .map(review -> {
                    try {
                        // 确保用户信息被加载
                        User user = review.getUser();
                        if (user != null) {
                            logger.debug("评价ID: {}, 用户信息: id={}, username={}", 
                                review.getId(), user.getId(), user.getUsername());
                        } else {
                            logger.warn("评价ID: {}的用户信息为空", review.getId());
                        }
                        
                        ReviewDTO dto = new ReviewDTO(review);
                        logger.debug("评价详情: id={}, userId={}, username={}, rating={}, content={}, createTime={}", 
                            dto.getId(), dto.getUserId(), dto.getUsername(), dto.getRating(), 
                            dto.getContent(), dto.getCreateTime());
                        return dto;
                    } catch (Exception e) {
                        logger.error("处理评价数据时出错: reviewId={}, error={}", review.getId(), e.getMessage(), e);
                        return null;
                    }
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
        
        logger.debug("成功转换{}条评价记录为DTO", dtos.size());
        return dtos;
    }

    @Override
    public List<ReviewDTO> getReviewsByUserId(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        return reviews.stream()
                .map(ReviewDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageRatingBySpotId(Long spotId) {
        return reviewRepository.getAverageRatingBySpotId(spotId);
    }

    @Override
    public Map<String, Object> getSpotReviewStatistics(Long spotId) {
        if (spotId == null) {
            logger.error("获取评价统计失败：景点ID为空");
            throw new IllegalArgumentException("景点ID不能为空");
        }

        try {
            List<Review> reviews = reviewRepository.findBySpotId(spotId);
            logger.debug("获取到景点{}的评价数量: {}", spotId, reviews.size());
            
            Map<String, Object> statistics = new HashMap<>();
            
            // 计算总评价数
            int totalCount = reviews.size();
            statistics.put("totalCount", totalCount);
            logger.debug("景点{}的总评价数: {}", spotId, totalCount);
            
            // 计算平均评分
            double averageRating = 0.0;
            if (!reviews.isEmpty()) {
                averageRating = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);
            }
            statistics.put("averageRating", averageRating);
            logger.debug("景点{}的平均评分: {}", spotId, averageRating);
            
            // 计算各评分的分布
            Map<Integer, Long> ratingDistribution = new HashMap<>();
            for (int i = 1; i <= 5; i++) {
                final int rating = i;
                long count = reviews.stream()
                    .filter(review -> review.getRating() == rating)
                    .count();
                ratingDistribution.put(i, count);
                logger.debug("景点{}的{}星评价数量: {}", spotId, i, count);
            }
            statistics.put("ratingDistribution", ratingDistribution);
            
            return statistics;
        } catch (Exception e) {
            logger.error("获取景点{}的评价统计信息时发生错误", spotId, e);
            throw new RuntimeException("获取评价统计失败：" + e.getMessage());
        }
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        if (reviewId == null) {
            throw new IllegalArgumentException("评价ID不能为空");
        }
        
        if (!reviewRepository.existsById(reviewId)) {
            return false;
        }
        
        reviewRepository.deleteById(reviewId);
        return true;
    }
} 