package com.example.mapper;

import com.example.entity.AdminAction;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminActionMapper {
    /**
     * 插入数据
     * @param adminAction
     */
    void insert(AdminAction adminAction);
}
