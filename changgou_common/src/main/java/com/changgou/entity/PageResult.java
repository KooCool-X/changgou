package com.changgou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果类
 * @param <T>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageResult<T> {
    private Long total;//总记录数
    private List<T> rows;//记录

}
