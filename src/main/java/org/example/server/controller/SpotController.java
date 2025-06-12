package org.example.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.server.common.Result;
import org.example.server.dto.SpotDTO;
import org.example.server.service.SpotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spots")
@CrossOrigin
@Tag(name = "景点管理", description = "景点相关接口")
public class SpotController {

    private static final Logger logger = LoggerFactory.getLogger(SpotController.class);

    @Autowired
    private SpotService spotService;

    @GetMapping
    @Operation(summary = "获取所有景点", description = "获取所有景点的详细信息")
    public Result<List<SpotDTO>> getAllSpots() {
        try {
            List<SpotDTO> spots = spotService.getAllSpots();
            return Result.success(spots);
        } catch (Exception e) {
            logger.error("获取景点列表失败", e);
            return Result.error("获取景点列表失败：" + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取景点详情", description = "根据ID获取景点的详细信息")
    public Result<SpotDTO> getSpotById(@PathVariable Long id) {
        try {
            SpotDTO spot = spotService.getSpotById(id);
            return Result.success(spot);
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            logger.error("获取景点详情失败: id={}", id, e);
            return Result.error("获取景点详情失败：" + e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "创建景点", description = "创建新的景点信息")
    public Result<SpotDTO> createSpot(@Valid @RequestBody SpotDTO spotDTO) {
        try {
            SpotDTO created = spotService.createSpot(spotDTO);
            return Result.success(created);
        } catch (Exception e) {
            logger.error("创建景点失败", e);
            return Result.error("创建景点失败：" + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新景点", description = "更新指定ID的景点信息")
    public Result<SpotDTO> updateSpot(@PathVariable Long id, @Valid @RequestBody SpotDTO spotDTO) {
        try {
            SpotDTO updated = spotService.updateSpot(id, spotDTO);
            return Result.success(updated);
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            logger.error("更新景点失败: id={}", id, e);
            return Result.error("更新景点失败：" + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除景点", description = "删除指定ID的景点")
    public Result<Void> deleteSpot(@PathVariable Long id) {
        try {
            spotService.deleteSpot(id);
            return Result.success();
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            logger.error("删除景点失败: id={}", id, e);
            return Result.error("删除景点失败：" + e.getMessage());
        }
    }
} 