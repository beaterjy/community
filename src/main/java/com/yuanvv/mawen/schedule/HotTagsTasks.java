package com.yuanvv.mawen.schedule;

import com.yuanvv.mawen.cache.HotTagCache;
import com.yuanvv.mawen.dto.HotTagDTO;
import com.yuanvv.mawen.mapper.QuestionMapper;
import com.yuanvv.mawen.model.Question;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class HotTagsTasks {

    @Autowired
    private HotTagCache hotTagCache;

    @Autowired
    private QuestionMapper questionMapper;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 3)
//    @Scheduled(cron = "0 0 1 * * *")
    public void hotTagSchedule() {

        Integer count = questionMapper.count();
        Map<String, HotTagDTO> hotTagDTOMap = new HashMap<>();

        // 获取全部问题
        for (Integer offset = 0, limit = 5; offset <= count; offset += limit) {
            List<Question> questions = questionMapper.latestList(offset, limit);

            // 保存为 Map< tag, HotTagDTO >
            for (Question question : questions) {
                String tagContent = question.getTag();
                String[] tags = StringUtils.split(tagContent, ",|，");
                for (String tag : tags) {
                    HotTagDTO hotTagDTO = hotTagDTOMap.get(tag);
                    if (hotTagDTO == null) {
                        HotTagDTO dto = new HotTagDTO();
                        dto.setName(tag);
                        dto.setQuestionCount(1L);
                        dto.setCommentCount(Long.valueOf(question.getCommentCount()));
                        hotTagDTOMap.put(tag, dto);
                    } else {
                        // 更新 Map
                        hotTagDTO.setQuestionCount(hotTagDTO.getQuestionCount() + 1L);
                        hotTagDTO.setCommentCount(hotTagDTO.getCommentCount() + question.getCommentCount());
                        hotTagDTOMap.put(tag, hotTagDTO);
                    }
                }
            }
        }
        // 保存到 cache 中
        hotTagCache.updateTags(hotTagDTOMap);
    }
}
