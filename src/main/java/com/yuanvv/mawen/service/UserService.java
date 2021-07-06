package com.yuanvv.mawen.service;

import com.yuanvv.mawen.dto.GithubUserDTO;
import com.yuanvv.mawen.mapper.UserMapper;
import com.yuanvv.mawen.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public String createOrUpdate(GithubUserDTO githubUserDTO) {

        User dbUser = userMapper.findByAccountId(String.valueOf(githubUserDTO.getId()));

        if (dbUser == null) {
            // 创建
            User user = new User();
            user.setName(githubUserDTO.getName());
            user.setAccountId(String.valueOf(githubUserDTO.getId()));
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUserDTO.getAvatar_url());
            user.setBio(githubUserDTO.getBio());
            userMapper.insert(user);
            return token;
        } else {
            // 更新
            dbUser.setName(githubUserDTO.getName());
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(githubUserDTO.getAvatar_url());
            dbUser.setBio(githubUserDTO.getBio());
            userMapper.update(dbUser);
            return dbUser.getToken();
        }
    }
}
