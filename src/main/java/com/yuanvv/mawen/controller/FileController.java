package com.yuanvv.mawen.controller;

import com.yuanvv.mawen.dto.FileDTO;
import com.yuanvv.mawen.exception.CustomizeErrorCode;
import com.yuanvv.mawen.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Controller
public class FileController {

    @Value("${upload.file.image-id}")
    private String id;

    @Value("${upload.file.image-dir}")
    private String dir;

    @RequestMapping(value = "/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        MultipartFile file = multipartRequest.getFile(id);
        String[] splits = file.getOriginalFilename().split("\\.");
        if (splits.length <= 1) {
            throw new CustomizeException(CustomizeErrorCode.UPLOAD_TYPE_ERROR);
        }
        // 随机文件名
        String fileName = UUID.randomUUID().toString() + "." + splits[splits.length - 1];
        try {
            InputStream inputStream = file.getInputStream();
            FileOutputStream fos = new FileOutputStream(dir + fileName);
            byte[] b = new byte[1024];
            while ((inputStream.read(b)) != -1) {
                fos.write(b);// 写入数据
            }
            inputStream.close();
            fos.close();// 保存数据

        } catch (IOException e) {
            e.printStackTrace();
        }

        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setMessage("图片");
        fileDTO.setUrl("/upload/image/" + fileName);
        return fileDTO;
    }
}
