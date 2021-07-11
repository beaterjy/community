package com.yuanvv.mawen.advice;

import com.alibaba.fastjson.JSON;
import com.yuanvv.mawen.dto.ResultDTO;
import com.yuanvv.mawen.exception.CustomizeErrorCode;
import com.yuanvv.mawen.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    // 可以处理的异常，根据请求的 contentType 类型以不同的方式返回
    @ExceptionHandler()
    public ModelAndView handleControllerException(HttpServletRequest request, Throwable ex,
                                            HttpServletResponse response,
                                            Model model) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            // 以 JSON 格式返回
            ResultDTO resultDTO = ex instanceof CustomizeException ?
                    ResultDTO.errorOf((CustomizeException) ex) :
                    ResultDTO.errorOf(new CustomizeException(CustomizeErrorCode.SYS_ERROR));
            try {
                // 使用 servlet 方式实现，TODO：未明
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                response.setStatus(200);
                PrintWriter printWriter = response.getWriter();
                printWriter.write(JSON.toJSONString(resultDTO));
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            // 以 网页格式返回
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", "服务太热了，要不然稍等下再来试试！");
            }
            return new ModelAndView("error");
        }
    }


}
