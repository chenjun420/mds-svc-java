package com.pts.mds.model;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     *  查询全部数据
     */
    List<User> selectAll();

}
