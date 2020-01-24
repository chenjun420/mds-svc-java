package com.pts.mds.service;

import com.pts.mds.common.dynamicdatasource.MdsDataSource;
import com.pts.mds.common.dynamicdatasource.MdsDataSourceNames;
import com.pts.mds.model.User;
import com.pts.mds.model.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@MdsDataSource(MdsDataSourceNames.SECONDARY)
public class SecondaryUserService {

    @Resource
    private UserMapper userMapper;

    public List<User> findUsers() {
        return userMapper.selectAll();
    }

}
