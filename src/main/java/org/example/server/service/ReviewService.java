package org.example.server.service;

import org.example.server.dto.ReviewDTO;
import java.util.List;
import java.util.Map;

/**
 * 景区评价服务接口
 */
public interface ReviewService {
    
    /**
     * 添加景区评价
     * @param reviewDTO 评价数据传输对象
     * @return 添加的评价实体
     */
    ReviewDTO createReview(ReviewDTO reviewDTO);
    
    /**
     * 获取指定景区的所有评价
     * @param spotId 景区ID
     * @return 评价列表
     */
    List<ReviewDTO> getReviewsBySpotId(Long spotId);
    
    /**
     * 获取指定用户的所有评价
     * @param userId 用户ID
     * @return 评价列表
     */
    List<ReviewDTO> getReviewsByUserId(Long userId);
    
    /**
     * 获取指定景区的平均评分
     * @param spotId 景区ID
     * @return 平均评分
     */
    Double getAverageRatingBySpotId(Long spotId);
    
    /**
     * 获取指定景区的评价统计信息
     * @param spotId 景区ID
     * @return 评价统计信息（包含平均分、评价总数、各分数段的评价数）
     */
    Map<String, Object> getSpotReviewStatistics(Long spotId);
    
    /**
     * 删除评价（后台管理功能）
     * @param reviewId 评价ID
     * @return 是否删除成功
     */
    boolean deleteReview(Long reviewId);
} 