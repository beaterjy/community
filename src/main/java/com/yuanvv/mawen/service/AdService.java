package com.yuanvv.mawen.service;

import com.yuanvv.mawen.dto.AdDTO;
import com.yuanvv.mawen.enums.AdPosType;
import com.yuanvv.mawen.mapper.AdMapper;
import com.yuanvv.mawen.model.Ad;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdService {

    @Autowired
    private AdMapper adMapper;

    public List<AdDTO> list(AdPosType adPosType) {
        List<Ad> ads = adMapper.listAvailable(adPosType.getName(), System.currentTimeMillis());
        List<AdDTO> adDTOS = ads.stream().map(ad -> {
            AdDTO adDTO = new AdDTO();
            BeanUtils.copyProperties(ad, adDTO);
            return adDTO;
        }).collect(Collectors.toList());
        return adDTOS;
    }
}
