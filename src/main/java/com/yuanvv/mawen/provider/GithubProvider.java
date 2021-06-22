package com.yuanvv.mawen.provider;

import com.alibaba.fastjson.JSON;
import com.yuanvv.mawen.dto.CodeDTO;
import com.yuanvv.mawen.dto.GithubUserDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;


/**
 * @author yuanvv
 * @date 2021/6/22
 */

@Component
public class GithubProvider {
    public String getAccessToken(CodeDTO codeDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(codeDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            String token = s.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUserDTO getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)   // token 修正放在头部
                .build();

        try (Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            GithubUserDTO userDTO = JSON.parseObject(s, GithubUserDTO.class);
            return userDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
