package com.yuanvv.mawen.advice;

import com.yuanvv.mawen.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {

    // 可以处理的异常
    @ExceptionHandler()
    public ModelAndView handleControllerException(HttpServletRequest request, Throwable ex,
                                                  Model model) {
        if (ex instanceof CustomizeException) {
            model.addAttribute("message", ex.getMessage());
        } else {
            model.addAttribute("message", "服务太热了，要不然稍等下再来试试！");
        }
        return new ModelAndView("error");
    }


}
