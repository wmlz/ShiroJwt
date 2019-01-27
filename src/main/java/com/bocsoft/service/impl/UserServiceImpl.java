package com.bocsoft.service.impl;

import com.bocsoft.mapper.RoleMapper;
import com.bocsoft.mapper.UserMapper;
import com.bocsoft.model.RoleDto;
import com.bocsoft.model.UserDto;
import com.bocsoft.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wang926454
 * @date 2018/8/9 15:45
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserDto> implements IUserService {


    private RoleMapper roleMapper;

    @Autowired
    public UserServiceImpl(RoleMapper roleMapper){
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDto> findRoleByUser(UserDto userDto) {
        return roleMapper.findRoleByUser(userDto);
    }

    @Override
    public int addUserRoles(int userId, Integer[] addRoles) {
        Map<String,Object> param = new HashMap<>();
        param.put("userId",userId);
        param.put("addRoles",addRoles);
        return roleMapper.addUserRoles(param);
    }

    @Override
    public int delUserRoles(int userId, Integer[] delRoles) {
        Map<String,Object> param = new HashMap<>();
        param.put("userId",userId);
        param.put("delRoles",delRoles);
        return roleMapper.delUserRoles(param);
    }

}
