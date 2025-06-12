package org.example.server.service.impl;

import org.example.server.dto.LoginRequest;
import org.example.server.dto.RegisterRequest;
import org.example.server.dto.UserDTO;
import org.example.server.entity.User;
import org.example.server.repository.UserRepository;
import org.example.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        logger.info("UserServiceImpl初始化完成");
    }

    @Override
    public UserDTO register(RegisterRequest registerRequest) {
        // 参数校验
        if (registerRequest == null || registerRequest.getUsername() == null || registerRequest.getPassword() == null) {
            throw new IllegalArgumentException("用户名和密码不能为空");
        }

        // 检查用户名是否已存在
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new IllegalArgumentException("用户名已存在");
        }

        // 创建用户
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword()); // 实际应用中应该加密存储
        
        // 设置昵称（如果为空则使用用户名）
        user.setNickname(registerRequest.getNickname() != null ? 
                registerRequest.getNickname() : registerRequest.getUsername());
        
        // 保存用户
        User savedUser = userRepository.save(user);
        logger.info("用户注册成功: {}", savedUser.getUsername());

        // 返回用户DTO
        return UserDTO.fromUser(savedUser);
    }

    @Override
    public UserDTO login(LoginRequest loginRequest) {
        // 参数校验
        if (loginRequest == null || loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            logger.error("登录失败：用户名或密码为空");
            throw new IllegalArgumentException("用户名和密码不能为空");
        }

        // 查询用户
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            logger.warn("登录失败：用户{}不存在", loginRequest.getUsername());
            return null;
        }

        // 验证密码
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            logger.warn("登录失败：用户{}密码错误", loginRequest.getUsername());
            return null;
        }

        logger.info("用户{}登录成功", user.getUsername());
        
        // 创建UserDTO
        UserDTO userDTO = UserDTO.fromUser(user);
        
        // 生成token
        String token = generateToken(user);
        userDTO.setToken(token);
        
        return userDTO;
    }

    /**
     * 生成用户token
     * 
     * @param user 用户实体
     * @return 生成的token
     */
    private String generateToken(User user) {
        // 在实际应用中，应该使用更安全的方式生成token，如JWT
        // 这里使用简单的UUID + 用户名 + 时间戳作为token
        String tokenData = user.getId() + ":" + user.getUsername() + ":" + System.currentTimeMillis();
        return Base64.getEncoder().encodeToString(tokenData.getBytes());
    }

    @Override
    public UserDTO getUserById(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }

        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(UserDTO::fromUser).orElse(null);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }

        User user = userRepository.findByUsername(username);
        return UserDTO.fromUser(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        
        return userRepository.existsByUsername(username);
    }
} 