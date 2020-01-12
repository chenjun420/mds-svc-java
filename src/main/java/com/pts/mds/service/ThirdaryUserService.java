package com.pts.mds.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pts.mds.common.DynamicDataSource.MdsDataSource;
import com.pts.mds.common.DynamicDataSource.MdsDataSourceNames;
import com.pts.mds.model.InfoWithPage;
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

    public InfoWithPage<List<User>> findUsers(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.selectAll();
        //用PageInfo对结果进行包装
        PageInfo pageInfo = new PageInfo(userList);
        InfoWithPage<List<User>> userPageInfo = new InfoWithPage<>();
        userPageInfo.setPagesTotal(pageInfo.getPages());
        userPageInfo.setPageSize(pageInfo.getPageSize());
        userPageInfo.setPageNum(pageInfo.getPageNum());
        userPageInfo.setData(userList);
        return userPageInfo;
    }

}
