package com.bocsoft.service;

import com.bocsoft.model.RoleDto;
import com.bocsoft.model.UserDto;

import java.util.List;

/**
 *
 * @author Wang926454
 * @date 2018/8/9 15:44
 */
public interface IUserService extends IBaseService<UserDto> {
    List<RoleDto> findRoleByUser(UserDto userDto);

    int addUserRoles(int userId, Integer[] addRoles);

    int delUserRoles(int userId, Integer[] delRoles);


}
