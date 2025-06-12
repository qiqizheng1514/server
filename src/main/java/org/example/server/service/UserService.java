package org.example.server.service;

import org.example.server.dto.LoginRequest;
import org.example.server.dto.RegisterRequest;
import org.example.server.dto.UserDTO;
import org.example.server.entity.User;

/**
 * 用户服务接口
 */
public interface UserService {
    /**
     * 用户注册
     *
     * @param registerRequest 注册请求
     * @return 注册成功的用户信息
     */
    UserDTO register(RegisterRequest registerRequest);

    /**
     * 用户登录
     *
     * @param loginRequest 登录请求
     * @return 登录成功的用户信息，登录失败则返回null
     */
    UserDTO login(LoginRequest loginRequest);

    /**
     * 根据ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserDTO getUserById(Long userId);

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    UserDTO getUserByUsername(String username);

    /**
     * 检查用户名是否已存在
     *
     * @param username 用户名
     * @return 如果用户名已存在则返回true，否则返回false
     */
    boolean existsByUsername(String username);
} 