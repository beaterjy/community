package com.yuanvv.mawen.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {
    private List<T> data;
    private Boolean showPre;
    private Boolean showNext;
    private Boolean showStart;
    private Boolean showEnd;
    private Integer totalPage;
    private Integer currPage;
    private Integer pageSize;
    private List<Integer> pages = new ArrayList<>();
    private Integer start;
    private Integer end;

    public void setPagination(Integer page, Integer pageSize, Integer totalCount) {
        this.currPage = page;
        this.pageSize = pageSize;
        this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;

        final Integer defaultLen = 7;
        final Integer gap = defaultLen / 2;
        this.start = 1;
        this.end = totalPage;
        Integer left = start;
        Integer right = totalPage;

        // 设置 pages
        if (right - left > defaultLen) {
            if (currPage <= start + gap) {   // 在前端
                right = start + defaultLen - 1;
            } else if (currPage >= end - gap) {  // 在后端
                left = end - defaultLen + 1;
            } else {                                    // 在中间
                left = currPage - gap;
                right = currPage + gap;
            }
        }

        for (int i = left; i <= right; i++) {
            pages.add(i);
        }

        // 设置 pre, next, start, end
        if (currPage.equals(start)) {
            showPre = false;
        } else {
            showPre = true;
        }

        if (currPage.equals(end)) {
            showNext = false;
        } else {
            showNext = true;
        }

        if (pages.contains(start)) {
            showStart = false;
        } else {
            showStart = true;
        }

        if (pages.contains(end)) {
            showEnd = false;
        } else {
            showEnd = true;
        }
    }
}
