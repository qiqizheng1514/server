package org.example.server.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.server.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * JSON文件持久化管理器
 * 负责将评价数据保存到JSON文件中，并在应用启动时加载数据
 */
@Component
public class JsonFilePersistenceManager {
    private static final Logger logger = LoggerFactory.getLogger(JsonFilePersistenceManager.class);
    private final ObjectMapper objectMapper;
    private final String dataFilePath;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public JsonFilePersistenceManager(@Value("${app.data.directory:./data}") String dataDirectory) {
        this.objectMapper = new ObjectMapper();
        
        // 确保数据目录存在
        try {
            Path dirPath = Paths.get(dataDirectory);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
                logger.info("创建数据目录: {}", dataDirectory);
            }
        } catch (IOException e) {
            logger.error("创建数据目录失败", e);
        }
        
        this.dataFilePath = Paths.get(dataDirectory, "reviews.json").toString();
        logger.info("数据文件路径: {}", dataFilePath);
    }

    /**
     * 保存评价列表到JSON文件
     * @param reviews 评价列表
     */
    public void saveReviews(List<Review> reviews) {
        lock.writeLock().lock();
        try {
            objectMapper.writeValue(new File(dataFilePath), reviews);
            logger.info("成功保存{}条评价到文件", reviews.size());
        } catch (IOException e) {
            logger.error("保存评价数据失败", e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 从JSON文件加载评价列表
     * @return 评价列表
     */
    public List<Review> loadReviews() {
        lock.readLock().lock();
        try {
            File file = new File(dataFilePath);
            if (!file.exists()) {
                logger.info("数据文件不存在，返回空列表");
                return new ArrayList<>();
            }
            
            return objectMapper.readValue(file, new TypeReference<List<Review>>() {});
        } catch (IOException e) {
            logger.error("加载评价数据失败", e);
            return new ArrayList<>();
        } finally {
            lock.readLock().unlock();
        }
    }
} 