package com.yuanvv.mawen.provider;

import com.alibaba.fastjson.JSON;
import com.yuanvv.mawen.dto.CodeDTO;
import com.yuanvv.mawen.dto.GiteeUserDTO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Map;


/**
 * @author yuanvv
 * @date 2021/6/22
 */

@Component
@Slf4j
public class GiteeProvider {
    public String getAccessToken(CodeDTO codeDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(codeDTO));
        String url = MessageFormat.format("https://gitee.com/oauth/token?grant_type=authorization_code&code={0}&client_id={1}&redirect_uri={2}",
                codeDTO.getCode(), codeDTO.getClient_id(), codeDTO.getRedirect_uri());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            Map<String, String> responseMap = (Map<String, String>) JSON.parse(s);
            String token = responseMap.get("access_token");
            return token;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public GiteeUserDTO getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(MessageFormat.format("https://gitee.com/api/v5/user?access_token={0}", accessToken))
                .build();

        try (Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            GiteeUserDTO userDTO = JSON.parseObject(s, GiteeUserDTO.class);
            if (userDTO.getId() == null) {
                throw new Exception("Gitee user is illegal.");
            }
            return userDTO;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
