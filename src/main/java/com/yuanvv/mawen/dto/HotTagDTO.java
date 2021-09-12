package com.yuanvv.mawen.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class HotTagDTO implements Comparable{
    private String name;
    private Long priority;
    private Long questionCount;
    private Long commentCount;

    @Override
    public int compareTo(@NotNull Object o) {
        // 大值优先
        return (int) (((HotTagDTO)o).getPriority() - this.getPriority());
    }

    public void computePriority() {
        // 优先级的计算方式
        priority = 5L * questionCount + commentCount;
    }
}
