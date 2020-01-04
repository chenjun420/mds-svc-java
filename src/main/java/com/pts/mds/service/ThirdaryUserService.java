package com.pts.mds.service;

import com.pts.mds.common.DynamicDataSource.MdsDataSource;
import com.pts.mds.common.DynamicDataSource.MdsDataSourceNames;
import com.pts.mds.model.User;
import com.pts.mds.model.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@MdsDataSource(MdsDataSourceNames.THIRDARY)
public class ThirdaryUserService {

    @Resource
    private UserMapper userMapper;

    public List<User> findUsers() {
        return userMapper.selectAll();
    }

}
