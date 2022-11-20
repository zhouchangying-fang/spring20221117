package com.baidu.springsecurity.security.mapper;

import com.baidu.springsecurity.security.user.Role;
import com.baidu.springsecurity.security.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User loadUserByUsername(@Param("username") String username);

    List<Role> getRolesByUid(@Param("uid") Integer uid);
}
