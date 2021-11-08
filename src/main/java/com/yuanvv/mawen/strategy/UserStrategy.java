package com.yuanvv.mawen.strategy;

import com.yuanvv.mawen.dto.LoginUserDTO;
import com.yuanvv.mawen.enums.LoginUserType;

public interface UserStrategy {
    public LoginUserDTO getUser(String code, String state);
    public LoginUserType getSupportedType();

}
