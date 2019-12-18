package com.atlantis.service.impl;

import com.atlantis.domain.Users;
import com.atlantis.domain.UsersExample;
import com.atlantis.domain.UsersExample.Criteria;
import com.atlantis.mapper.UsersMapper;
import com.atlantis.service.UsersService;
import com.atlantis.utils.ResultByPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Atlantis
 * @Date: 2019/12/18
 * @Description: com.atlantis.service.impl
 * @version: 1.0
 */
@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersMapper userMapper;

    @Override
    public ResultByPage queryUsers(Users user, Integer page, Integer pageSize) {
        String username = "";
        String nickname = "";
        if (user != null) {
            username = user.getUsername();
            nickname = user.getNickname();
        }

        PageHelper.startPage(page, pageSize);

        UsersExample userExample = new UsersExample();
        Criteria userCriteria = userExample.createCriteria();
        if (StringUtils.isNotBlank(username)) {
            userCriteria.andUsernameLike("%" + username + "%");
        }
        if (StringUtils.isNotBlank(nickname)) {
            userCriteria.andNicknameLike("%" + nickname + "%");
        }
        List<Users> userList = userMapper.selectByExample(userExample);

        PageInfo<Users> pageList = new PageInfo<Users>(userList);
        ResultByPage grid = new ResultByPage();
        grid.setTotal(pageList.getPages());
        grid.setRows(userList);
        grid.setPage(page);
        grid.setRecords(pageList.getTotal());
        return grid;
    }
}
