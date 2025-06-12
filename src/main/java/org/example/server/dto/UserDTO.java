package org.example.server.dto;

import org.example.server.entity.User;

import java.util.Date;

/**
 * 用户信息DTO（用于返回给前端的用户信息，不包含敏感字段如密码）
 */
public class UserDTO {
    private Long id;
    private String username;
    private String nickname;
    private String avatar;
    private Date createTime;
    private String token;

    public UserDTO() {
    }

    /**
     * 从User实体转换为UserDTO
     */
    public static UserDTO fromUser(User user) {
        if (user == null) {
            return null;
        }
        
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setNickname(user.getNickname());
        dto.setAvatar("/images/avatar/default-avatar.png");
        dto.setCreateTime(user.getCreateTime());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
} 