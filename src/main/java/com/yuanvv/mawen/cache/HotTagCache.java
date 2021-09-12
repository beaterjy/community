package com.yuanvv.mawen.cache;

import com.yuanvv.mawen.dto.HotTagDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

@Component
@Data
public class HotTagCache {
    private List<HotTagDTO> hotTags;

    public void updateTags(Map<String, HotTagDTO> hotTagDTOMap) {
        // Map 转为 List
        ArrayList<HotTagDTO> hotTagDTOS = new ArrayList<>();
        hotTagDTOMap.forEach((name,dto)-> {
            dto.computePriority();
            hotTagDTOS.add(dto);
        });

        // 大顶堆
        PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>(hotTagDTOS);
        // 列表
        List<HotTagDTO> hotTags = new ArrayList<>();
        while (!priorityQueue.isEmpty()) {
            hotTags.add(priorityQueue.poll());
        }
        // 更新
        this.hotTags = hotTags;
    }
}
