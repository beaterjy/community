package com.yuanvv.mawen.service;

import com.yuanvv.mawen.dto.LoginUserDTO;
import com.yuanvv.mawen.enums.LoginUserType;
import com.yuanvv.mawen.mapper.UserMapper;
import com.yuanvv.mawen.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public String createOrUpdate(LoginUserDTO loginUserDTO, LoginUserType supportedType) {

        User dbUser = userMapper.findByAccountIdAndType(String.valueOf(loginUserDTO.getId()), supportedType.getType());

        if (dbUser == null) {
            // 创建
            User user = new User();
            user.setType(supportedType.getType());
            user.setName(loginUserDTO.getName());
            user.setAccountId(String.valueOf(loginUserDTO.getId()));
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(loginUserDTO.getAvatar_url());
            user.setBio(loginUserDTO.getBio());
            userMapper.insert(user);
            return token;
        } else {
            // 更新
            dbUser.setName(loginUserDTO.getName());
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(loginUserDTO.getAvatar_url());
            dbUser.setBio(loginUserDTO.getBio());
            userMapper.update(dbUser);
            return dbUser.getToken();
        }
    }
}
