package com.yuanvv.mawen.strategy;

import com.yuanvv.mawen.dto.CodeDTO;
import com.yuanvv.mawen.dto.GithubUserDTO;
import com.yuanvv.mawen.dto.LoginUserDTO;
import com.yuanvv.mawen.enums.LoginUserType;
import com.yuanvv.mawen.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GithubUserStrategy implements UserStrategy {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.client.redirect_uri}")
    private String redirectUri;

    @Override
    public LoginUserDTO getUser(String code, String state) {

        CodeDTO codeDTO = new CodeDTO();
        codeDTO.setClient_id(clientId);
        codeDTO.setClient_secret(clientSecret);
        codeDTO.setCode(code);
        codeDTO.setRedirect_uri(redirectUri);

        String accessToken = githubProvider.getAccessToken(codeDTO);
        GithubUserDTO githubUserDTO = githubProvider.getUser(accessToken);
        // GithubUser -> 通用用户信息
        if (githubUserDTO != null) {
            LoginUserDTO userDTO = new LoginUserDTO();
            userDTO.setId(githubUserDTO.getId());
            userDTO.setName(githubUserDTO.getName());
            userDTO.setBio(githubUserDTO.getBio());
            userDTO.setAvatar_url(githubUserDTO.getAvatar_url());
            return userDTO;
        }
        return null;
    }

    @Override
    public LoginUserType getSupportedType() {
        return LoginUserType.GITHUB_USER;
    }
}
