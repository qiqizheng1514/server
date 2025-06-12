package org.example.server.controller;

import org.example.server.common.Result;
import org.example.server.dto.LoginRequest;
import org.example.server.dto.RegisterRequest;
import org.example.server.dto.UserDTO;
import org.example.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
        logger.info("AuthController初始化完成");
    }

    /**
     * 用户注册
     *
     * @param registerRequest 注册请求
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<UserDTO> register(@RequestBody RegisterRequest registerRequest) {
        try {
            logger.info("收到注册请求: {}", registerRequest.getUsername());
            UserDTO userDTO = userService.register(registerRequest);
            return Result.success(userDTO);
        } catch (IllegalArgumentException e) {
            logger.warn("注册失败: {}", e.getMessage());
            return Result.error(400, e.getMessage());
        } catch (Exception e) {
            logger.error("注册异常", e);
            return Result.error(500, "服务器错误");
        }
    }

    /**
     * 用户登录
     *
     * @param loginRequest 登录请求
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<UserDTO> login(@RequestBody LoginRequest loginRequest) {
        try {
            logger.info("收到登录请求: {}", loginRequest.getUsername());
            UserDTO userDTO = userService.login(loginRequest);
            
            if (userDTO == null) {
                return Result.error(401, "用户名或密码错误");
            }
            
            return Result.success(userDTO);
        } catch (IllegalArgumentException e) {
            logger.warn("登录失败: {}", e.getMessage());
            return Result.error(400, e.getMessage());
        } catch (Exception e) {
            logger.error("登录异常", e);
            return Result.error(500, "服务器错误");
        }
    }

    /**
     * 验证用户会话
     *
     * @param authorization 授权头信息
     * @return 验证结果
     */
    @GetMapping("/validate")
    public Result<Boolean> validateSession(@RequestHeader(value = "Authorization", required = false) String authorization) {
        try {
            // 检查授权头是否存在
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                logger.warn("会话验证失败: 无效的授权头");
                return Result.error(401, "无效的授权头");
            }
            
            // 提取token
            String token = authorization.substring(7);
            
            // 在实际应用中，这里应该验证token的有效性
            // 目前简单实现，只要有token就认为有效
            logger.info("会话验证成功");
            return Result.success(true);
        } catch (Exception e) {
            logger.error("会话验证异常", e);
            return Result.error(500, "服务器错误");
        }
    }

    /**
     * 获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/user/{username}")
    public Result<UserDTO> getUserInfo(@PathVariable String username) {
        try {
            logger.info("获取用户信息: {}", username);
            UserDTO userDTO = userService.getUserByUsername(username);
            
            if (userDTO == null) {
                return Result.error(404, "用户不存在");
            }
            
            return Result.success(userDTO);
        } catch (IllegalArgumentException e) {
            logger.warn("获取用户信息失败: {}", e.getMessage());
            return Result.error(400, e.getMessage());
        } catch (Exception e) {
            logger.error("获取用户信息异常", e);
            return Result.error(500, "服务器错误");
        }
    }

    /**
     * 检查用户名是否已存在
     *
     * @param username 用户名
     * @return 用户名是否存在
     */
    @GetMapping("/check-username")
    public Result<Boolean> checkUsernameExists(@RequestParam String username) {
        try {
            logger.info("检查用户名是否存在: {}", username);
            boolean exists = userService.existsByUsername(username);
            return Result.success(exists);
        } catch (IllegalArgumentException e) {
            logger.warn("检查用户名失败: {}", e.getMessage());
            return Result.error(400, e.getMessage());
        } catch (Exception e) {
            logger.error("检查用户名异常", e);
            return Result.error(500, "服务器错误");
        }
    }
} 