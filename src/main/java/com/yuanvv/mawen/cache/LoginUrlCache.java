package com.yuanvv.mawen.cache;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Data
@Component
public class LoginUrlCache {

    @Value("${github.client.id}")
    private String githubClientId;

    @Value("${github.client.redirect_uri}")
    private String githubRedirectUri;

    @Value("${gitee.client.id}")
    private String giteeClientId;

    @Value("${gitee.client.redirect_uri}")
    private String giteeRedirectUri;


    public String getGithubCallbackUrl() {
        return MessageFormat.format("https://github.com/login/oauth/authorize?client_id={0}&scope=user&state=1&redirect_uri={1}", githubClientId, githubRedirectUri);
    }

    public String getGiteeCallbackUrl() {
        return MessageFormat.format("https://gitee.com/oauth/authorize?client_id={0}&redirect_uri={1}&response_type=code", giteeClientId, giteeRedirectUri);
    }

}
