package com.pts.mds.controller;

import com.pts.mds.common.ApiResponse;
import com.pts.mds.model.User;
import com.pts.mds.service.PrimaryUserService;
import com.pts.mds.service.SecondaryUserService;
import com.pts.mds.service.ThirdaryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private PrimaryUserService primaryUserService;

    @Autowired
    private SecondaryUserService secondaryUserService;

    @Autowired
    private ThirdaryUserService thirdaryUserService;

    @GetMapping("/primary")
    public ApiResponse getPrimaryUsers() {
        List<User> userList = primaryUserService.findUsers();
        return new ApiResponse(true, 0, "Success", userList);
    }

    @GetMapping("/secondary")
    public ApiResponse getSecondaryUsers() {
        List<User> userList = secondaryUserService.findUsers();
        return new ApiResponse(true, 0, "Success", userList);
    }

    @GetMapping("/thirdary")
    public ApiResponse getThirdaryUsers() {
        List<User> userList = thirdaryUserService.findUsers();
        return new ApiResponse(true, 0, "Success", userList);
    }

}
