package com.yuanvv.mawen.interceptor;

import com.yuanvv.mawen.dto.AdDTO;
import com.yuanvv.mawen.enums.AdPosType;
import com.yuanvv.mawen.mapper.UserMapper;
import com.yuanvv.mawen.model.User;
import com.yuanvv.mawen.service.AdService;
import com.yuanvv.mawen.service.NotificationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AdService adService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 全站广告
        for (AdPosType adPos : AdPosType.values()) {
            List<AdDTO> ads = adService.list(adPos);
            request.getSession().setAttribute("ad"+adPos.getName(), ads);
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token") && cookie.getValue() != null) {
                    User user = userMapper.findByToken(cookie.getValue());
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                        Long unreadCount = notificationService.unreadCountByReceiverId(user.getId().longValue());
                        request.getSession().setAttribute("unreadCount", unreadCount);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
