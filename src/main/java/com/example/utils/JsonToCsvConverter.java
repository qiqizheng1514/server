package com.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.*;
import java.util.*;

public class JsonToCsvConverter {
    public static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            
            // 转换用户数据
            String usersJson = readFile("server/data/users.json");
            JsonNode usersArray = mapper.readTree(usersJson);
            List<String> userFields = Arrays.asList("id", "username", "password", "nickname", "avatar", "create_time", "update_time");
            convertToCsv(usersArray, "server/data/users.csv", userFields);
            
            // 转换收藏数据
            String favoritesJson = readFile("server/data/favorites.json");
            JsonNode favoritesArray = mapper.readTree(favoritesJson);
            List<String> favoriteFields = Arrays.asList("id", "user_id", "spot_id", "spot_name", "spot_image", "create_time");
            convertToCsv(favoritesArray, "server/data/favorites.csv", favoriteFields);
            
            // 转换评论数据
            String reviewsJson = readFile("server/data/reviews.json");
            JsonNode reviewsArray = mapper.readTree(reviewsJson);
            List<String> reviewFields = Arrays.asList("id", "spot_id", "user_id", "username", "avatar", "score", "title", "content", "date");
            convertToCsv(reviewsArray, "server/data/reviews.csv", reviewFields);
            
            System.out.println("转换完成！");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }
    
    private static void convertToCsv(JsonNode array, String outputPath, List<String> fields) throws IOException {
        try (FileWriter writer = new FileWriter(outputPath)) {
            // 写入CSV头部
            writer.write(String.join(",", fields) + "\n");
            
            // 写入数据行
            for (JsonNode item : array) {
                List<String> values = new ArrayList<>();
                for (String field : fields) {
                    String value = "";
                    // 处理字段名映射
                    switch (field) {
                        case "create_time":
                            value = item.has("createTime") ? item.get("createTime").asText() : "";
                            break;
                        case "update_time":
                            value = item.has("updateTime") ? item.get("updateTime").asText() : "";
                            break;
                        case "user_id":
                            value = item.has("userId") ? item.get("userId").asText() : "";
                            break;
                        case "spot_id":
                            value = item.has("spotId") ? item.get("spotId").asText() : "";
                            break;
                        case "spot_name":
                            value = item.has("spotName") ? item.get("spotName").asText() : "";
                            break;
                        case "spot_image":
                            value = item.has("spotImage") ? item.get("spotImage").asText() : "";
                            break;
                        default:
                            value = item.has(field) ? item.get(field).asText() : "";
                    }
                    // 处理CSV特殊字符
                    value = value.replace("\"", "\"\"");
                    if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
                        value = "\"" + value + "\"";
                    }
                    values.add(value);
                }
                writer.write(String.join(",", values) + "\n");
            }
        }
    }
} 