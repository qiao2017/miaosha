package com.example.demo.mapper;

import com.example.demo.bean.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User getUserByUserName(@Param("userName") String userName);

    int insertUser(@Param("user") User user);
}
