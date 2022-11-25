package com.baidu.springsecurity.security.user;

import com.baidu.springsecurity.security.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;

public class UserDetailPassword implements UserDetailsPasswordService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        int result = userMapper.updatePassword(user.getUsername(),newPassword);
        if (result == 1) {
            ((User) user).setPassword(newPassword);
        }
        return user;
    }
}
