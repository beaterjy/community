package com.yuanvv.mawen.cache;

import com.yuanvv.mawen.dto.TagDTO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TagCache {

    public static List<TagDTO> getTags(){
        List<TagDTO> tags = new ArrayList<>();

        TagDTO frontEnd = new TagDTO();
        frontEnd.setCategoryName("前端开发");
        frontEnd.setTags(Arrays.asList("html","html5","css","css3","javascript","jquery","json","ajax","正则表达式","bootstrap"));
        tags.add(frontEnd);

        TagDTO backEnd = new TagDTO();
        backEnd.setCategoryName("Java开发");
        backEnd.setTags(Arrays.asList("java","java-ee","jar","spring","hibernate","struts","tomcat","maven","eclipse","intellij-idea"));
        tags.add(backEnd);

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("数据库","mysql","sqlite","oracle","sql","nosql","redis","mongodb","memcached","postgresql"));
        tags.add(db);

        return tags;
    };

    public static String filterInvalid(String content) {
        List<TagDTO> tags = getTags();
        List<String> tagList = tags.stream().flatMap(t -> t.getTags().stream()).collect(Collectors.toList());
        String[] tagSelecteds = content.split(",|，");
        String invalid = Arrays.stream(tagSelecteds).filter(t -> StringUtils.isBlank(t) || !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}


