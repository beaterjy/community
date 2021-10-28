package com.yuanvv.mawen.strategy;

import com.yuanvv.mawen.dto.CodeDTO;
import com.yuanvv.mawen.dto.GiteeUserDTO;
import com.yuanvv.mawen.dto.LoginUserDTO;
import com.yuanvv.mawen.enums.LoginUserType;
import com.yuanvv.mawen.provider.GiteeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GiteeUserStrategy implements UserStrategy {

    @Autowired
    private GiteeProvider giteeProvider;

    @Value("${gitee.client.id}")
    private String clientId;
    @Value("${gitee.client.secret}")
    private String clientSecret;
    @Value("${gitee.client.redirect_uri}")
    private String redirectUri;

    @Override
    public LoginUserDTO getUser(String code, String state) {

        CodeDTO codeDTO = new CodeDTO();
        codeDTO.setClient_id(clientId);
        codeDTO.setClient_secret(clientSecret);
        codeDTO.setCode(code);
        codeDTO.setRedirect_uri(redirectUri);

        String accessToken = giteeProvider.getAccessToken(codeDTO);
        GiteeUserDTO giteeUserDTO = giteeProvider.getUser(accessToken);

        // GiteeUser -> 通用用户信息
        if (giteeUserDTO != null) {
            LoginUserDTO userDTO = new LoginUserDTO();
            userDTO.setId(giteeUserDTO.getId());
            userDTO.setName(giteeUserDTO.getName());
            userDTO.setBio(giteeUserDTO.getBio());
            userDTO.setAvatar_url(giteeUserDTO.getAvatar_url());
            return userDTO;
        }
        return null;
    }

    @Override
    public LoginUserType getSupportedType() {
        return LoginUserType.GITEE_USER;
    }
}
