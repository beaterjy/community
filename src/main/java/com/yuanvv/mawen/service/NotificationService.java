package com.yuanvv.mawen.service;

import com.yuanvv.mawen.dto.NotificationDTO;
import com.yuanvv.mawen.dto.PaginationDTO;
import com.yuanvv.mawen.enums.NotificationStatus;
import com.yuanvv.mawen.enums.NotificationType;
import com.yuanvv.mawen.exception.CustomizeErrorCode;
import com.yuanvv.mawen.exception.CustomizeException;
import com.yuanvv.mawen.mapper.NotificationMapper;
import com.yuanvv.mawen.model.Notification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public PaginationDTO<NotificationDTO> getPageByReceiverId(Long id, Integer page, Integer pageSize) {
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        List<Notification> notifications = notificationMapper.getListByReceiverIdOrderByStatus(id, (page - 1) * pageSize, pageSize);
        for (Notification notification: notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setNotificationType(NotificationType.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }

        // 封装成 PaginationDTO
        Long totalCount = notificationMapper.countByReceiverId(id);
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setData(notificationDTOS);
        paginationDTO.setPagination(page, pageSize, totalCount.intValue());
        return paginationDTO;
    }

    public Long unreadCountByReceiverId(Long id) {
        return notificationMapper.countByReceiverIdAndStatus(id, NotificationStatus.UNREAD.getStatus());
    }

    public NotificationDTO updateStatusById(Long id, Integer status) {
        Notification notification = notificationMapper.getById(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }

        // 更新 status
        notification.setStatus(status);
        // 数据库更新
        notificationMapper.update(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        return notificationDTO;
    }
}
