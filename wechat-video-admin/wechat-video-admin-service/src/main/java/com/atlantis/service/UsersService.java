package com.atlantis.service;


import com.atlantis.domain.Users;
import com.atlantis.utils.ResultByPage;

/**
 * @Auther: Atlantis
 * @Date: 2019/12/18
 * @Description: com.atlantis.service
 * @version: 1.0
 */
public interface UsersService {
    public ResultByPage queryUsers(Users user, Integer page, Integer pageSize);
}
