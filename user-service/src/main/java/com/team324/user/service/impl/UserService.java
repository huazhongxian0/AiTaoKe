package com.team324.user.service.impl;

import com.team324.user.mapper.UserMapper;
import com.team324.user.pojo.User;
import com.team324.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

}